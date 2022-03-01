package com.live.emmazone.activities.provider

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.live.emmazone.response_model.ShopDetailResponse
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
    private var selectedSizeId = ""
    private var selectedColorId = ""
    private var highlightValue = 1

    private lateinit var imageAdapter: ImageAdapter
    private val imageList = ArrayList<ShopDetailResponse.Body.Product.ProductImage>()
    private var mainImagePath = ""
    var mainImage = ""
    private var imageslist: ArrayList<File> = ArrayList()


    override fun selectedImage(imagePath: String?, code: Int) {
        if (imagePath != null) {
            if (code == 0) {
                //0 for Main Pic
                mainImagePath = imagePath
                binding.ivShop.loadImage(imagePath)
            } else {
                imageList.add(ShopDetailResponse.Body.Product.ProductImage(0, imagePath, 0, 0))
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
        highlightSwitchListener()

        appViewModel.selectedCategoryListApi(this, true)
        appViewModel.getResponse().observe(this, this)

    }

    fun highlightSwitchListener() {
        binding.switchHighlight.setOnCheckedChangeListener { buttonView, isChecked ->
            // do something, the isChecked will be
            // true if the switch is in the On position

            if (isChecked) {
                highlightValue = 1
            } else {
                highlightValue = 0
            }
        }
    }


    private fun setCategoryAdapter() {
        val categoryAdapter = CategoriesAdapter(list)
        binding.rvCategories.adapter = categoryAdapter

        categoryAdapter.onClickListener = { pos ->
            list.forEachIndexed { index, body ->

                if (pos == index) {
                    body.isSelected = true
                    selectedCategoryId = body.id.toString()
                    selectedColorId = ""
                    selectedSizeId = ""
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


    private fun setImageAdapter() {
        imageAdapter = ImageAdapter(imageList)
        binding.recyclerImages.adapter = imageAdapter

        imageAdapter.onItemClickListener = { pos ->
            getImage(1, false)
        }

        imageAdapter.onDeleteImage =
            { pos: Int, data: ShopDetailResponse.Body.Product.ProductImage ->
                imageList.removeAt(pos)
                imageAdapter.notifyDataSetChanged()
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
            validateAddProduct()
        }

        binding.ivShop.setOnClickListener {
            getImage(0, false)
        }


    }

    private fun validateAddProduct() {
        val productName = binding.edtShopName.text.toString().trim()
        val shotDesc = binding.edtShotDesc.text.toString().trim()
        val description = binding.edtDesc.text.toString().trim()
        val productPrice = binding.edtProductPrice.text.toString().trim()
        val productQuantity = binding.edtProductQ.text.toString().trim()

        if (Validator.addProductValidation(
                productName,
                shotDesc,
                description,
                productPrice,
                productQuantity,
                selectedCategoryId,
                selectedColorId,
                selectedSizeId,
                imageList,
                mainImagePath
            )
        ) {
            val image: ArrayList<MultipartBody.Part> = ArrayList()
            if (imageList.isNotEmpty()) {
                imageList.forEach {
                    image.add(prepareMultiPart("image", File(it.image)))
                }
            }

            val hashMap = HashMap<String, RequestBody>()
            hashMap["product_name"] = toBody(productName)
            hashMap["price"] = toBody(productPrice)
            hashMap["product_quantity"] = toBody(productQuantity)
            hashMap["shortDescription"] = toBody(shotDesc)
            hashMap["description"] = toBody(description)
            hashMap["categoryId"] = toBody(selectedCategoryId)
            hashMap["colorId"] = toBody(selectedColorId)
            hashMap["sizeId"] = toBody(selectedSizeId)
            hashMap["product_highlight"] = toBody(highlightValue.toString())
            val mainImage = prepareMultiPart("mainImage", File(mainImagePath))



            appViewModel.addProductApi(this, true, hashMap, image, mainImage)
            appViewModel.getResponse().observe(this, this)
        } else {
            AppUtils.showMsgOnlyWithoutClick(this, Validator.errorMessage)
        }
    }


    /*private fun validateData() {
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

        }*/

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
                    val response: AddProductResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {
                        showAddProductDialog()
                    }
                }

            }
        }
    }
}



