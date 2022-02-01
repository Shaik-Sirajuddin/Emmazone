package com.live.emmazone.activities

import android.app.Activity
import android.content.Intent
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
import java.util.*

class AddNewAddress : AppCompatActivity(),Observer<RestObservable> {
    lateinit var binding: ActivityAddNewAddressBinding

    private val appViewModel: AppViewModel by viewModels()

     private lateinit var pickupLatLng : LatLng

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

            pickupLocationLauncher.launch(intent)
        }

    }

    private val pickupLocationLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val place = Autocomplete.getPlaceFromIntent(result.data!!)
            Log.d("Place: ", place.name.toString() + " " + place.address)

           pickupLatLng = place.latLng!!

            binding.editState.setText(place.address)

        }
    }

    override fun onChanged(t: RestObservable?) {

    }
}