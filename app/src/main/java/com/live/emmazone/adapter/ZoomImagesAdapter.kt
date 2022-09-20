package com.live.emmazone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.model.ShopProductDetailResponse
import com.live.emmazone.response_model.Product
import com.schunts.extensionfuncton.loadImage
import kotlinx.android.synthetic.main.item_zoom_image.view.*

class ZoomImagesAdapter(val imagesArrayList: ArrayList<Product.ProductImage>) :
    RecyclerView.Adapter<ZoomImagesAdapter.ZoomImageViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZoomImageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_zoom_image, parent, false)
        return ZoomImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ZoomImageViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return imagesArrayList.size
    }

    inner class ZoomImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(pos: Int) {
            itemView.zoomAgeImage.loadImage(imagesArrayList[pos].image)
        }
    }
}