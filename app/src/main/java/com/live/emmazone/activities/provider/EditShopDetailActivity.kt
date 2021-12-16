package com.live.emmazone.activities.provider

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterEditShop
import com.live.emmazone.databinding.ActivityEditShopDetailBinding
import com.live.emmazone.model.ModelEditShopCategory
import com.live.emmazone.utils.ToastUtils
import com.permissionx.guolindev.PermissionX

class EditShopDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityEditShopDetailBinding
    val list = ArrayList<ModelEditShopCategory>()
    lateinit var adapter: AdapterEditShop

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditShopDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.imageArrowback.setOnClickListener {
            onBackPressed()
        }
        binding.btnUpdate.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            val factory = LayoutInflater.from(this)
            val view: View = factory.inflate(R.layout.dialog_shop_profile_updated, null)

            val buttonOk = view.findViewById<Button>(R.id.ok)

            buttonOk.setOnClickListener {
                onBackPressed()
            }

            alertDialog.setView(view)
            alertDialog.show()
        }

        binding.rvEditShop.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        list.add(ModelEditShopCategory(R.drawable.shoe_round, "Shoes", "Edit Image"))
        list.add(ModelEditShopCategory(R.drawable.gogg, "Goggles", "Edit Image"))
        list.add(ModelEditShopCategory(R.drawable.clock3, "Watches", "Edit Image"))

        binding.rvEditShop.adapter = AdapterEditShop(list)

        binding.apply {

            mainImageLayout.setOnClickListener { optionsDialog() }
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