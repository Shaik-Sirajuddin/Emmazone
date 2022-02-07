package com.live.emmazone.activities.provider

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.live.emmazone.R
import com.live.emmazone.activities.listeners.OnActionListener
import com.live.emmazone.adapter.ImageAdapter
import com.live.emmazone.databinding.ActivityAddNewProductBinding
import com.live.emmazone.model.ImageModel
import com.live.emmazone.utils.ToastUtils
import com.permissionx.guolindev.PermissionX
import android.widget.*
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.adapter.ColorSizeAdapter
import com.live.emmazone.model.ColorSizeModel
import com.live.emmazone.net.RestObservable
import com.live.emmazone.view_models.AppViewModel


class AddNewProductActivity : AppCompatActivity(),Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()

    lateinit var binding: ActivityAddNewProductBinding
    var isNotifyOn = true
    private lateinit var images: ArrayList<ImageModel>
    private lateinit var imageAdapter: ImageAdapter

    private var isMainPhoto = true

    private val colorList = ArrayList<ColorSizeModel>()
    private val sizeList = ArrayList<ColorSizeModel>()
    private lateinit var colorAdapter: ColorSizeAdapter
    private lateinit var sizeAdapter: ColorSizeAdapter

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        images = arrayListOf()

        initListener()
        initAdapter()
        setColorSizeAdapter()

        binding.tvInfo.setOnClickListener {
            showPopup(binding.tvInfo)
        }
    }

    private fun setColorSizeAdapter() {
        colorList.add(ColorSizeModel())
        sizeList.add(ColorSizeModel())


        colorAdapter = ColorSizeAdapter(colorList)
        sizeAdapter = ColorSizeAdapter(sizeList)
        binding.rvColor.adapter = colorAdapter
        binding.rvSize.adapter = sizeAdapter

    }


    private fun initAdapter() {

        val onActionListener = object : OnActionListener<ImageModel> {
            override fun notify(model: ImageModel, position: Int) {
                isMainPhoto = false
                optionsDialog()
            }
        }

        imageAdapter = ImageAdapter(this, images, onActionListener)
        binding.recyclerImages.adapter = imageAdapter
    }

    private fun initListener() {
        binding.apply {

            back.setOnClickListener {
                onBackPressed()
            }

            btnSave.setOnClickListener {
                showAddProductDialog()
            }

            ivShop.setOnClickListener {
                isMainPhoto = true
                optionsDialog()
            }

            addColour.setOnClickListener {
                colorList.add(ColorSizeModel())
                colorAdapter.notifyDataSetChanged()
            }

            productSize.setOnClickListener {
                sizeList.add(ColorSizeModel())
                sizeAdapter.notifyDataSetChanged()
            }
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

    private fun optionsDialog() {
        val dialog = BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this,
                android.R.color.transparent
            )
        )
        dialog.setContentView(R.layout.dialog_select_photo)

        val tvCamera: TextView? = dialog.findViewById(R.id.tv_camera)
        val tvGallery: TextView? = dialog.findViewById(R.id.tv_gallery)
        val tvCancel: TextView? = dialog.findViewById(R.id.tv_cancel)

        tvCamera?.setOnClickListener {
            dialog.dismiss()
            openResourceWithPermissionCheck(isCameraRequest = true)
        }

        tvGallery?.setOnClickListener {
            dialog.dismiss()
            openResourceWithPermissionCheck(isCameraRequest = false)
        }

        tvCancel?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun openResourceWithPermissionCheck(isCameraRequest: Boolean) {
        PermissionX.init(this)
            .permissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    "You need to allow permissions, to select photo.",
                    "Allow",
                    "Deny"
                )
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    "You need to allow necessary permissions in Settings manually",
                    "Open Settings",
                    "Cancel"
                )
            }
            .request { allGranted, _, _ ->
                if (allGranted) {
                    if (isCameraRequest)
                        getImageFromCamera()
                    else
                        getImageFromGallery()
                } else
                    ToastUtils.showToast("Unable to perform action due to permissions")
            }
    }

    private fun getImageFromCamera() {
        cameraResultLauncher.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
    }

    private fun getImageFromGallery() {
        galleryResultLauncher.launch(
            Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            },
        )
    }

    private var cameraResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val imageBitmap = data?.extras?.get("data") as Bitmap?
                val uri = getImageUri(imageBitmap)
                addImageToList(uri)
            }
        }

    private var galleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                addImageToList(data?.data)
            }
        }

    private fun addImageToList(uri: Uri?) {
        if (isMainPhoto)
            binding.ivShop.setImageURI(uri)
        else {
            val model = ImageModel(imgUrl = uri)
            images.add(model)
            imageAdapter?.notifyDataSetChanged()
        }

        binding.recyclerImages.scrollToPosition(images.size)
    }

    private fun getImageUri(inImage: Bitmap?): Uri? {
        val outImage = Bitmap.createScaledBitmap(inImage!!, 1000, 1000, true)
        val path = MediaStore.Images.Media.insertImage(
            baseContext.contentResolver,
            outImage,
            "Title",
            null
        )
        return Uri.parse(path)
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

    }
}


