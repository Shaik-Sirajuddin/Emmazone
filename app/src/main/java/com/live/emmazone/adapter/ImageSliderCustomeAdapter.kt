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
import com.live.emmazone.databinding.ItemLayoutHomeNearbyShopBinding
import com.live.emmazone.databinding.RowImageSliderBinding
import com.live.emmazone.model.ShopProductDetailResponse
import com.live.emmazone.response_model.Product
import com.live.emmazone.utils.AppConstants
import com.schunts.extensionfuncton.loadImage
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlin.collections.ArrayList


class ImageSliderCustomeAdapter(
    private val context: Context,
    private val imagesArrayList: ArrayList<String>,
    private val zoomImage: Boolean = true,
    private val onClick: () -> Unit = {}
) : SliderViewAdapter<ImageSliderCustomeAdapter.ImageSliderViewHolder>() {

    override fun getCount(): Int {
        return imagesArrayList.size
    }

    inner class ImageSliderViewHolder(val binding: RowImageSliderBinding) :
        SliderViewAdapter.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            with(binding) {
                imagslideid.loadImage(imagesArrayList[pos])

                imagslideid.setOnClickListener {
                    if (zoomImage) {
                        val intent = Intent(context, ImageZoomActivity::class.java)
                        intent.putExtra(AppConstants.IMAGES_ARRAYLIST, imagesArrayList)
                        context.startActivity(intent)
                    } else {
                        onClick()
                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?): ImageSliderViewHolder {
        val binding = RowImageSliderBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return ImageSliderViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ImageSliderViewHolder, position: Int) {
        viewHolder.bind(position)
    }
}
