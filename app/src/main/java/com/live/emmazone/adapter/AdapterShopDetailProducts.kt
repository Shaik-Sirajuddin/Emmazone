package com.live.emmazone.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.live.emmazone.R
import com.live.emmazone.activities.listeners.OnItemClick
import com.live.emmazone.activities.main.ProductDetailActivity
import com.live.emmazone.response_model.Product
import com.live.emmazone.response_model.SellerShopDetailResponse
import com.live.emmazone.response_model.ShopDetailResponse
import com.live.emmazone.utils.AppConstants
import com.schunts.extensionfuncton.loadImage


class AdapterShopDetailProducts(
    val mContext: Context,
    private val list: ArrayList<Product>
) :
    RecyclerView.Adapter<AdapterShopDetailProducts.ViewHolder>() {

    var onItemClick:((productId:String)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_shopdetail_products, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = list[position]
        if (data.productImages.isNotEmpty()) {
            holder.imageProductSD.loadImage(AppConstants.PRODUCT_IMAGE_URL + data.productImages[0].image)
        } else {
            holder.imageProductSD.setImageResource(R.drawable.placeholder)

        }
        holder.productItemNameSD.text = data.name
//        holder.productItemPriceSD.text = mContext.getString(R.string.euro_symbol, data.productPrice)
        holder.tvShopDetailProductBrandSD.text = data.shortDescription
        //  holder.tvSDDeliveryEstimateSD.setText(ModelShopDetailProducts.)

        holder.itemView.setOnClickListener {
            // cellClickListener.onCellClickListener()

            onItemClick?.invoke(list[position].id.toString())


        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageProductSD: ImageView = itemView.findViewById(R.id.imageProductShopDetail)
        val productItemNameSD = itemView.findViewById<TextView>(R.id.productItemName)
        val productItemPriceSD = itemView.findViewById<TextView>(R.id.productItemPrice)
        val tvShopDetailProductBrandSD =
            itemView.findViewById<TextView>(R.id.tvShopDetailProductBrand)
        val tvShopDetailProductTextSD =
            itemView.findViewById<TextView>(R.id.tvShopDetailProductText)
        val tvSDDeliveryEstimateSD = itemView.findViewById<TextView>(R.id.tvSDDeliveryEstimate)

    }
}