package com.live.emmazone.activities.auth

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityEditProfileBinding
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.EditProfileResponse
import com.live.emmazone.response_model.ProfileResponse
import com.live.emmazone.response_model.SignUpResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.ImagePickerUtility
import com.live.emmazone.utils.ToastUtils
import com.live.emmazone.view_models.AppViewModel
import com.permissionx.guolindev.PermissionX
import com.schunts.extensionfuncton.loadImage
import com.schunts.extensionfuncton.prepareMultiPart
import com.schunts.extensionfuncton.toBody
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

class EditProfileActivity : ImagePickerUtility(),Observer<RestObservable> {
    lateinit var binding : ActivityEditProfileBinding

    private val appViewModel: AppViewModel by viewModels()

    private var mImagePath = ""
    private var profileDetail:ProfileResponse? = null


    override fun selectedImage(imagePath: String?, code: Int) {
        if (imagePath != null) {
            mImagePath = imagePath
            binding.ivProfile.loadImage(imagePath)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
         profileDetail = intent.getSerializableExtra("profile") as ProfileResponse

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.btnUpdate.setOnClickListener {
          editProfileApi()

        }

        binding.apply {

            camera.setOnClickListener {
                getImage(0,false)
            }
        }

        binding.ivProfile.loadImage(AppConstants.IMAGE_USER_URL+profileDetail!!.body.image)

        binding.edtName.setText( profileDetail!!.body.username)
        binding.edtEditEmail.setText( profileDetail!!.body.email)
        binding.ccp.setCountryForNameCode( profileDetail!!.body.countryCode)
        binding.edtMobile.setText( profileDetail!!.body.phone)
    }

    private fun AlertDialog(){

        val alertDialog = AlertDialog.Builder(this)
        val factory = LayoutInflater.from(this)
        val view : View = factory.inflate(R.layout.dialog_profile_updated, null)

        val buttonOk= view.findViewById<Button>(R.id.ok)

        buttonOk.setOnClickListener {
            onBackPressed()
        }

        alertDialog.setView(view)
        alertDialog.show()

    }

//    private fun optionsDialog() {
//        val dialog = BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCancelable(true)
//        dialog.setCanceledOnTouchOutside(true)
//        dialog.window?.setBackgroundDrawable(
//            ContextCompat.getDrawable(
//                this,
//                android.R.color.transparent
//            )
//        )
//        dialog.setContentView(R.layout.dialog_select_photo)
//
//        val tvCamera: TextView? = dialog.findViewById(R.id.tv_camera)
//        val tvGallery: TextView? = dialog.findViewById(R.id.tv_gallery)
//        val tvCancel: TextView? = dialog.findViewById(R.id.tv_cancel)
//
//        tvCamera?.setOnClickListener {
//            dialog.dismiss()
//            openResourceWithPermissionCheck(isCameraRequest = true)
//        }
//
//        tvGallery?.setOnClickListener {
//            dialog.dismiss()
//            openResourceWithPermissionCheck(isCameraRequest = false)
//        }
//
//        tvCancel?.setOnClickListener {
//            dialog.dismiss()
//        }
//
//        dialog.show()
//    }
//
//    private fun openResourceWithPermissionCheck(isCameraRequest: Boolean) {
//        PermissionX.init(this)
//            .permissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
//            .onExplainRequestReason { scope, deniedList ->
//                scope.showRequestReasonDialog(
//                    deniedList,
//                    "You need to allow permissions, to select photo.",
//                    "Allow",
//                    "Deny"
//                )
//            }
//            .onForwardToSettings { scope, deniedList ->
//                scope.showForwardToSettingsDialog(
//                    deniedList,
//                    "You need to allow necessary permissions in Settings manually",
//                    "Open Settings",
//                    "Cancel"
//                )
//            }
//            .request { allGranted, _, _ ->
//                if (allGranted) {
//                    if (isCameraRequest)
//                        getImageFromCamera()
//                    else
//                        getImageFromGallery()
//                } else
//                    ToastUtils.showToast("Unable to perform action due to permissions")
//            }
//    }
//
//    private fun getImageFromCamera() {
//        cameraResultLauncher.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
//    }
//
//    private fun getImageFromGallery() {
//        galleryResultLauncher.launch(
//            Intent(Intent.ACTION_PICK).apply {
//                type = "image/*"
//            },
//        )
//    }
//
//    private var cameraResultLauncher =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                val data: Intent? = result.data
//                val imageBitmap = data?.extras?.get("data") as Bitmap?
//                if (imageBitmap != null)
//                    binding.ivProfile.setImageBitmap(imageBitmap)
//            }
//        }
//
//    private var galleryResultLauncher =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                val data: Intent? = result.data
//                binding.ivProfile.setImageURI(data?.data)
//            }
//        }

    private fun editProfileApi() {
         val image : MultipartBody.Part
        val name = binding.edtName.text.toString().trim()
        val mobileNo = binding.edtMobile.text.toString().trim()
        if (mImagePath.isNotEmpty())
            image = prepareMultiPart("image", File(mImagePath))
        else
         image = prepareMultiPart("image", "")

        if (Validator.editProfileValidation(
                name,
                mobileNo,
            )
        ) {

            //EditProfile API hit
            val hashMap = HashMap<String, RequestBody>()
            hashMap["name"] = toBody(name)
            hashMap["phone"] = toBody(mobileNo)
            hashMap["countryCode"] = toBody(binding.ccp.selectedCountryCodeWithPlus)


            appViewModel.editProfileApi(this, true, hashMap, image)
            appViewModel.getResponse().observe(this, this)
        } else AppUtils.showMsgOnlyWithoutClick(this, Validator.errorMessage)
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is EditProfileResponse) {
                    val response: EditProfileResponse = t.data
                    setResult(RESULT_OK)
                    AlertDialog()

                }

            }

        }
    }


}