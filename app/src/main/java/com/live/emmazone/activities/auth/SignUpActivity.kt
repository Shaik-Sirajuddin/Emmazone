package com.live.emmazone.activities.auth

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.Window
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.live.emmazone.R
import com.live.emmazone.activities.TermsCondition
import com.live.emmazone.activities.VerificationCode
import com.live.emmazone.databinding.ActivitySignUpBinding
import com.live.emmazone.utils.ToastUtils
import com.permissionx.guolindev.PermissionX

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageArrowback.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.termsConditions.setOnClickListener {
            val intent = Intent(this, TermsCondition::class.java)
            startActivity(intent)
        }
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, VerificationCode::class.java)
            startActivity(intent)
        }
        binding.tvSignIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.apply {

            pickImage.setOnClickListener{ optionsDialog() }

            imageAdd.setOnClickListener { optionsDialog() }
        }
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
                if (imageBitmap != null)
                    binding.pickImage.setImageBitmap(imageBitmap)
            }
        }

    private var galleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                binding.pickImage.setImageURI(data?.data)
            }
        }

}