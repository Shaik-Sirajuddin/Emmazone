package com.live.emmazone.activities.provider

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.live.emmazone.R
import com.live.emmazone.adapter.CategoriesAdapter
import com.live.emmazone.adapter.ColorAdapter
import com.live.emmazone.adapter.ImageAdapter
import com.live.emmazone.adapter.SizeAdapter
import com.live.emmazone.databinding.ActivityEditProductBinding
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.model.ImageModel
import com.live.emmazone.net.RestObservable
import com.live.emmazone.response_model.*
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

class EditProductActivity : ImagePickerUtility(), Observer<RestObservable> {

    private var mainImage = ""
    private lateinit var images: ArrayList<ImageModel>
    private var selectedCategoryId = ""
    private var selectedSizeId = ""
    private var selectedColorId = ""
    private var highlightValue = 1
    private val colorList = ArrayList<CategoryColorSizeResponse.Body.CategoryColor>()
    private val sizeList = ArrayList<CategoryColorSizeResponse.Body.CategorySize>()
    private lateinit var colorAdapter: ColorAdapter
    private lateinit var sizeAdapter: SizeAdapter
    private val list: ArrayList<CategoryListResponse.Body> = ArrayList()
    var deleteImageArrayId = ArrayList<String>()
    var adapterPosition = 0
    private var arrStringMultipleImagesUploadable: ArrayList<String> = ArrayList()
    private val appViewModel: AppViewModel by viewModels()

    var productData: SellerShopDetailResponse.Body.ShopDetails.Product? = null


    lateinit var binding: ActivityEditProductBinding

    private lateinit var imageAdapter: ImageAdapter
    private var imageList = ArrayList<SellerShopDetailResponse.Body.ShopDetails.Product.ProductImage>()
    var mainImagePath = ""
    var id = ""

