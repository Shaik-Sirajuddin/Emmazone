package com.live.emmazone.activities

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.live.emmazone.databinding.ActivityImageGetterBinding
import com.live.emmazone.response_model.ProductImage
import com.live.emmazone.utils.ImagePickerUtility
import com.schunts.extensionfuncton.loadImage

class ImageGetter : ImagePickerUtility() {
    private lateinit var binding : ActivityImageGetterBinding

    override fun selectedImage(imagePath: String?, code: Int, bitmap: Bitmap?) {
        val intent = Intent()
        if (imagePath != null) {
            intent.putExtra("imagePath",imagePath.toString())
            setResult(RESULT_OK,intent)
        }
        else{
            setResult(RESULT_CANCELED,intent)
        }
        finish()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageGetterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getImage(0,false)
    }
}