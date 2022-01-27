package com.live.emmazone.activities.provider

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.live.emmazone.MainActivity
import com.live.emmazone.R
import com.live.emmazone.adapter.CategoriesAdapter
import com.live.emmazone.databinding.ActivityAddShopDetailBinding
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.AddShopResponse
import com.live.emmazone.response_model.CategoryListResponse
import com.live.emmazone.utils.*
import com.live.emmazone.view_models.AppViewModel
import com.permissionx.guolindev.PermissionX
import com.schunts.extensionfuncton.prepareMultiPart
import com.schunts.extensionfuncton.toBody
import okhttp3.RequestBody
import retrofit2.http.Body
import java.io.File
import java.util.ArrayList

class AddShopDetailActivity : ImageLocationUpdateUtility(),
    Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()
    private lateinit var binding: ActivityAddShopDetailBinding
    private val list: ArrayList<CategoryListResponse.Body> = ArrayList()

    private var latitude = ""
    private var longitude = ""
    private var mImagePath = ""

    override fun updatedLatLng(lat: Double?, lng: Double?) {
        if (lat != null && lng != null) {
            latitude = lat.toString()
            longitude = lng.toString()
            stopLocationUpdates()
        } else {
            getLiveLocation(this)
        }
    }

    override fun selectedImage(imagePath: String?, code: Int) {
        if (imagePath != null) {
            mImagePath = imagePath
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddShopDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clicksHandle()
        getLiveLocation(this)

        appViewModel.categoryListApi(this, true)
        appViewModel.getResponse().observe(this, this)
    }

    private fun clicksHandle() {

        binding.mainImageLayout.setOnClickListener {
            getImage(0, false)
        }

        binding.imageAddCat.setOnClickListener {
            binding.rvAddShop.visibility = View.VISIBLE
        }

        binding.imageArrowback.setOnClickListener {
            onBackPressed()
        }

        binding.btnDone.setOnClickListener {
            profileCompletedDialog()
            validateData()
        }
    }

    private fun setCategoryAdapter() {
        val categoryAdapter = CategoriesAdapter(list)
        binding.rvAddShop.adapter = categoryAdapter

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
            dialog.dismiss()
        }
        dialog.show()
    }


    private fun validateData() {
        var selectedCategories = ""

        list.forEach {
            selectedCategories = selectedCategories + it.id + ","
        }

        val shopName = binding.edtShopName.text.toString().trim()
        val shopYear = binding.edtShopYearFoundation.text.toString().trim()
        val shopAddress = binding.edtShopAddress.text.toString().trim()
        val shopDesc = binding.edtShopDesc.text.toString().trim()


        if (Validator.validateAddShop(
                mImagePath, shopName, shopYear,
                shopAddress, shopDesc, selectedCategories
            )
        ) {

            val hashMap = HashMap<String, RequestBody>()
            hashMap["shop_name"] = toBody(shopName)
            hashMap["year_of_foundation"] = toBody(shopYear)
            hashMap["address"] = toBody(shopAddress)
            hashMap["description"] = toBody(shopDesc)
            hashMap["latitude"] = toBody(latitude)
            hashMap["longitude"] = toBody(longitude)

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
                }
                else if (t.data is AddShopResponse){

                }
            }
        }
    }


}