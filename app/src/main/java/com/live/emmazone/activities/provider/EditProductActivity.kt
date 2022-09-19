package com.live.emmazone.activities.provider

import android.content.Intent
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
import com.live.emmazone.net.RestObservable
import com.live.emmazone.response_model.*
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
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
    var productData: Product? = null
    lateinit var binding: ActivityEditProductBinding
    val list  = ArrayList<ProductVariant>()
    private lateinit var imageAdapter: ImageAdapter
    private var imageList = ArrayList<Product.ProductImage>()
    var mainImagePath = ""
    var id = ""

    override fun selectedImage(imagePath: String?, code: Int) {
        if (imagePath != null) {
            if (code == 0) {
                //0 for Main Pic
                mainImagePath = imagePath
                binding.ivShop.loadImage(imagePath)
            } else {
                imageList.add(Product.ProductImage(0, imagePath, 0, 0))
                imageAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productData = intent.extras?.get("productData") as Product
        list.add(ProductVariant(0,0,0,"",0,"","",""))
        list.addAll(productData!!.productVariants)
        adapter = ProductVariantAdapter(list,this,{
            //editVariant(it)
        },{
            deleteVariant(it)
        },{
            addVariant()
        })
        binding.variantRecyclerView.adapter = adapter
        binding.variantRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        setData(productData!!)

        images = arrayListOf()

        initListener()
        highlightSwitchListener()
        appViewModel.selectedCategoryListApi(this, true)
        appViewModel.getResponse().observe(this, this)

    }
    private fun editVariant(pos:Int){
        val intent = Intent(this,AddNewProductVariant::class.java)
        intent.putExtra(AddNewProductVariant.PRODUCT_VARIANT,productData!!.productVariants[pos-1])
        intent.putExtra(AddNewProductVariant.CATEGORY_ID,productData!!.categoryId)
        intent.putExtra(AddNewProductVariant.PRODUCT_ID,productData!!.id)
        startActivity(intent)
    }
    private fun deleteVariant(pos:Int){

    }
    private fun addVariant(){
        val intent = Intent(this,AddNewProductVariant::class.java)
        intent.putExtra(AddNewProductVariant.CATEGORY_ID,productData!!.categoryId)
        intent.putExtra(AddNewProductVariant.PRODUCT_ID,productData!!.id)
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

    private fun setData(productData: Product) {
        productData.mainImage.let { mainImage = it }
        id = productData.id.toString()
        imageList.addAll(productData.productImages)
        productData.mainImage.let { binding.ivShop.loadImage(it) }
        binding.edtShopName.setText(productData.name)
        binding.edtShotDesc.setText(productData.shortDescription)
        binding.edtDesc.setText(productData.description)
        selectedCategoryId = productData.categoryId.toString()
        setImageAdapter()
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
                        Product.ProductImage(
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

        if (Validator.editProductValidation(
                productName, description,selectedCategoryId,
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
        if (t.data is AddProductResponse) {
            showProductUpdateDialog()
        }
    }



}