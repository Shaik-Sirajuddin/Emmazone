package com.live.emmazone.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.util.Log
import android.view.Window
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.live.emmazone.BuildConfig
import com.live.emmazone.R
import com.permissionx.guolindev.PermissionX
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


abstract class ImagePickerUtility : AppCompatActivity() {

    private var mVideoDialog: Boolean = false
    private var mCode = 0
    private lateinit var mImageFile: File


    private val videoCameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // There are no request codes
                Log.e("VideoSelected", "RESULT_OK")
                val contentURI = result.data?.data

                val selectedVideoPath = getPath(contentURI!!)
                selectedImage(selectedVideoPath, mCode)
            }
        }

    private val imageCameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri = Uri.fromFile(mImageFile)
                val picturePath = getAbsolutePath(uri)
                selectedImage(picturePath, mCode)
            }

        }


    private val videoGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // There are no request codes
                Log.e("VideoSelected", "RESULT_OK")
                val contentURI = result.data?.data

                val selectedVideoPath = getPath(contentURI!!)
//                val dummyPath = "/storage/emulated/0/Movies/Instagram/VID_53050323_203748_988.mp4"
                selectedImage(selectedVideoPath, mCode)
            }
        }

    private val imageGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri = result.data?.data
                val picturePath = getAbsolutePath(uri!!)
                selectedImage(picturePath, mCode)
            }
        }

    open fun getImage(code: Int, videoDialog: Boolean) {

        //*****videoDialog -> put false for pick the Image.*****
        //*****videoDialog -> put true for pick the Video.*****

        mCode = code
        mVideoDialog = videoDialog

        PermissionX.init(this)
            .permissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )
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
                    imageDialog()
                } else
                    ToastUtils.showToast("Unable to perform action due to permissions")
            }
    }


    private fun imageDialog() {
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
            if (mVideoDialog) {
                captureVideo()
            } else {
                captureImage()
            }
        }

        tvGallery?.setOnClickListener {
            dialog.dismiss()
            if (mVideoDialog) {
                openGalleryForVideo()
            } else {
                openGalleryForImage()
            }
        }

        tvCancel?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    private fun captureVideo() {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        videoCameraLauncher.launch(intent)

    }

    private fun captureImage() {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        try {
            createImageFile(this, imageFileName, ".jpg")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val fileUri = FileProvider.getUriForFile(
            Objects.requireNonNull(this), BuildConfig.APPLICATION_ID + ".provider",
            mImageFile
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        imageCameraLauncher.launch(intent)
    }


    private fun openGalleryForVideo() {
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_PICK
        videoGalleryLauncher.launch(
            Intent.createChooser(intent, "Select Video")
        )

    }


    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK, Images.Media.EXTERNAL_CONTENT_URI)
        imageGalleryLauncher.launch(intent)
    }

    @Throws(IOException::class)
    fun createImageFile(context: Context, name: String, extension: String) {
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        mImageFile = File.createTempFile(
            name,
            extension,
            storageDir
        )
    }


    //------------------------Return Uri file to String Path ------------------//
    @SuppressLint("Recycle")
    private fun getAbsolutePath(uri: Uri): String {
        if ("content".equals(uri.scheme, ignoreCase = true)) {
            val projection = arrayOf("_data")
            val cursor: Cursor?
            try {
                cursor = contentResolver.query(uri, projection, null, null, null)
                val columnIndex = cursor!!.getColumnIndexOrThrow("_data")
                if (cursor.moveToFirst()) {
                    return cursor.getString(columnIndex)
                }
            } catch (e: Exception) {
                // Eat it
                e.printStackTrace()
            }
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path!!
        }
        return ""
    }

    private fun getPath(uri: Uri?): String? {
        val projection = arrayOf(MediaStore.Video.Media.DATA)
        val cursor: Cursor? =
            contentResolver.query(uri!!, projection, null, null, null)
        return if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            val column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(column_index)
        } else null
    }

    abstract fun selectedImage(imagePath: String?, code: Int)
}