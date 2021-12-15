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
import com.live.emmazone.model.ModelShopDetailCategory
import com.live.emmazone.model.ModelWishList

class AdapterShopDetailCategory(private val list: ArrayList<ModelShopDetailCategory>) :
    RecyclerView.Adapter<AdapterShopDetailCategory.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_shopdetail_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val ModelShopDetailCategory = list[position]
        holder.imageCategory.setImageResource(ModelShopDetailCategory.imageShopDetailCategory)
        holder.tvItemName.setText(ModelShopDetailCategory.tvShopDetailCategoryItemName)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageCategory : ImageView = itemView.findViewById(R.id.itemShopDetailCategory)
        val tvItemName = itemView.findViewById<TextView>(R.id.tvShopDetailCategories)
            }
}