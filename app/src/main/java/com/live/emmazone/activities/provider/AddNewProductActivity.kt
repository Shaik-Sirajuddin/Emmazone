package com.live.emmazone.activities.provider

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import com.live.emmazone.R
import com.live.emmazone.activities.ImageZoomActivity
import com.live.emmazone.adapter.CategoriesAdapter
import com.live.emmazone.adapter.ColorAdapter
import com.live.emmazone.adapter.ImageAdapter
import com.live.emmazone.adapter.SizeAdapter
import com.live.emmazone.databinding.ActivityAddNewProductBinding
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.model.ImageModel
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
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


class AddNewProductActivity : ImagePickerUtility(), Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()

    lateinit var binding: ActivityAddNewProductBinding
    private val list: ArrayList<CategoryListResponse.Body> = ArrayList()


    private var categoryAdapter: CategoriesAdapter? = null
    private var selectedCategoryId = ""
    private var highlightValue = 1
    private var btnSaveCloseClick = false


    private lateinit var images: ArrayList<ImageModel>
    private lateinit var imageAdapter: ImageAdapter
    private val imageList = ArrayList<ProductImage>()
    private var mainImagePath = ""
    private var mainImage = ""


    override fun selectedImage(imagePath: String?, code: Int) {
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
    }

    private fun validateAddProduct() {
        val productName = binding.edtShopName.text.toString().trim()
        val shotDesc = binding.edtShotDesc.text.toString().trim()
        val description = binding.edtDesc.text.toString().trim()

        if (Validator.addProductValidation(
                productName,
                shotDesc,
                description,
                selectedCategoryId,
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
            hashMap["shortDescription"] = toBody(shotDesc)
            hashMap["description"] = toBody(description)
            hashMap["categoryId"] = toBody(selectedCategoryId)
            hashMap["product_highlight"] = toBody(highlightValue.toString())
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

    private fun launchEdit(product: ProductGroup){
        val intent = Intent(this,EditProductActivity::class.java)
        intent.putExtra("group",product)
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
                }
            }
            else -> {}
        }
    }
}



