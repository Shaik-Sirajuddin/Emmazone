package com.live.emmazone.activities.provider

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.NumberPicker
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.live.emmazone.R
import com.live.emmazone.adapter.CategoriesAdapter
import com.live.emmazone.databinding.ActivityAddShopDetailBinding
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.extensionfuncton.savePreference
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.AddShopResponse
import com.live.emmazone.response_model.CategoryListResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.ImagePickerUtility
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.loadImage
import com.schunts.extensionfuncton.prepareMultiPart
import com.schunts.extensionfuncton.toBody
import com.schunts.extensionfuncton.toast
import okhttp3.RequestBody
import java.io.File
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.forEach
import kotlin.collections.set

class AddShopDetailActivity : ImagePickerUtility(),
    Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()
    private lateinit var binding: ActivityAddShopDetailBinding
    private val list: ArrayList<CategoryListResponse.Body> = ArrayList()

    private var latitude = ""
    private var longitude = ""
    private var mImagePath = ""

    /*var categoryId = ""
    private var categoryIdList =  ArrayList<String>()
    var combineids = ""*/

    private var fields =
        listOf(Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.ID, Place.Field.NAME)

    private val locationLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val place = Autocomplete.getPlaceFromIntent(result.data!!)
            Log.d("Place: ", place.address)

            latitude = place.latLng!!.latitude.toString()
            longitude = place.latLng!!.longitude.toString()

            binding.edtShopAddress.setText(place.address)

//            binding.editState.setText(place.address)

        }
    }


    override fun selectedImage(imagePath: String?, code: Int, bitmap: Bitmap?) {
        if (imagePath != null) {
            mImagePath = imagePath
            binding.imgEditShop.loadImage(imagePath)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddShopDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initialize Places
        Places.initialize(this, this.getString(R.string.map_key))


        clicksHandle()
        appViewModel.categoryListApi(this, true)
        appViewModel.getResponse().observe(this, this)
    }

    private fun clicksHandle() {

        binding.imgEditShop.setOnClickListener {
            getImage(0, false, 16f, 9f, true)
        }

        binding.imageAddCat.setOnClickListener {
            binding.rvCategories.visibility = View.VISIBLE
        }

        binding.edtShopAddress.setOnClickListener {
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this)

            locationLauncher.launch(intent)
        }

        binding.imageArrowback.setOnClickListener {
            onBackPressed()
        }

        binding.btnDone.setOnClickListener {
            validateData()
        }

        binding.edtShopYearFoundation.setOnClickListener {
            createDialogWithoutDateField()
        }
    }

    private fun setCategoryAdapter() {
        val categoryAdapter = CategoriesAdapter(list)
        binding.rvCategories.adapter = categoryAdapter

        categoryAdapter.onClickListener = { pos ->
            list[pos].isSelected = !list[pos].isSelected
            categoryAdapter.notifyDataSetChanged()

        }

    }

    private fun profileCompletedDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_profile_completed)

        dialog.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this,
                android.R.color.transparent
            )
        )

        val ok = dialog.findViewById<Button>(R.id.ok)

        ok.setOnClickListener {
            startActivity(Intent(this, ProviderMainActivity::class.java))
            finishAffinity()
            dialog.dismiss()
        }
        dialog.show()
    }


    private fun validateData() {
        var selectedCategories = ""

        list.forEach {
            if (it.isSelected)
                selectedCategories = selectedCategories + it.id + ","
        }

        val shopName = binding.edtShopName.text.toString().trim()
        val shopYear = binding.edtShopYearFoundation.text.toString().trim()
        val shopAddress = binding.edtShopAddress.text.toString().trim()
        val shopDesc = binding.edtShopDesc.text.toString().trim()
        val postalCode = binding.edtPostalCode.text.toString().trim()

        if (Validator.validateAddShop(
                mImagePath, shopName, shopYear, postalCode,
                shopAddress, shopDesc, selectedCategories
            )
        ) {


            val hashMap = HashMap<String, RequestBody>()
            hashMap["shopName"] = toBody(shopName)
            hashMap["year"] = toBody(shopYear)
            hashMap["shopAddress"] = toBody(shopAddress)
            hashMap["shopDescription"] = toBody(shopDesc)
            hashMap["latitude"] = toBody(latitude)
            hashMap["longitude"] = toBody(longitude)
            hashMap["postalCode"] = toBody(postalCode)
            hashMap["category"] = toBody(selectedCategories.substring(0, selectedCategories.length))

            val image = prepareMultiPart("image", File(mImagePath))
            appViewModel.addShopApi(this, true, hashMap, image)
        } else {
            AppUtils.showMsgOnlyWithoutClick(this, Validator.errorMessage)
        }
    }


    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is CategoryListResponse) {
                    val response: CategoryListResponse = t.data
                    if (response.code == AppConstants.SUCCESS_CODE) {
                        list.clear()
                        list.addAll(response.body)
                        setCategoryAdapter()
                    }
                } else if (t.data is AddShopResponse) {
                    val response: AddShopResponse = t.data
                    if (response.code == AppConstants.SUCCESS_CODE) {
                        savePreference(AppConstants.ROLE, AppConstants.SELLER_ROLE)
                        savePreference(AppConstants.IS_LOGIN, true)
                        profileCompletedDialog()

                    }


                }
            }

            else -> {}
        }
    }

    private fun createDialogWithoutDateField() {

        val alertDialog: AlertDialog?
        val builder = AlertDialog.Builder(this, R.style.AlertDialogStyle)
        val inflater = layoutInflater

        val cal = Calendar.getInstance()

        val dialog = inflater.inflate(R.layout.month_year_picker_dialog, null)
        val monthPicker = dialog.findViewById(R.id.picker_month) as NumberPicker
        val yearPicker = dialog.findViewById(R.id.picker_year) as NumberPicker

        monthPicker.minValue = 1
        monthPicker.maxValue = 12
        monthPicker.value = cal.get(Calendar.MONTH) + 1

        val year = cal.get(Calendar.YEAR)
        yearPicker.minValue = 1500
        yearPicker.maxValue = cal.get(Calendar.YEAR)
        yearPicker.value = year

        builder.setView(dialog)
            .setPositiveButton(getString(R.string.ok)) { dialogInterface, which ->
                val value = yearPicker.value
                binding.edtShopYearFoundation.setText(value.toString())
                dialogInterface.cancel()
            }

        builder.setNegativeButton(getString(R.string.cancel)) { dialogInterface, which ->
            dialogInterface.cancel()
        }

        alertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(true)
        alertDialog.show()
    }
}