    override fun selectedImage(imagePath: String?, code: Int) {
        if (imagePath != null) {
            if (code == 0) {
                //0 for Main Pic
                mainImagePath = imagePath
                binding.ivShop.loadImage(imagePath)
            } else {
                imageList.add(SellerShopDetailResponse.Body.ShopDetails.Product.ProductImage(0, imagePath, 0, 0))
                imageAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productData = intent.extras?.get("productData") as SellerShopDetailResponse.Body.ShopDetails.Product

        setData(productData!!)

        images = arrayListOf()

        initListener()
//        initAdapter()
        highlightSwitchListener()
        appViewModel.selectedCategoryListApi(this, true)
        appViewModel.getResponse().observe(this, this)

    }


    fun highlightSwitchListener() {
        binding.switchNotification.setOnCheckedChangeListener { buttonView, isChecked ->
            // do something, the isChecked will be
            // true if the switch is in the On position
            if (isChecked) {
                highlightValue = 1
            } else {
                highlightValue = 0
            }
        }
    }

    private fun setData(productData: SellerShopDetailResponse.Body.ShopDetails.Product) {
        productData.mainImage?.let { mainImage = it }
        id = productData.id.toString()
        imageList.addAll(productData.productImages)
        productData.mainImage?.let { binding.ivShop.loadImage(it) }
        binding.edtShopName.setText(productData.name)
        binding.edtShotDesc.setText(productData.shortDescription)
        binding.edtDesc.setText(productData.description)
        binding.edtProductPrice.setText(productData.productPrice)
        binding.edtProductQ.setText(productData.productQuantity.toString())
        selectedCategoryId = productData.categoryId.toString()
        selectedColorId = productData.categoryColorId.toString()
        selectedSizeId = productData.categorySizeId.toString()

        getColorSizeApiHit()

        setImageAdapter()
    }

    private fun setImageAdapter() {
        imageAdapter = ImageAdapter(imageList)
        binding.recyclerImages.adapter = imageAdapter

        imageAdapter.onItemClickListener = { pos ->
            getImage(1, false)
        }
        imageAdapter.onDeleteImage = { pos, data ->
            if (data.id != 0) {
                deleteImageArrayId.add(data.id.toString())
            }
            imageList.removeAt(pos)
            imageAdapter.notifyDataSetChanged()
        }
    }


    private fun initListener() {
        binding.apply {

            back.setOnClickListener { onBackPressed() }

            btnUpdate.setOnClickListener {
                validateAddProduct()
                //showProductUpdateDialog()
            }

            ivShop.setOnClickListener {
                getImage(0, false)
            }
        }
    }

    private fun validateAddProduct() {
        val productName = binding.edtShopName.text.toString().trim()
        val description = binding.edtDesc.text.toString().trim()
        val productPrice = binding.edtProductPrice.text.toString().trim()
        val productQuantity = binding.edtProductQ.text.toString().trim()
        val shotDesc = binding.edtShotDesc.text.toString().trim()

        if (Validator.editProductValidation(
                productName, description, productPrice, productQuantity, selectedCategoryId,
                selectedColorId, selectedSizeId, imageList
            )
        ) {

            if (mainImage.isEmpty() && mainImagePath.isEmpty()) {
                AppUtils.showMsgOnlyWithoutClick(this, "Please select main image")
                return
            }
            val hashMap = HashMap<String, RequestBody>()
            hashMap["id"] = toBody(id)
            hashMap["product_name"] = toBody(productName)
            hashMap["price"] = toBody(productPrice)
            hashMap["product_quantity"] = toBody(productQuantity)
            hashMap["shortDescription"] = toBody(shotDesc)
            hashMap["description"] = toBody(description)
            hashMap["categoryId"] = toBody(selectedCategoryId)
            hashMap["colorId"] = toBody(selectedColorId)
            hashMap["sizeId"] = toBody(selectedSizeId)
            hashMap["product_highlight"] = toBody(highlightValue.toString())
            var mainImage: MultipartBody.Part? = null
            if (mainImagePath.isNotEmpty()) {
                mainImage = prepareMultiPart("mainImage", File(mainImagePath))
            }

            for (i in 0 until imageList.size) {
                if (imageList[i].image.contains(AppConstants.PRODUCT_IMAGE_URL)) {
                    arrStringMultipleImagesUploadable.remove(imageList[i].image)
                } else {
                    arrStringMultipleImagesUploadable.add(imageList[i].image)

                }
            }

            val image: ArrayList<MultipartBody.Part> = ArrayList()
            if (arrStringMultipleImagesUploadable.isNotEmpty()) {
                arrStringMultipleImagesUploadable.forEach {
                    image.add(prepareMultiPart("image", File(it)))
                }
            }

            var strIds = ""
            if (deleteImageArrayId.size > 0) {
                strIds = TextUtils.join(",", deleteImageArrayId)
                hashMap["deleteProductImageIds"] = toBody(strIds)
            }

            appViewModel.editShopProductApi(this, true, hashMap, image, mainImage)
            appViewModel.getResponse().observe(this, this)
        } else {
            AppUtils.showMsgOnlyWithoutClick(this, Validator.errorMessage)
        }
    }

    private fun showProductUpdateDialog() {
        val alertBuilder = AlertDialog.Builder(this)
        val factory = LayoutInflater.from(this)
        val view: View = factory.inflate(R.layout.dialog_product_detail_updated, null)

        val buttonOk = view.findViewById<Button>(R.id.ok)

        buttonOk.setOnClickListener {
            onBackPressed()
        }

        alertBuilder.setView(view)
        alertBuilder.show()
    }

    override fun onChanged(t: RestObservable) {
        if (t.data is CategoryListResponse) {
            val response: CategoryListResponse = t.data
            if (response.code == AppConstants.SUCCESS_CODE) {
                list.clear()
                list.addAll(response.body)
                for (i in 0 until list.size) {
                    if (selectedCategoryId == list[i].id.toString()) {
                        list[i].isSelected = true
                    }
                }
                setCategoryAdapter()
            }
        } else if (t.data is CategoryColorSizeResponse) {
            val response: CategoryColorSizeResponse = t.data
            if (response.code == AppConstants.SUCCESS_CODE) {

                colorList.clear()
                sizeList.clear()

                colorList.addAll(response.body.categoryColors)
                sizeList.addAll(response.body.categorySizes)

                if (colorList.size > 0) {
                    binding.tvProColor.visibility = View.VISIBLE
                } else {
                    binding.tvProColor.visibility = View.GONE
                }
                if (sizeList.size > 0) {
                    binding.tvProdSize.visibility = View.VISIBLE
                } else {
                    binding.tvProdSize.visibility = View.GONE
                }

                setColorAdapter()
                setSizeAdapter()

            }
        } else if (t.data is AddProductResponse) {
            showProductUpdateDialog()
        }
    }

    private fun setColorAdapter() {
        colorAdapter = ColorAdapter(colorList)
        binding.rvColor.adapter = colorAdapter

        for (i in 0 until colorList.size) {
            if (selectedColorId == colorList[i].id.toString()) {
                colorList[i].isSelected = true
            }
        }

        colorAdapter.onClickListener = { pos ->
            colorList.forEachIndexed { index, body ->

                if (pos == index) {
                    body.isSelected = true
                    selectedColorId = body.id.toString()
                } else {
                    body.isSelected = false
                }
            }
            Log.d("selectedSizeId", selectedSizeId)
            // colorList[pos].isSelected = !colorList[pos].isSelected

            colorAdapter.notifyDataSetChanged()
        }
    }

    private fun setSizeAdapter() {
        sizeAdapter = SizeAdapter(sizeList)
        binding.rvSize.adapter = sizeAdapter

        for (i in 0 until sizeList.size) {
            if (selectedSizeId == sizeList[i].id.toString()) {
                sizeList[i].isSelected = true
            }
        }


        sizeAdapter.onClickListener = { pos ->
            sizeList.forEachIndexed { index, body ->

                if (pos == index) {
                    body.isSelected = true
                    selectedSizeId = body.id.toString()
                } else {
                    body.isSelected = false
                }
            }
            Log.d("selectedSizeId", selectedSizeId)
            // sizeList[pos].isSelected = !sizeList[pos].isSelected

            sizeAdapter.notifyDataSetChanged()
        }

    }

    private fun getColorSizeApiHit() {
        val hashMap = HashMap<String, String>()
        hashMap["categoryId"] = selectedCategoryId

        appViewModel.categoryColorSizeApi(this, true, hashMap)
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

}