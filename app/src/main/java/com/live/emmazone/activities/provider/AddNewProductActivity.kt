package com.live.emmazone.activities.provider

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import com.live.emmazone.BuildConfig
import com.live.emmazone.R
import com.live.emmazone.activities.ImageZoomActivity
import com.live.emmazone.adapter.CategoriesAdapter
import com.live.emmazone.adapter.ColorAdapter
import com.live.emmazone.adapter.ImageAdapter
import com.live.emmazone.adapter.SizeAdapter
import com.live.emmazone.databinding.ActivityAddNewProductBinding
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.model.DeliveryTemplateModel
import com.live.emmazone.model.ImageModel
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.*
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.AppUtils.Companion.setEuroLocale
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


class AddNewProductActivity : ImagePickerUtility(), Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()

    lateinit var binding: ActivityAddNewProductBinding
    private val list: ArrayList<CategoryListResponse.Body> = ArrayList()
    private var byteArray: ByteArray? = null


    private var categoryAdapter: CategoriesAdapter? = null
    private var selectedCategoryId = ""
    private var highlightValue = 1
    private var btnSaveCloseClick = false


    private lateinit var images: ArrayList<ImageModel>
    private lateinit var imageAdapter: ImageAdapter
    private val imageList = ArrayList<ProductImage>()
    private var mainImagePath = ""
    private var mainImage = ""

    private var selectedIndex = -1
    private lateinit var adapter: ArrayAdapter<String>
    private val templates = ArrayList<DeliveryTemplateModel>()
    private val templateNames = ArrayList<String>()
    override fun selectedImage(imagePath: String?, code: Int, bitmap: Bitmap?) {
        if (imagePath != null) {
            if (code == 0) {
                //0 for Main Pic
                mainImagePath = imagePath
                binding.ivShop.loadImage(imagePath)
            } else {
                imageList.add(
                    ProductImage(
                        0,
                        imagePath,
                        0,
                        0
                    )
                )
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

    private fun highlightSwitchListener() {
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

    /** Delivery templates loading and handling
     * */

    private fun loadData() {
        val body = templates[selectedIndex]
        binding.bicyclePricing.setText(body.bicycle_price.toString())
        binding.shopPricing.setText(body.shop_price.toString())
        binding.thirdPartyPricing.setText(body.logistics_price.toString())
    }

    private fun getTemplates() {
        val map = HashMap<String, String>()
        map["vendorId"] = getPreference(AppConstants.VENDOR_ID, "")
        appViewModel.getDeliveryTemplates(this, true, map)
        appViewModel.mResponse.observe(this, this)
    }

    private fun setData(body: List<DeliveryTemplateModel>) {
        templates.clear()
        templates.addAll(body)
        templateNames.clear()
        templates.forEach {
            templateNames.add(it.templateName)
        }
        adapter.notifyDataSetChanged()
    }

    private fun setCategoryAdapter() {
        categoryAdapter = CategoriesAdapter(list)
        binding.rvCategories.adapter = categoryAdapter

        categoryAdapter?.onClickListener = { pos ->
            list.forEachIndexed { index, body ->

                if (pos == index) {
                    body.isSelected = true
                    selectedCategoryId = body.id.toString()
                } else {
                    body.isSelected = false
                }
            }
            categoryAdapter?.notifyDataSetChanged()
        }

    }


    private fun setImageAdapter() {
        imageAdapter = ImageAdapter(imageList)
        binding.recyclerImages.adapter = imageAdapter

        imageAdapter.onItemClickListener = { pos, clickOn ->
            if (clickOn == "addImage") {
//                getImage(1, false)
                selectImage()
            } else if (clickOn == "viewImage") {
                val intent = Intent(this, ImageZoomActivity::class.java)
                intent.putExtra(AppConstants.IMAGE_USER_URL, imageList[pos].image)
                startActivity(intent)
            }
        }

        imageAdapter.onDeleteImage =
            { pos: Int, data: ProductImage ->
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

        binding.btnSaveContinue.setOnClickListener {
            btnSaveCloseClick = false
            validateAddProduct()
        }

        binding.btnSaveClose.setOnClickListener {
            btnSaveCloseClick = true
            validateAddProduct()
        }

        binding.ivAdd.setOnClickListener {
            getImage(0, false)
        }
        binding.scanCode.setOnClickListener {
            checkCameraPermission()
        }
        binding.ivShop.setOnClickListener {
            if (mainImagePath.isNotEmpty()) {
                val intent = Intent(this, ImageZoomActivity::class.java)
                intent.putExtra(AppConstants.IMAGE_USER_URL, mainImagePath)
                startActivity(intent)
            } else if (mainImage.isNotEmpty()) {
                val intent = Intent(this, ImageZoomActivity::class.java)
                intent.putExtra(AppConstants.IMAGE_USER_URL, mainImage)
                startActivity(intent)
            }
        }
        /** Start : Setting up autoCompleteTextview configuration */
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, templateNames)
        binding.autoCompleteTextView.setAdapter(adapter)
        binding.autoCompleteTextView.setDropDownBackgroundResource(R.color.white)
        binding.autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
            selectedIndex = i
            loadData()
        }
        /**End*/
        getTemplates()
    }

    private fun validateAddProduct() {
        val productName = binding.edtShopName.text.toString().trim()
        val shotDesc = binding.edtShotDesc.text.toString().trim()
        val description = binding.edtDesc.text.toString().trim()
        val registerCode = binding.edtRegisterCode.text.toString().trim().toLongOrNull()
        val bicycleDeliveryEnabled = binding.bicycleDeliveryCheckbox.isChecked
        val shopDeliveryEnabled = binding.selfDeliveryCheckbox.isChecked
        var bicyclePricing = binding.bicyclePricing.text.toString().trim().toIntOrNull()
        var shopPricing = binding.shopPricing.text.toString().trim().toIntOrNull()
        val thirdPartyPricing = binding.thirdPartyPricing.text.toString().trim().toIntOrNull()

        if (Validator.addProductValidation(
                productName,
                shotDesc,
                description,
                selectedCategoryId,
                imageList,
                registerCode,
                mainImagePath,
                bicycleDeliveryEnabled,
                shopDeliveryEnabled,
                bicyclePricing,
                shopPricing,
                thirdPartyPricing
            )
        ) {
            val image: ArrayList<MultipartBody.Part> = ArrayList()
            if (imageList.isNotEmpty()) {
                imageList.forEach {
                    image.add(prepareMultiPart("image", File(it.image)))
                }
            }
            shopPricing = shopPricing ?: 0
            bicyclePricing = bicyclePricing ?: 0

            val hashMap = HashMap<String, RequestBody>()
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

            val mainImage = prepareMultiPart("mainImage", File(mainImagePath))

            appViewModel.addProductGroup(this, true, hashMap, image, mainImage)
            appViewModel.getResponse().observe(this, this)
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

    override fun onResume() {
        super.onResume()
//        setEuroLocale()

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

    private fun launchEdit(product: ProductGroup) {
        val intent = Intent(this, EditProductActivity::class.java)
        intent.putExtra("group", product)
        startActivity(intent)
        finish()
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
                } else if (t.data is AddProductGroupResponse) {
                    val response: AddProductGroupResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {
                        if (btnSaveCloseClick) {
                            showAddProductDialog()
                        } else {

                            mainImagePath = ""
                            selectedCategoryId = ""
                            binding.ivShop.setImageResource(R.color.black)
                            imageList.clear()
                            imageAdapter.notifyDataSetChanged()
                            binding.edtShopName.setText("")
                            binding.edtShotDesc.setText("")
                            binding.edtDesc.setText("")

                            list.forEach {
                                it.isSelected = false
                            }
                            categoryAdapter?.notifyDataSetChanged()

                            binding.nestedScrollView.post {
                                binding.nestedScrollView.fullScroll(NestedScrollView.FOCUS_UP)
                            }
                            launchEdit(t.data.body.group)
                        }
                    }
                } else if (t.data is DeliveryTemplateResponse) {
                    val response: DeliveryTemplateResponse = t.data
                    setData(response.body)
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



