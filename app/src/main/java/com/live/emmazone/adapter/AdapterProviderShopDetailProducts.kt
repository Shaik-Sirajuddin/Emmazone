package com.live.emmazone.adapter

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.model.ModelProShopDetailProducts
import com.makeramen.roundedimageview.RoundedImageView

class AdapterProviderShopDetailProducts(private val list: ArrayList<ModelProShopDetailProducts>) :
    RecyclerView.Adapter<AdapterProviderShopDetailProducts.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.item_provider_shopdetail_products, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

      val ModelProShopDetailProducts = list[position]

        if (position == 0)
        {
           holder.imageEditSDProduct.visibility= View.GONE
           holder.imageDelete.visibility= View.GONE
        }

        holder.imageProductSD.setImageResource(ModelProShopDetailProducts.imageProductShopDetail)
        holder.imageEditSDProduct.setImageResource(ModelProShopDetailProducts.imgEdit)
        holder.imageDelete.setImageResource(ModelProShopDetailProducts.imgDelete)
        holder.productItemNameSD.setText(ModelProShopDetailProducts.productItemName)
        holder.productItemPriceSD.setText(ModelProShopDetailProducts.productItemPrice)
        holder.tvShopDetailProductBrandSD.setText(ModelProShopDetailProducts.tvShopDetailProductBrand)
        holder.tvShopDetailProductText.setText(ModelProShopDetailProducts.tvShopDetailProductText)
        holder.tvSDDeliveryEstimateSD.setText(ModelProShopDetailProducts.tvSDDeliveryEstimate)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageProductSD : RoundedImageView = itemView.findViewById(R.id.imageProductShopDetail)
        val imageEditSDProduct : ImageView = itemView.findViewById(R.id.imgEdit)
        val imageDelete : ImageView = itemView.findViewById(R.id.imgDelete)
        val productItemNameSD = itemView.findViewById<TextView>(R.id.productItemName)
        val productItemPriceSD = itemView.findViewById<TextView>(R.id.productItemPrice)
        val tvShopDetailProductBrandSD= itemView.findViewById<TextView>(R.id.tvShopDetailProductBrand)
        val tvShopDetailProductText= itemView.findViewById<TextView>(R.id.tvShopDetailProductText)
        val tvSDDeliveryEstimateSD= itemView.findViewById<TextView>(R.id.tvSDDeliveryEstimate)

            }
}