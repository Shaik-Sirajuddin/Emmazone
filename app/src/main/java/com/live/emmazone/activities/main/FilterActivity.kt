package com.live.emmazone.activities.main

import android.app.Activity
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityFilterBinding
import com.live.emmazone.utils.AppConstants
import java.io.IOException
import java.util.*

class FilterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilterBinding

    private var mLatitude = ""
    private var mLongitude = ""
    private var mDistance = ""
    private var mLocation = ""

    private var fields =
        Arrays.asList(Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.ID, Place.Field.NAME)

    private val locationLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val place = Autocomplete.getPlaceFromIntent(result.data!!)
            Log.d("Place: ", place.address)

            mLatitude = place.latLng!!.latitude.toString()
            mLongitude = place.latLng!!.longitude.toString()

            mLocation = completedAddress(place.latLng!!.latitude, place.latLng!!.longitude)
            binding.edtLocation.setText(mLocation)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        clicksHandle()
        //Initialize Places
        Places.initialize(this, this.getString(R.string.map_key))


        mLatitude = intent.getStringExtra(AppConstants.LATITUDE)!!
        mLongitude = intent.getStringExtra(AppConstants.LONGITUDE)!!
        mLocation = intent.getStringExtra(AppConstants.LOCATION)!!
        mDistance = intent.getStringExtra(AppConstants.DISTANCE)!!

        binding.edtLocation.setText(mLocation)
        if (mDistance.isNotEmpty()) {
            binding.slDistance.value = mDistance.toFloat()
        }
    }

    private fun clicksHandle() {
        binding.btnApply.setOnClickListener {
            if (binding.slDistance.value != 0.0f){
                mDistance = binding.slDistance.value.toString()
            }
            val intent = Intent()
            intent.putExtra(AppConstants.DISTANCE, mDistance)
            intent.putExtra(AppConstants.LATITUDE, mLatitude)
            intent.putExtra(AppConstants.LONGITUDE, mLongitude)
            intent.putExtra(AppConstants.LOCATION, mLocation)
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.btnCancel.setOnClickListener {
            val intent = Intent()
            intent.putExtra(AppConstants.DISTANCE, "")
            intent.putExtra(AppConstants.LATITUDE, "")
            intent.putExtra(AppConstants.LONGITUDE, "")
            intent.putExtra(AppConstants.LOCATION, "")
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.back.setOnClickListener { onBackPressed() }

        binding.edtLocation.setOnClickListener {
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this)

            locationLauncher.launch(intent)
        }


    }

    private fun completedAddress(latitude: Double, longitude: Double): String {
        var addresses: List<Address>? = null
        var city: String? = ""
        val geoCoder = Geocoder(this, Locale.getDefault())
        var location = ""

        try {
            addresses = geoCoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                val countryName = addresses[0].countryName
                val state = addresses[0].subAdminArea
                val address = addresses[0].featureName
                val island = addresses[0].adminArea
//                val city = addresses[0].locality


                try {
                    city = addresses[0].locality
                    if (city == null) city = addresses[0].subLocality
                    if (city == null) city = addresses[0].subAdminArea
                    if (city == null) city = addresses[0].adminArea
                    //  addressCity = city
                } catch (e: Exception) {
                    //  addressCity = ""
                }

                val pinCode = addresses[0].postalCode
                val latitudeAddress = addresses[0].latitude
                val longitudeAddress = addresses[0].longitude

                // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                location = addresses[0].getAddressLine(0)

            }
        } catch (e: IOException) {
            e.printStackTrace()
            location = ""
        }

        return location
    }
}