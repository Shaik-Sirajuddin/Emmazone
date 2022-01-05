package com.schunts.extensionfuncton

import android.content.Context
import android.net.ConnectivityManager
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.live.emmazone.R
import com.live.emmazone.base.AppController
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

fun ImageView.loadImage(path: Any) {
    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.strokeWidth = 8f
    circularProgressDrawable.centerRadius = 40f
    circularProgressDrawable.setColorSchemeColors(
        ContextCompat.getColor(
            this.context,
            R.color.black
        )
    )
    circularProgressDrawable.start()
    Glide.with(this)
        .load(path)
        .placeholder(circularProgressDrawable)
        .error(R.drawable.placeholder)
        .into(this)

}


fun toBody(string: String): RequestBody {
    return string.toRequestBody("multipart/form-data".toMediaTypeOrNull())

}

fun prepareMultiPart(partName: String, image: Any?): MultipartBody.Part {

    /*    var   imageFileBody = MultipartBody.Part.createFormData(partName, "image_"+".jpg", requestBody);
          imageArrayBody.add(imageFileBody);*/
    var requestFile: RequestBody? = null
    if (image is File) {

        requestFile = image
            .asRequestBody("/*".toMediaTypeOrNull())
    } else if (image is ByteArray) {
        requestFile = image
            .toRequestBody(
                "/*".toMediaTypeOrNull(),
                0, image.size
            )
    }
    if (image is String) {
        val attachmentEmpty = "".toRequestBody("text/plain".toMediaTypeOrNull());
        return MultipartBody.Part.createFormData(partName, "", attachmentEmpty);
    } else
        return MultipartBody.Part.createFormData(partName, (image as File).name, requestFile!!)
}

fun toast(message: String) {
    Toast.makeText(AppController.instance!!, message, Toast.LENGTH_SHORT).show()
}

fun Context.checkIfHasNetwork(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = cm.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}