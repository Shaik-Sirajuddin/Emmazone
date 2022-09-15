package com.schunts.extensionfuncton

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.amulyakhare.textdrawable.TextDrawable
import com.bumptech.glide.Glide
import com.live.emmazone.R
import com.live.emmazone.base.AppController
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
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
    } else if(image is File)
        return MultipartBody.Part.createFormData(partName, (image).name, requestFile!!)
    else
        return MultipartBody.Part.createFormData(partName, "profile", requestFile!!)
}

fun toast(message: String) {
    Toast.makeText(AppController.instance!!, message, Toast.LENGTH_SHORT).show()
}

fun Context.checkIfHasNetwork(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = cm.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}
fun letterDrawable(letter: String): TextDrawable {

    return TextDrawable.builder()
        .beginConfig()
        .textColor(Color.WHITE)
        .fontSize(50)
        .bold()
        .toUpperCase()
        .endConfig()
        .buildRect(letter,Color.parseColor("#076e0e"))
}
fun letterByteArray(letter: String): ByteArray {
    val drawable = letterDrawable(letter)
    val bitmap = drawableToBitmap(drawable)
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    return stream.toByteArray()
}
fun drawableToBitmap(drawable: Drawable): Bitmap {
    if (drawable is BitmapDrawable) {
        return drawable.bitmap
    }
    var width = drawable.intrinsicWidth
    width = if (width > 0) width else 200
    var height = drawable.intrinsicHeight
    height = if (height > 0) height else 200
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}