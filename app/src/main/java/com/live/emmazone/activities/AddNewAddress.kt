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
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.AddNewAddressResponse
import com.live.emmazone.response_model.EditProfileResponse
import com.live.emmazone.utils.AppUtils
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

            validateData()

        }
        binding.back.setOnClickListener {
            onBackPressed()
        }

        //Initialize Places
        Places.initialize(this, this.getString(R.string.map_key))

        binding.editState.setOnClickListener {
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this)

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

            completedAddress(pickupLat!!,pickupLong!!)

//            binding.editState.setText(place.address)

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


                binding.editState.setText(city)
                binding.editCity.setText(island)
//                edit_state.setText(island)
                binding.editZipCode.setText(pinCode)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            location = ""
        }

        return location
    }

    private fun validateData() {
        val name = binding.editName.text.toString().trim()
        val address = binding.editAddress.text.toString().trim()
        val state = binding.editState.text.toString().trim()
        val city = binding.editCity.text.toString().trim()
        val zipcode = binding.editZipCode.text.toString().trim()

        if (Validator.addAddressValidation(name,address,state,city,zipcode)) {
            //************Forgot Password API hit*********************

            val hashMap = HashMap<String, String>()
            hashMap["name"] = name
            hashMap["address"] = address
            hashMap["city"] = state
            hashMap["state"] = city
            hashMap["zipcode"] = zipcode
            hashMap["latitude"] = pickupLat.toString()
            hashMap["longitude"] = pickupLong.toString()

            appViewModel.addAddressApi(this, true, hashMap)
            appViewModel.getResponse().observe(this, this)

        } else AppUtils.showMsgOnlyWithoutClick(this, Validator.errorMessage)
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is AddNewAddressResponse) {
                    val response: AddNewAddressResponse = t.data
                    setResult(RESULT_OK)
                    finish()
//                    startActivity(Intent(this, DeliveryAddress::class.java))
//                    setResult(RESULT_OK)
//                    AlertDialog()

                }

            }

        }

    }
}