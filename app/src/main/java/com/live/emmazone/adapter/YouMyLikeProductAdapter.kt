package com.live.emmazone.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.activities.main.ProductDetailActivity
import com.live.emmazone.model.CartResponsModel
import com.schunts.extensionfuncton.loadImage


class YouMyLikeProductAdapter(
    val mContext: Context, private val list: ArrayList<CartResponsModel.Body.CartItem.Product>
) :
    RecyclerView.Adapter<YouMyLikeProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_shopdetail_products, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = list[position]
        val image = if(data.group!=null && !data.group!!.productImages.isNullOrEmpty()){
            data.group!!.productImages[0].image
        }
        else{
            data.mainImage
        }
        image?.let { holder.imageProductSD.loadImage(it) }
        holder.productItemNameSD.text = data.name
        holder.productItemPriceSD.text = mContext.getString(R.string.euro_symbol,data.productPrice)
        holder.tvShopDetailProductBrandSD.text = data.description
        //  holder.tvSDDeliveryEstimateSD.setText(ModelShopDetailProducts.)

        holder.itemView.setOnClickListener {
            // cellClickListener.onCellClickListener()
            val intent = Intent(mContext, ProductDetailActivity::class.java)
            intent.putExtra("productId", list[position].id.toString())
            mContext.startActivity(intent)
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
            itemView.findViewById<TextView>(R.id.rating)
        val tvSDDeliveryEstimateSD = itemView.findViewById<TextView>(R.id.tvSDDeliveryEstimate)

    }
}