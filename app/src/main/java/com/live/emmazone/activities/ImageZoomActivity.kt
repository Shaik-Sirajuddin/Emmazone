package com.live.emmazone.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.live.emmazone.R
import com.live.emmazone.adapter.ZoomImagesAdapter
import com.live.emmazone.model.ShopProductDetailResponse
import com.live.emmazone.utils.AppConstants
import kotlinx.android.synthetic.main.activity_image_zoom.*

class ImageZoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_zoom)

        val imagesArrayList = intent.getSerializableExtra(AppConstants.IMAGES_ARRAYLIST) as
                ArrayList<ShopProductDetailResponse.Body.ProductImage>

        rvZoomImage.adapter = ZoomImagesAdapter(imagesArrayList)

    }
}