package com.live.emmazone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.model.ModelDeliveryAddress
import com.live.emmazone.model.ModelNotifications
import com.live.emmazone.model.ModelShopDetailProducts
import com.live.emmazone.model.ModelWishList

class AdapterShopDetailProducts(private val list: ArrayList<ModelShopDetailProducts>) :
    RecyclerView.Adapter<AdapterShopDetailProducts.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_shopdetail_products, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val ModelShopDetailProducts = list[position]
        holder.imageProductSD.setImageResource(ModelShopDetailProducts.imageProductShopDetail)
        holder.productItemNameSD.setText(ModelShopDetailProducts.productItemName)
        holder.productItemPriceSD.setText(ModelShopDetailProducts.productItemPrice)
        holder.tvShopDetailProductBrandSD.setText(ModelShopDetailProducts.tvShopDetailProductBrand)
        holder.tvShopDetailProductTextSD.setText(ModelShopDetailProducts.tvShopDetailProductText)
        holder.tvSDDeliveryEstimateSD.setText(ModelShopDetailProducts.tvSDDeliveryEstimate)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageProductSD : ImageView = itemView.findViewById(R.id.imageProductShopDetail)
        val productItemNameSD = itemView.findViewById<TextView>(R.id.productItemName)
        val productItemPriceSD = itemView.findViewById<TextView>(R.id.productItemPrice)
        val tvShopDetailProductBrandSD= itemView.findViewById<TextView>(R.id.tvShopDetailProductBrand)
        val tvShopDetailProductTextSD= itemView.findViewById<TextView>(R.id.tvShopDetailProductText)
        val tvSDDeliveryEstimateSD= itemView.findViewById<TextView>(R.id.tvSDDeliveryEstimate)

            }
}