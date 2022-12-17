package com.live.emmazone.activities.provider

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.activities.ImageZoomActivity
import com.live.emmazone.adapter.ImageAdapter
import com.live.emmazone.adapter.ProductVariantAdapter
import com.live.emmazone.databinding.ActivityEditProductBinding
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.model.ImageModel
import com.live.emmazone.model.ProductVariant
import com.live.emmazone.model.ShopProductDetailResponse
import com.live.emmazone.net.RestObservable
import com.live.emmazone.response_model.*
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.AppUtils.Companion.setEuroLocale
import com.live.emmazone.utils.ImagePickerUtility
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.loadImage
import com.schunts.extensionfuncton.prepareMultiPart
import com.schunts.extensionfuncton.toBody
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.api.widget.Widget
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class EditProductActivity : ImagePickerUtility(), Observer<RestObservable> {

    private var mainImage = ""
    private lateinit var images: ArrayList<ImageModel>
    private var selectedCategoryId = ""
    private var highlightValue = 1
    var deleteImageArrayId = ArrayList<String>()
    private var arrStringMultipleImagesUploadable: ArrayList<String> = ArrayList()
    private val appViewModel: AppViewModel by viewModels()
    private lateinit var adapter: ProductVariantAdapter
    var productGroup: ProductGroup? = null
    lateinit var binding: ActivityEditProductBinding
    val list  = ArrayList<Product>()
    private lateinit var imageAdapter: ImageAdapter
    private var imageList = ArrayList<ProductImage>()
    var mainImagePath = ""
    var id = ""
    var isRefresh = false

    override fun selectedImage(imagePath: String?, code: Int, bitmap: Bitmap?) {
        if (imagePath != null) {
            if (code == 0) {
                //0 for Main Pic
                mainImagePath = imagePath.toString()
                binding.ivShop.loadImage(imagePath)
            } else {
                imageList.add(ProductImage(0, imagePath, 0, 0))
                imageAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productGroup = intent.extras?.get("group") as ProductGroup
        addDummyData()
//        if(!productGroup!!.products.isNullOrEmpty())
//        list.addAll(productGroup!!.products)
        adapter = ProductVariantAdapter(list,this,{
            editVariant(it)
        },{
            deleteVariant(it)
        },{
            addVariant()
        })
        binding.variantRecyclerView.adapter = adapter
        binding.variantRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        setData(productGroup!!)

        images = arrayListOf()

        initListener()
        highlightSwitchListener()
        appViewModel.selectedCategoryListApi(this, true)
        appViewModel.getResponse().observe(this, this)
        productDetailApiHit()

    }
    private fun addDummyData(){
        list.add(
            Product(
                Product.Category("",""),0,0,0,0,"","",0,"","",0,
                Product.ProductColor(0,"","",0,0,""),0, arrayListOf() ,
                "",0,"",Product.ProductSize(0,"",0,0,"",""),
                "",0,0,SearchProductResponse.Body.Group(0, arrayListOf()))
        )
    }
    private fun productDetailApiHit() {
        val hashMap = java.util.HashMap<String, String>()
        hashMap["groupId"] = productGroup!!.id.toString()
        appViewModel.shopProductDetailApi(this, true, hashMap)
        appViewModel.getResponse().observe(this, this)
    }
    private fun editVariant(pos:Int){
        isRefresh = true
        val intent = Intent(this,AddNewProductVariant::class.java)
        intent.putExtra(AddNewProductVariant.PRODUCT,productGroup!!.products[pos-1])
        intent.putExtra(AddNewProductVariant.GROUP,productGroup)
        startActivity(intent)
    }
    private fun deleteVariant(pos:Int){
        appViewModel.deleteProductApi(this,true,list[pos].id.toString())
        appViewModel.getResponse().observe(this,this)
    }
    private fun addVariant(){
        isRefresh = true
        val intent = Intent(this,AddNewProductVariant::class.java)
        intent.putExtra(AddNewProductVariant.GROUP,productGroup)
        startActivity(intent)
    }
    private fun highlightSwitchListener() {
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

    private fun setData(productGroup: ProductGroup) {

        productGroup.mainImage?.let { mainImage = it }
        id = productGroup.id.toString()
        imageList.clear()
        imageList.addAll(productGroup.productImages)
        productGroup.mainImage?.let { binding.ivShop.loadImage(it) }
        binding.edtShopName.setText(productGroup.name)
        binding.edtShotDesc.setText(productGroup.shortDescription)
        binding.edtDesc.setText(productGroup.description)
        binding.edtRegisterCode.setText(productGroup.registerCode.toString())
        selectedCategoryId = productGroup.categoryId.toString()
        list.clear()
        addDummyData()
        if(!productGroup.products.isNullOrEmpty()){
            list.addAll(productGroup.products)
        }
      adapter.notifyDataSetChanged()
        setImageAdapter()
    }

    override fun onResume() {
        super.onResume()
//        setEuroLocale()
        if(isRefresh){
            productDetailApiHit()
        }
        isRefresh = false
    }
    private fun setImageAdapter() {
        imageAdapter = ImageAdapter(imageList)
        binding.recyclerImages.adapter = imageAdapter

        imageAdapter.onItemClickListener = { pos,clickOn ->
            if (clickOn == "addImage"){
//                getImage(1, false)
                selectImage()
            }else if (clickOn == "viewImage"){
                val intent = Intent(this@EditProductActivity,ImageZoomActivity::class.java)
                intent.putExtra(AppConstants.IMAGE_USER_URL,imageList[pos].image)
                startActivity(intent)
            }
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

            ivAdd.setOnClickListener {
                getImage(0, false)
            }

            ivShop.setOnClickListener {
                if (mainImagePath.isNotEmpty()){
                    val intent = Intent(this@EditProductActivity,ImageZoomActivity::class.java)
                    intent.putExtra(AppConstants.IMAGE_USER_URL,mainImagePath)
                    startActivity(intent)
                }else if (mainImage.isNotEmpty()){
                    val intent = Intent(this@EditProductActivity,ImageZoomActivity::class.java)
                    intent.putExtra(AppConstants.IMAGE_USER_URL,mainImage)
                    startActivity(intent)
                }
            }
        }
    }

    private fun selectImage() {
        Album.image(this)
            .multipleChoice()
            .camera(true)
            .columnCount(4)
            .widget(Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build())
            .onResult { result ->
                /*mAlbumFiles.clear()
                mAlbumFiles.addAll(result)*/

                result.forEach {
                    imageList.add(
                        ProductImage(
                            0,
                            it.path,
                            0,
                            0
                        )
                    )
                }
                imageAdapter.notifyDataSetChanged()

            }.onCancel {
            }.start()
    }

    private fun validateAddProduct() {
        val productName = binding.edtShopName.text.toString().trim()
        val description = binding.edtDesc.text.toString().trim()
        val shotDesc = binding.edtShotDesc.text.toString().trim()
        val registerCode = binding.edtRegisterCode.text.toString().trim().toIntOrNull()

        if (Validator.editProductValidation(
                productName, description,selectedCategoryId,
                registerCode,
                imageList
            )
        ) {

            if (mainImage.isEmpty() && mainImagePath.isEmpty()) {
                AppUtils.showMsgOnlyWithoutClick(this, "Please select main image")
                return
            }
            val hashMap = HashMap<String, RequestBody>()
            hashMap["id"] = toBody(id)
            hashMap["product_name"] = toBody(productName)
            hashMap["shortDescription"] = toBody(shotDesc)
            hashMap["description"] = toBody(description)
            hashMap["categoryId"] = toBody(selectedCategoryId)
            hashMap["product_highlight"] = toBody(highlightValue.toString())
            hashMap["registerCode"] = toBody(registerCode.toString())
            var mainImage: MultipartBody.Part? = null
            if (mainImagePath.isNotEmpty()) {
                mainImage = prepareMultiPart("mainImage", File(mainImagePath))
            }
            for (i in 0 until imageList.size) {
                if (imageList[i].image.contains(AppConstants.PRODUCT_IMAGE_URL) || imageList[i].image.contains("http")) {
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
            appViewModel.editProductGroup(this, true, hashMap, image, mainImage)
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

        alertBuilder.setView(view)

        val dialog = alertBuilder.create()
        dialog.show()

        buttonOk.setOnClickListener {
            dialog.dismiss()
        }
    }
    private fun deleteVariant(){
        productDetailApiHit()
    }
    override fun onChanged(t: RestObservable) {
        if (t.data is EditProductGroupResponse) {
            showProductUpdateDialog()
        }
        else if ( t.data is ShopProductDetailResponse){
            productGroup!!.products = t.data.body.products
            setData(productGroup!!)
        }
        else if( t.data is CommonResponse){
            deleteVariant()
        }
    }

}