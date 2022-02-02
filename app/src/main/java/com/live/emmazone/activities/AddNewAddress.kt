package com.live.emmazone.activities

import android.app.Activity
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.live.emmazone.R
import com.live.emmazone.activities.main.DeliveryAddress
import com.live.emmazone.databinding.ActivityAddNewAddressBinding
import com.live.emmazone.net.RestObservable
import com.live.emmazone.view_models.AppViewModel
import java.io.IOException
import java.util.*

class AddNewAddress : AppCompatActivity(),Observer<RestObservable> {
    lateinit var binding: ActivityAddNewAddressBinding

    private val appViewModel: AppViewModel by viewModels()

     private var pickupLat : Double? = null
     private var pickupLong : Double? = null

    private var fields =
        Arrays.asList(Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.ID, Place.Field.NAME)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            startActivity(Intent(this, DeliveryAddress::class.java))
        }
        binding.back.setOnClickListener {
            onBackPressed()
        }

        //Initialize Places
        Places.initialize(this, this.getString(R.string.map_key))

        binding.editState.setOnClickListener {
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this)
            completedAddress(pickupLat!!,pickupLong!!)

            pickupLocationLauncher.launch(intent)
        }

    }

    private val pickupLocationLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val place = Autocomplete.getPlaceFromIntent(result.data!!)
            Log.d("Place: ", place.address)

           pickupLat = place.latLng!!.latitude
           pickupLong = place.latLng!!.longitude



            binding.editState.setText(place.address)

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


                binding.editState.setText(island)
                binding.editCity.setText(city)
//                edit_state.setText(island)
                binding.editZipCode.setText(pinCode)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            location = ""
        }

        return location
    }

    override fun onChanged(t: RestObservable?) {

    }
}