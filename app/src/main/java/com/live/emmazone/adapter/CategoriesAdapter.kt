package com.live.emmazone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.databinding.ItemCategoryBinding
import com.live.emmazone.response_model.CategoryListResponse
import com.live.emmazone.utils.AppConstants
import com.schunts.extensionfuncton.loadImage

class CategoriesAdapter(val list: MutableList<CategoryListResponse.Body>) :
    RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    var onClickListener:((pos:Int,selectedid:CategoryListResponse.Body)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context))
        return CategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class CategoriesViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pos: Int) {
            val category = list[pos]

            binding.civCategory.loadImage(AppConstants.IMAGE_CATEGORY_URL+category.image)
            binding.tvCategoryName.text = category.name

            if (category.isSelected) {
                binding.ivTick.visibility = View.VISIBLE
            } else {
                binding.ivTick.visibility = View.GONE
            }


//            itemView.setOnClickListener {
//                category.isSelected = !category.isSelected
//                notifyDataSetChanged()
//                if (category.isSelected)
//                {
//                 val selectedId =   list[pos].id
//                }
//
//            }

            itemView.setOnClickListener {
//                category.isSelected = !category.isSelected
//                if (category.isSelected)
//                {
//                    val selectedId =   list[pos].id
//                }
                onClickListener?.invoke(pos,list[pos])
            }


        }
    }
}