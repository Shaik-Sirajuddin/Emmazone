package com.live.emmazone.activities.provider

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterEditShop
import com.live.emmazone.adapter.CategoriesAdapter
import com.live.emmazone.databinding.ActivityEditShopDetailBinding
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CategoryListResponse
import com.live.emmazone.response_model.EditShopDeatilResponse
import com.live.emmazone.response_model.SellerShopDetailResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.ImagePickerUtility
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.bitmapToByte
import com.schunts.extensionfuncton.loadImage
import com.schunts.extensionfuncton.prepareMultiPart
import com.schunts.extensionfuncton.toBody
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class EditShopDetailActivity : ImagePickerUtility(),
    Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()

    lateinit var binding: ActivityEditShopDetailBinding
    lateinit var adapter: AdapterEditShop

    private val list: ArrayList<CategoryListResponse.Body> = ArrayList()

    private var latitude = ""
    private var longitude = ""
    private var mImagePath = ""
    private var byteArray:ByteArray? = null
    private lateinit var sellerShopDetail: SellerShopDetailResponse

    private var fields =
        Arrays.asList(Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.ID, Place.Field.NAME)

    private val locationLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val place = Autocomplete.getPlaceFromIntent(result.data!!)
            Log.d("Place: ", place.address)

            latitude = place.latLng!!.latitude.toString()
            longitude = place.latLng!!.longitude.toString()

            binding.edtShopAddress.setText(place.address)

        }
    }

    override fun selectedImage(imagePath: String?, code: Int, bitmap: Bitmap?) {
        if (imagePath != null) {
            mImagePath = imagePath
            binding.imgEditShop.loadImage(imagePath)
        }
        if(bitmap != null){
            byteArray = bitmapToByte(bitmap)
            mImagePath = ""
            binding.imgEditShop.loadImage(bitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditShopDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clicksHandle()
        //Initialize Places
        Places.initialize(this, this.getString(R.string.map_key))

        appViewModel.categoryListApi(this, true)
        appViewModel.getResponse().observe(this, this)

        sellerShopDetail =
            intent.getSerializableExtra(AppConstants.SHOP_DETAIL_RESPONSE)!! as SellerShopDetailResponse
        binding.imgEditShop.loadImage(sellerShopDetail.body.shopDetails.image)
        binding.edtShopName.setText(sellerShopDetail.body.shopDetails.shopName)
        binding.edtShopYearFoundation.setText(sellerShopDetail.body.shopDetails.year.toString())
        binding.edtShopAddress.setText(sellerShopDetail.body.shopDetails.shopAddress)
        binding.edtShopDesc.setText(sellerShopDetail.body.shopDetails.shopDescription)
        binding.edtPostalCode.setText(sellerShopDetail.body.shopDetails.postalCode)
        latitude = sellerShopDetail.body.shopDetails.latitude
        longitude = sellerShopDetail.body.shopDetails.longitude

    }

    private fun clicksHandle() {
        binding.imageArrowback.setOnClickListener {
            onBackPressed()
        }

        binding.edtShopAddress.setOnClickListener {
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this)

            locationLauncher.launch(intent)
        }

        binding.btnUpdate.setOnClickListener {
            validateData()
        }

        binding.mainImageLayout.setOnClickListener {
            getImage(0, false,16f,9f,true)
        }

        binding.edtShopYearFoundation.setOnClickListener {
            createDialogWithoutDateField()
        }
    }


    private fun setCategoryAdapter() {
        val shopCategoryList = sellerShopDetail.body.shopDetails.shopCategories

        list.forEach { category ->
            shopCategoryList.forEach { selectedCategory ->
                if (category.id == selectedCategory.categoryId) {
                    category.isSelected = true
                }
            }
        }

        val categoryAdapter = CategoriesAdapter(list)
        binding.rvCategories.adapter = categoryAdapter

        categoryAdapter.onClickListener = { pos ->
            list[pos].isSelected = !list[pos].isSelected
            categoryAdapter.notifyDataSetChanged()

        }

    }

    private fun createDialogWithoutDateField() {

        val alertDialog: android.app.AlertDialog?
        val builder = android.app.AlertDialog.Builder(this, R.style.AlertDialogStyle)
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


        if (Validator.validateEditShop(
                shopName, shopYear,shopAddress,
                postalCode, shopDesc, selectedCategories
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

            val image: MultipartBody.Part
            if (mImagePath.isNotEmpty()) {
                image = prepareMultiPart("image", File(mImagePath))
            } else {
                image = prepareMultiPart("image", "")
            }

            appViewModel.editShopDetail(this, true, hashMap, image)
        } else {
            AppUtils.showMsgOnlyWithoutClick(this, Validator.errorMessage)
        }
    }


    private fun updateDialog() {
        val alertDialog = AlertDialog.Builder(this)
        val factory = LayoutInflater.from(this)
        val view: View = factory.inflate(R.layout.dialog_shop_profile_updated, null)

        val buttonOk = view.findViewById<Button>(R.id.ok)

        buttonOk.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
        alertDialog.setView(view)
        alertDialog.show()
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
                } else if (t.data is EditShopDeatilResponse) {
                    val response: EditShopDeatilResponse = t.data
                    if (response.code == AppConstants.SUCCESS_CODE) {
                        updateDialog()
                    }
                }
            }
            else -> {}
        }
    }


}