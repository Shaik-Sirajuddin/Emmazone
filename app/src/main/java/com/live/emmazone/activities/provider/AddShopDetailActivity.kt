package com.live.emmazone.activities.provider

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.live.emmazone.MainActivity
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityAddShopDetailBinding
import com.live.emmazone.utils.ToastUtils
import com.permissionx.guolindev.PermissionX

class AddShopDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddShopDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddShopDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            mainImageLayout.setOnClickListener { optionsDialog() }
        }

        binding.imageArrowback.setOnClickListener {
            onBackPressed()
        }

        binding.btnDone.setOnClickListener {

            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_profile_completed)

            dialog.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
            dialog.window?.setBackgroundDrawable(ContextCompat.getDrawable(this, android.R.color.transparent))

            val ok = dialog.findViewById<Button>(R.id.ok)

            ok.setOnClickListener {
                startActivity(Intent(this, ProviderMainActivity::class.java))
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    private fun optionsDialog() {
        val dialog = BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(this, android.R.color.transparent)
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
                    binding.ivShop.setImageBitmap(imageBitmap)
            }
        }

    private var galleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                binding.ivShop.setImageURI(data?.data)
            }
        }

}