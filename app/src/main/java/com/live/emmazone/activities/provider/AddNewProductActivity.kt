package com.live.emmazone.activities.provider

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.live.emmazone.R
import com.live.emmazone.adapter.CategoriesAdapter
import com.live.emmazone.adapter.ColorAdapter
import com.live.emmazone.adapter.ImageAdapter
import com.live.emmazone.adapter.SizeAdapter
import com.live.emmazone.databinding.ActivityAddNewProductBinding
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.model.ImageModel
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.AddProductResponse
import com.live.emmazone.response_model.CategoryColorSizeResponse
import com.live.emmazone.response_model.CategoryListResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.ImagePickerUtility
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.loadImage
import com.schunts.extensionfuncton.prepareMultiPart
import com.schunts.extensionfuncton.toBody
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class AddNewProductActivity : ImagePickerUtility(), Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()

    lateinit var binding: ActivityAddNewProductBinding
    private lateinit var images: ArrayList<ImageModel>

    private val colorList = ArrayList<CategoryColorSizeResponse.Body.CategoryColor>()
    private val sizeList = ArrayList<CategoryColorSizeResponse.Body.CategorySize>()
    private lateinit var colorAdapter: ColorAdapter
    private lateinit var sizeAdapter: SizeAdapter

    private val list: ArrayList<CategoryListResponse.Body> = ArrayList()

    private var selectedCategoryId = ""

    private lateinit var imageAdapter: ImageAdapter
    private val imageList = ArrayList<String>()
    private var mainImagePath = ""


    override fun selectedImage(imagePath: String?, code: Int) {
        if (imagePath != null) {
            if (code == 0) {
                //0 for Main Pic
                mainImagePath = imagePath
                binding.ivShop.loadImage(imagePath)
            } else {
                imageList.add(imagePath)
                imageAdapter.notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        images = arrayListOf()

        initListener()
        setImageAdapter()

        appViewModel.selectedCategoryListApi(this, true)
        appViewModel.getResponse().observe(this, this)

    }


    private fun setCategoryAdapter() {
        val categoryAdapter = CategoriesAdapter(list)
        binding.rvCategories.adapter = categoryAdapter

        categoryAdapter.onClickListener = { pos ->
            list.forEachIndexed { index, body ->

                if (pos == index) {
                    body.isSelected = true
                    selectedCategoryId = body.id.toString()
                    getColorSizeApiHit()
                } else {
                    body.isSelected = false
                }
            }
            categoryAdapter.notifyDataSetChanged()
        }

    }

    private fun getColorSizeApiHit() {
        val hashMap = HashMap<String, String>()
        hashMap["categoryId"] = selectedCategoryId

        appViewModel.categoryColorSizeApi(this, true, hashMap)
        appViewModel.getResponse().observe(this, this)
    }

    private fun setColorAdapter() {
        colorAdapter = ColorAdapter(colorList)
        binding.rvColor.adapter = colorAdapter

        colorAdapter.onClickListener = { pos ->
            colorList[pos].isSelected = !colorList[pos].isSelected

            colorAdapter.notifyDataSetChanged()
        }
    }

    private fun setSizeAdapter() {
        sizeAdapter = SizeAdapter(sizeList)
        binding.rvSize.adapter = sizeAdapter


        sizeAdapter.onClickListener = { pos ->
            sizeList[pos].isSelected = !sizeList[pos].isSelected

            sizeAdapter.notifyDataSetChanged()
        }

    }


    private fun setImageAdapter() {
        imageAdapter = ImageAdapter(imageList)
        binding.recyclerImages.adapter = imageAdapter

        imageAdapter.onItemClickListener = { pos ->
            getImage(1, false)
        }
    }

    private fun initListener() {

        binding.tvInfo.setOnClickListener {
            showPopup(binding.tvInfo)
        }

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.btnSave.setOnClickListener {
            validateData()
        }

        binding.ivShop.setOnClickListener {
            getImage(0, false)
        }


    }

    private fun validateData() {
        val image: ArrayList<MultipartBody.Part> = ArrayList()
        if (mainImagePath.isNotEmpty()) {
            imageList.add(mainImagePath)
        }
        var selectedColorId = ""
        var selectedSizeId = ""
        var productHighLight = ""

        colorList.forEach {
            if (it.isSelected) {
                selectedColorId = selectedColorId + it.id + ","
            }
        }

        sizeList.forEach {
            if (it.isSelected) {
                selectedSizeId = selectedSizeId + it.id + ","
            }
        }

        if (imageList.isNotEmpty()) {
            imageList.forEach {
                image.add(prepareMultiPart("image", File(it)))
            }
        }


        if (binding.switchNotification.isChecked) {
            productHighLight = "1"
        } else {
            productHighLight = "0"
        }

        val productName = binding.edtShopName.text.toString().trim()
        val desc = binding.edtDesc.text.toString().trim()
        val price = binding.edtProductPrice.text.toString().trim()
        val quantity = binding.edtProductQ.text.toString().trim()


        if (Validator.addProductValidation(
                imageList.size, productName, desc, price, quantity,
                selectedCategoryId, selectedColorId, selectedSizeId
            )
        ) {

            val hashMap = HashMap<String, RequestBody>()
            hashMap["product_name"] = toBody(productName)
            hashMap["price"] = toBody(price)
            hashMap["product_quantity"] = toBody(quantity)
            hashMap["description"] = toBody(desc)
            hashMap["color"] = toBody(selectedColorId.substring(0, selectedColorId.length))
            hashMap["size"] = toBody(selectedSizeId.substring(0, selectedSizeId.length))
            hashMap["product_highlight"] = toBody(productHighLight)
            hashMap["categoryId"] = toBody(selectedCategoryId)


            appViewModel.addProductApi(this, true, hashMap, image)
        } else {
            AppUtils.showMsgOnlyWithoutClick(this, Validator.errorMessage)
        }

    }

    private fun showAddProductDialog() {
        val alertBuilder = AlertDialog.Builder(this)
        val factory = LayoutInflater.from(this)
        val view: View = factory.inflate(R.layout.dialog_product_added, null)

        val buttonOk = view.findViewById<Button>(R.id.ok)

        buttonOk.setOnClickListener {
            onBackPressed()
        }
        alertBuilder.setView(view)
        alertBuilder.show()
    }

    private fun showPopup(it: View) {

        val inflater =
            applicationContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.popup_my_post, null)

        val myPopupWindow = PopupWindow(
            view,
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            true
        )
        myPopupWindow.showAsDropDown(it, 0, -180)
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
                } else if (t.data is CategoryColorSizeResponse) {
                    val response: CategoryColorSizeResponse = t.data
                    if (response.code == AppConstants.SUCCESS_CODE) {

                        colorList.clear()
                        sizeList.clear()

                        colorList.addAll(response.body.categoryColors)
                        sizeList.addAll(response.body.categorySizes)

                        setColorAdapter()
                        setSizeAdapter()

                    }
                } else if (t.data is AddProductResponse) {
                    val response: AddProductResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {
                        showAddProductDialog()
                    }
                }

            }
        }
    }
}



