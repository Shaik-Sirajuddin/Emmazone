package com.live.emmazone.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
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
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.live.emmazone.R
import com.permissionx.guolindev.PermissionX
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


abstract class ImagePickerUtility : AppCompatActivity() {

    private var mVideoDialog: Boolean = false
    private var mCode = 0
    private lateinit var mImageFile: File
    private val CAMERA_REQUEST_CODE = 1001
    private val CROP_PIC_REQUEST_CODE = 9090


    private val videoCameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // There are no request codes
                Log.e("VideoSelected", "RESULT_OK")
                val contentURI = result.data?.data

                val selectedVideoPath = getPath(contentURI!!)
                selectedImage(selectedVideoPath, mCode, null)
            }
        }

    private val imageCameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri = Uri.fromFile(mImageFile)
                val picturePath = getAbsolutePath(uri)
                selectedImage(picturePath, mCode, null)
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
                selectedImage(selectedVideoPath, mCode, null)
            }
        }

    private fun cropImage(uri: Uri) {

        val intent = Intent("com.android.camera.action.CROP")
        // intent.setClassName("com.android.camera", "com.android.camera.CropImage")
//                val file: File = File(picturePath)
//                val uri = Uri.fromFile(file)
        intent.data = uri
        intent.putExtra("crop", "true")
//       intent.putExtra("aspectX", 1)
//       intent.putExtra("aspectY", 1)
        intent.putExtra("outputX", 400)
        intent.putExtra("outputY", 400)
        intent.putExtra("noFaceDetection", true)
        intent.putExtra("return-data", true)
        startActivityForResult(intent, CROP_PIC_REQUEST_CODE)
    }

    private val imageGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri = result.data?.data
//                val picturePath = getAbsolutePath(uri!!)
//                selectedImage(picturePath, mCode)
                if (uri != null) {
                    cropImage(uri)
                }
            }
        }

    open fun getImage(
        code: Int, videoDialog: Boolean,
        aspectRatioX: Float = -1f,
        aspectRatioY: Float = -1f,
        hasCropAspectRatio: Boolean = false
    ) {

        //*****videoDialog -> put false for pick the Image.*****
        //*****videoDialog -> put true for pick the Video.*****
        if (hasCropAspectRatio && (aspectRatioX == -1f || aspectRatioY == -1f)) {
            Log.e("ImagePicker : GetImage", "Invalid Aspect Ratio")
            return
        }
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
                    imageDialog(aspectRatioX,aspectRatioY,hasCropAspectRatio)
                } else
                    ToastUtils.showToast("Unable to perform action due to permissions")
            }
    }


    private fun imageDialog(
        aspectRatioX: Float = -1f,
        aspectRatioY: Float = -1f,
        hasCropAspectRatio: Boolean = false
    ) {
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
                /* captureImage()*/
                if (hasCropAspectRatio){
                    ImagePicker.with(this).cameraOnly().crop(aspectRatioX, aspectRatioY)
                        .start(CAMERA_REQUEST_CODE)
                }
                else{
                    ImagePicker.with(this).cameraOnly().crop().start(CAMERA_REQUEST_CODE)
                }
            }
        }

        tvGallery?.setOnClickListener {
            dialog.dismiss()
            if (mVideoDialog) {
                openGalleryForVideo()
            } else {
                openGalleryForImage(aspectRatioX,aspectRatioY,hasCropAspectRatio)
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
            Objects.requireNonNull(this), "com.live.emmazone" + ".provider",
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


    private fun openGalleryForImage(
        aspectRatioX: Float = -1f,
        aspectRatioY: Float = -1f,
        hasCropAspectRatio: Boolean = false
    ) {
        val cropImageTask = CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON)

        if (hasCropAspectRatio) {
            cropImageTask.setAspectRatio(aspectRatioX.roundToInt(), aspectRatioY.roundToInt())
        }

        cropImageTask.start(this)
//        val intent = Intent(Intent.ACTION_PICK, Images.Media.EXTERNAL_CONTENT_URI)
//        imageGalleryLauncher.launch(intent)
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


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
            val contentURI = data?.data
            val selectedImagePath = getAbsolutePath(contentURI!!)
            selectedImage(selectedImagePath, mCode, null)
        }
        if (resultCode == RESULT_OK && requestCode == CROP_PIC_REQUEST_CODE) {
            if (data != null) {
                val extras = data.extras
                val bitmap = extras!!.getParcelable<Bitmap>("data")
                selectedImage(null, mCode, bitmap)
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri = result.uri
                selectedImage(getAbsolutePath(resultUri), mCode, null)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                Log.e("imageUtility", error.message.toString())
            }
        }
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

    abstract fun selectedImage(imagePath: String?, code: Int, bitmap: Bitmap?)
}