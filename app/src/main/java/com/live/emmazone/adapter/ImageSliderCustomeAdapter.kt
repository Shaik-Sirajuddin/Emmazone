package com.live.emmazone.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.viewpager.widget.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.live.emmazone.R
import com.live.emmazone.activities.ImageZoomActivity
import com.live.emmazone.model.ShopProductDetailResponse
import com.live.emmazone.utils.AppConstants
import com.schunts.extensionfuncton.loadImage
import java.util.ArrayList

/**
 * Created by Gagan Deep Singh on 9/2/2017.
 */
class ImageSliderCustomeAdapter(
    private val context: Context,
    private val imagesArrayList: ArrayList<ShopProductDetailResponse.Body.ProductImage>
) : PagerAdapter() {
    private val inflater: LayoutInflater
    var big_image: ImageView? = null
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return imagesArrayList.size
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val myImageLayout = inflater.inflate(R.layout.row_image_slider, view, false)
        val myImage = myImageLayout.findViewById<View>(R.id.imagslideid) as ImageView
        //final ProgressBar progressbar=(ProgressBar) myImageLayout.findViewById(R.id.progressbar);

        myImage.loadImage(imagesArrayList[position].image)


        myImage.setOnClickListener {
            val intent = Intent(context, ImageZoomActivity::class.java)
            intent.putExtra(AppConstants.IMAGES_ARRAYLIST, imagesArrayList)
            context.startActivity(intent)

        }

        view.addView(myImageLayout, 0)
        return myImageLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    init {
        inflater = LayoutInflater.from(context)
    }
}