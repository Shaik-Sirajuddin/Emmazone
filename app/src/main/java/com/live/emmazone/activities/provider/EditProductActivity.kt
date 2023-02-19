package com.live.emmazone.activities.provider

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.live.emmazone.BuildConfig
import com.live.emmazone.R
import com.live.emmazone.activities.ImageZoomActivity
import com.live.emmazone.adapter.ImageAdapter
import com.live.emmazone.adapter.ProductVariantAdapter
import com.live.emmazone.databinding.ActivityEditProductBinding
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.model.ImageModel
import com.live.emmazone.model.ProductDeliveryModel
import com.live.emmazone.model.ProductVariant
import com.live.emmazone.model.ShopProductDetailResponse
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.*
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.AppUtils.Companion.dpToPx
import com.live.emmazone.utils.AppUtils.Companion.setEuroLocale
import com.live.emmazone.utils.AppUtils.Companion.showToast
import com.live.emmazone.utils.ImagePickerUtility
import com.live.emmazone.utils.SimpleScannerActivity
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
    val list = ArrayList<Product>()
    private lateinit var imageAdapter: ImageAdapter
    private var imageList = ArrayList<ProductImage>()
    var mainImagePath = ""
    var id = ""
    var isRefresh = false
    private var templateNo = -1
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
        adapter = ProductVariantAdapter(list, this, {
            editVariant(it)
        }, {
            deleteVariant(it)
        }, {
            addVariant()
        })

        binding.variantRecyclerView.adapter = adapter
        binding.variantRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        initListener()

        setData(productGroup!!)

        images = arrayListOf()

        highlightSwitchListener()
        appViewModel.selectedCategoryListApi(this, true)
        appViewModel.getResponse().observe(this, this)
        productDetailApiHit()

    }

    private fun addDummyData() {
        list.add(
            Product(
                Product.Category("", ""), 0, 0, 0, 0, "", "", 0, "", "", 0,
                Product.ProductColor(0, "", "", 0, 0, ""), 0, arrayListOf(),
                "", 0, "", Product.ProductSize(0, "", 0, 0, "", ""),
                "", 0, 0, SearchProductResponse.Body.Group(0, arrayListOf())
            )
        )
    }

    private fun productDetailApiHit() {
        val hashMap = java.util.HashMap<String, String>()
        hashMap["groupId"] = productGroup!!.id.toString()
        appViewModel.shopProductDetailApi(this, true, hashMap)
        appViewModel.getResponse().observe(this, this)
    }

    private fun editVariant(pos: Int) {
        isRefresh = true
        val intent = Intent(this, AddNewProductVariant::class.java)
        intent.putExtra(AddNewProductVariant.PRODUCT, productGroup!!.products[pos - 1])
        intent.putExtra(AddNewProductVariant.GROUP, productGroup)
        startActivity(intent)
    }

    private fun deleteVariant(pos: Int) {
        appViewModel.deleteProductApi(this, true, list[pos].id.toString())
        appViewModel.getResponse().observe(this, this)
    }

    private fun addVariant() {
        isRefresh = true
        val intent = Intent(this, AddNewProductVariant::class.java)
        intent.putExtra(AddNewProductVariant.GROUP, productGroup)
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
        selectedCategoryId = productGroup.categoryId.toString()
        setDeliveryData(productGroup.productDelivery)
        list.clear()
        addDummyData()
        if (!productGroup.products.isNullOrEmpty()) {
            list.addAll(productGroup.products)
        }
        adapter.notifyDataSetChanged()
        setImageAdapter()
        binding.edtRegisterCode.setText(productGroup.registerCode.toString())
    }


    override fun onResume() {
        super.onResume()
//        setEuroLocale()
        if (isRefresh) {
            productDetailApiHit()
        }
        isRefresh = false
    }

    private fun setImageAdapter() {
        imageAdapter = ImageAdapter(imageList)
        binding.recyclerImages.adapter = imageAdapter

        imageAdapter.onItemClickListener = { pos, clickOn ->
            if (clickOn == "addImage") {
//                getImage(1, false)
                selectImage()
            } else if (clickOn == "viewImage") {
                val intent = Intent(this@EditProductActivity, ImageZoomActivity::class.java)
                intent.putExtra(AppConstants.IMAGE_USER_URL, imageList[pos].image)
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
        val templateNames = resources.getStringArray(R.array.delivery_template_names)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, templateNames)

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
                if (mainImagePath.isNotEmpty()) {
                    val intent = Intent(this@EditProductActivity, ImageZoomActivity::class.java)
                    intent.putExtra(AppConstants.IMAGE_USER_URL, mainImagePath)
                    startActivity(intent)
                } else if (mainImage.isNotEmpty()) {
                    val intent = Intent(this@EditProductActivity, ImageZoomActivity::class.java)
                    intent.putExtra(AppConstants.IMAGE_USER_URL, mainImage)
                    startActivity(intent)
                }
            }
            scanCode.setOnClickListener {
                checkCameraPermission()
            }
            /** Start : Setting up autoCompleteTextview configuration */
            autoCompleteTextView.setAdapter(adapter)
            autoCompleteTextView.setDropDownBackgroundResource(R.color.white)
            autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
                templateNo = i
                loadTemplate()
            }
            /**End*/
        }


    }

    /** Delivery templates loading and handling
     * */
    private fun loadTemplate() {
        val map = HashMap<String, String>()
        map["vendorId"] = getPreference(AppConstants.VENDOR_ID, "")
        map["templateNo"] = templateNo.toString()
        appViewModel.getDeliveryTemplate(this, true, map)
        appViewModel.mResponse.observe(this, this)
    }

    private fun setTemplateData(body: DeliveryTemplateResponse.Body) {
        binding.bicyclePricing.setText(body.bicycle_price.toString())
        binding.shopPricing.setText(body.shop_price.toString())
        binding.thirdPartyPricing.setText(body.logistics_price.toString())
    }

    private fun setDeliveryData(body: ProductDeliveryModel) {
        binding.bicyclePricing.setText(body.bicycle_price.toString())
        binding.shopPricing.setText(body.shop_price.toString())
        binding.thirdPartyPricing.setText(body.logistics_price.toString())
        binding.selfDeliveryCheckbox.isChecked = body.shop_available
        binding.bicycleDeliveryCheckbox.isChecked = body.bicycle_available
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

        val bicycleDeliveryEnabled = binding.bicycleDeliveryCheckbox.isChecked
        val shopDeliveryEnabled = binding.selfDeliveryCheckbox.isChecked
        var bicyclePricing = binding.bicyclePricing.text.toString().trim().toIntOrNull()
        var shopPricing = binding.shopPricing.text.toString().trim().toIntOrNull()
        val thirdPartyPricing = binding.thirdPartyPricing.text.toString().trim().toIntOrNull()
        if (Validator.editProductValidation(
                productName, description, selectedCategoryId,
                registerCode,
                imageList,
                bicycleDeliveryEnabled,
                shopDeliveryEnabled,
                bicyclePricing,
                shopPricing,
                thirdPartyPricing
            )
        ) {

            if (mainImage.isEmpty() && mainImagePath.isEmpty()) {
                AppUtils.showMsgOnlyWithoutClick(this, "Please select main image")
                return
            }
            shopPricing = shopPricing ?: 0
            bicyclePricing = bicyclePricing ?: 0
            val hashMap = HashMap<String, RequestBody>()
            hashMap["id"] = toBody(id)
            hashMap["product_name"] = toBody(productName)
            hashMap["shortDescription"] = toBody(shotDesc)
            hashMap["description"] = toBody(description)
            hashMap["categoryId"] = toBody(selectedCategoryId)
            hashMap["product_highlight"] = toBody(highlightValue.toString())
            hashMap["registerCode"] = toBody(registerCode.toString())
            hashMap["bicycle_available"] = toBody(bicycleDeliveryEnabled.toString())
            hashMap["shop_available"] = toBody(shopDeliveryEnabled.toString())
            hashMap["logistics_price"] = toBody(thirdPartyPricing.toString())
            hashMap["bicycle_price"] = toBody(bicyclePricing.toString())
            hashMap["shop_price"] = toBody(shopPricing.toString())
            var mainImage: MultipartBody.Part? = null
            if (mainImagePath.isNotEmpty()) {
                mainImage = prepareMultiPart("mainImage", File(mainImagePath))
            }
            for (i in 0 until imageList.size) {
                if (imageList[i].image.contains(AppConstants.PRODUCT_IMAGE_URL) || imageList[i].image.contains(
                        "http"
                    )
                ) {
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

    private fun deleteVariant() {
        productDetailApiHit()
    }

    override fun onChanged(t: RestObservable) {
        when (t.status) {
            Status.SUCCESS -> {
                if (t.data is EditProductGroupResponse) {
                    showProductUpdateDialog()
                } else if (t.data is ShopProductDetailResponse) {
                    productGroup!!.products = t.data.body.products
                    setData(productGroup!!)
                } else if (t.data is CommonResponse) {
                    deleteVariant()
                } else if (t.data is DeliveryTemplateResponse) {
                    val response: DeliveryTemplateResponse = t.data
                    setTemplateData(response.body)
                }
            }
            Status.ERROR -> {
                if (t.data is EditProductGroupResponse) {
                    AppUtils.showMsgOnlyWithoutClick(this, t.data.message)
                } else if (t.data is ShopProductDetailResponse) {
                    AppUtils.showMsgOnlyWithoutClick(this, t.data.message)
                } else if (t.data is CommonResponse) {
                    AppUtils.showMsgOnlyWithoutClick(this, t.data.message)
                } else if (t.data is DeliveryTemplateResponse) {
                    AppUtils.showMsgOnlyWithoutClick(this, t.data.message)
                } else {
                    AppUtils.showMsgOnlyWithoutClick(this, "Something went wrong")
                }
            }
            else -> {}
        }
    }

    //handling camera permissions
    private val permissions = arrayOf(Manifest.permission.CAMERA)

    private fun hasPermissionsCheck(permissions: Array<String>): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(
            this,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkPermissionDenied(permissions: String) {
        if (shouldShowRequestPermissionRationale(permissions)) {
            val mBuilder = AlertDialog.Builder(this)
            val dialog: AlertDialog =
                mBuilder.setTitle(R.string.alert).setMessage(R.string.permissionRequired)
                    .setPositiveButton(
                        R.string.ok
                    ) { dialog, which -> requestPermission() }
                    .setNegativeButton(
                        R.string.cancel
                    ) { dialog, which ->

                    }.create()
            dialog.setOnShowListener {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                    ContextCompat.getColor(
                        this, R.color.green
                    )
                )
            }
            dialog.show()
        } else {
            val builder = AlertDialog.Builder(this)
            val dialog: AlertDialog =
                builder.setTitle(R.string.alert).setMessage(R.string.permissionRequired)
                    .setCancelable(
                        false
                    )
                    .setPositiveButton(R.string.openSettings) { dialog, which ->
//finish()
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts(
                                "package",
                                BuildConfig.APPLICATION_ID + ".provider",
                                null
                            )
                        )
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }.create()
            dialog.setOnShowListener {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                    ContextCompat.getColor(
                        this, R.color.green
                    )
                )
            }
            dialog.show()
        }
    }

    private val scanLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val code = result.data!!.getStringExtra(AppConstants.ORDER_ID).toString().trim()
                binding.edtRegisterCode.setText(code)
            }
        }
    private val cameraPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

            if (permissions.isNotEmpty()) {
                permissions.entries.forEach {
                    Log.d("permissions", "${it.key} = ${it.value}")
                }

                val camera = permissions[Manifest.permission.CAMERA]

                if (camera == true) {
                    Log.e("permissions", "Permission Granted Successfully")
                    val intent = Intent(this, SimpleScannerActivity::class.java)
                    scanLauncher.launch(intent)
                } else {
                    Log.e("permissions", "Permission not granted")
                    checkCameraPermission()
                }

            }

        }

    private fun checkCameraPermission() {

        if (hasPermissionsCheck(permissions)) {
            Log.e("Permissions", "Permissions Granted")
            val intent = Intent(this, SimpleScannerActivity::class.java)
            scanLauncher.launch(intent)
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            checkPermissionDenied(Manifest.permission.CAMERA)
        } else {
            Log.e("Permissions", "Request for Permissions")
            requestPermission()
        }
    }

    private fun requestPermission() {
        cameraPermissions.launch(permissions)
    }
}