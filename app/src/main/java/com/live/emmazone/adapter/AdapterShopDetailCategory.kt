package com.live.emmazone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.live.emmazone.R
import com.live.emmazone.databinding.ItemLayoutShopdetailCategoryBinding
import com.live.emmazone.model.ModelShopDetailCategory
import com.live.emmazone.model.sellerShopDetails.Category
import com.live.emmazone.response_model.ShopDetailResponse
import com.live.emmazone.utils.AppConstants
import com.schunts.extensionfuncton.loadImage

class AdapterShopDetailCategory(private val list: ArrayList<Category>) :
    RecyclerView.Adapter<AdapterShopDetailCategory.CategoryViewHolder>() {

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        mContext = parent.context
        val binding = ItemLayoutShopdetailCategoryBinding.inflate(LayoutInflater.from(mContext))
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class CategoryViewHolder(val binding: ItemLayoutShopdetailCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            val category = list[pos]
            Glide.with(mContext).load(category.categoryImage).
            placeholder(R.drawable.placeholder).into(binding.itemShopDetailCategory)
            binding.tvShopDetailCategories.text = category.categoryName
        }
    }
}