package com.live.emmazone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.response_model.Product
import com.schunts.extensionfuncton.loadImage
import kotlinx.android.synthetic.main.item_layout_shopdetail_products.view.*


class AdapterShopDetailProducts(
    val mContext: Context,
    private val list: ArrayList<Product>,
    val wishListClick : (pos:Int)->Unit,
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
        val image = if(data.group != null && !data.group!!.productImages.isNullOrEmpty()){
            data.group!!.productImages[0].image
        } else {
            data.images[0].image
        }
        if (image.isNotEmpty()) {
            holder.imageProductSD.loadImage(image)
        } else {
            holder.imageProductSD.setImageResource(R.drawable.placeholder)

        }

        holder.productItemNameSD.text = data.name
        holder.productItemPriceSD.text = mContext.getString(R.string.euro_symbol, data.productPrice)
        holder.tvShopDetailProductBrandSD.text = data.shortDescription
        holder.ratingText.text = data.productReview
        val rate = data.productReview.toFloatOrNull()
        rate?.let {
            holder.ratingBar.rating = it
        }
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(list[position].id.toString())
        }
        if(data.isLiked == 1){
            holder.itemView.heart.setImageResource(R.drawable.heart)
        }
        else{
            holder.itemView.heart.setImageResource(R.drawable.heart_unselect)
        }
        holder.itemView.heart.setOnClickListener {
            wishListClick(position)
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
        val ratingText=
            itemView.findViewById<TextView>(R.id.rating)
        val tvSDDeliveryEstimateSD = itemView.findViewById<TextView>(R.id.tvSDDeliveryEstimate)
        val ratingBar : RatingBar = itemView.findViewById(R.id.ratingBar)


    }
}