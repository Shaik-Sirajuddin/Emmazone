package com.live.emmazone.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.live.emmazone.R
import com.live.emmazone.adapter.ZoomImagesAdapter
import com.live.emmazone.model.ShopProductDetailResponse
import com.live.emmazone.utils.AppConstants
import com.schunts.extensionfuncton.loadImage
import kotlinx.android.synthetic.main.activity_image_zoom.*
import kotlinx.android.synthetic.main.item_zoom_image.view.*


class ImageZoomActivity : AppCompatActivity() {

    private var imagesArrayList: ArrayList<ShopProductDetailResponse.Body.Product.ProductImage> =
        ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_zoom)

        if (intent.getSerializableExtra(AppConstants.IMAGES_ARRAYLIST) != null) {
            imagesArrayList = intent.getSerializableExtra(AppConstants.IMAGES_ARRAYLIST) as
                    ArrayList<ShopProductDetailResponse.Body.Product.ProductImage>

            rvZoomImage.visibility = View.VISIBLE
            zoomAgeImage.visibility = View.GONE

        } else if (intent.getStringExtra(AppConstants.IMAGE_USER_URL) != null) {
            rvZoomImage.visibility = View.GONE
            zoomAgeImage.visibility = View.VISIBLE
            zoomAgeImage.loadImage(intent.getStringExtra(AppConstants.IMAGE_USER_URL)!!)
        }

        rvZoomImage.adapter = ZoomImagesAdapter(imagesArrayList)
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rvZoomImage)
    }
}