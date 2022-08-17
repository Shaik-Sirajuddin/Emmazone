package com.live.emmazone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.model.*

class AdapterEditShop(private val list: ArrayList<ModelEditShopCategory>) :
    RecyclerView.Adapter<AdapterEditShop.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.item_edit_shop_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val ModelEditShopCategory = list[position]
        holder.imgCat.setImageResource(ModelEditShopCategory.imgCategory)
        holder.tvproItemName.setText(ModelEditShopCategory.tvCategoryName)
        holder.tvEditImage.setText(ModelEditShopCategory.tvEditImage)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgCat : ImageView = itemView.findViewById(R.id.imgCategory)
        val tvproItemName = itemView.findViewById<TextView>(R.id.tvCategoryName)
        val tvEditImage = itemView.findViewById<TextView>(R.id.tvEditImage)

            }
}