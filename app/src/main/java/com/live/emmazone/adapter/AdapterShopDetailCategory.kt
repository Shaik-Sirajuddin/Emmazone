package com.live.emmazone.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.live.emmazone.R
import com.live.emmazone.databinding.ItemLayoutShopdetailCategoryBinding
import com.live.emmazone.response_model.SellerShopDetailResponse

class AdapterShopDetailCategory(
    private val list: ArrayList<SellerShopDetailResponse.Body.ShopDetails.ShopCategory>,
    private val onClick : (ind : Int) -> Unit
    ) :
    RecyclerView.Adapter<AdapterShopDetailCategory.CategoryViewHolder>() {

    private lateinit var mContext: Context
    private var ind = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        mContext = parent.context
        val binding = ItemLayoutShopdetailCategoryBinding.inflate(LayoutInflater.from(mContext))
        val holder = CategoryViewHolder(binding)
        binding.root.setOnClickListener {
            when (holder.adapterPosition) {
                list.size-1 -> {
                    if(ind!=-1){
                        notifyItemChanged(ind)
                    }
                    ind = list.size-1
                }
                ind -> {
                    notifyItemChanged(ind)
                    ind = -1
                }
                else -> {
                    notifyItemChanged(ind)
                    ind = holder.adapterPosition
                    notifyItemChanged(ind)
                }
            }
            onClick(ind)
        }
        return holder
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
            if(pos == ind){
                binding.tvShopDetailCategories.setTextColor(Color.parseColor("#000000"))
                binding.tvShopDetailCategories.typeface = Typeface.DEFAULT_BOLD
            }
            else{
                binding.tvShopDetailCategories.setTextColor(Color.parseColor("#CE000000"))
                binding.tvShopDetailCategories.typeface = Typeface.DEFAULT

            }
        }
    }
}