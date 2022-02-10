package com.live.emmazone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.databinding.ItemColorBinding
import com.live.emmazone.model.ColorSizeModel
import com.live.emmazone.response_model.CategoryColorSizeResponse

class SizeAdapter(val list: ArrayList<CategoryColorSizeResponse.Body.CategorySize>) :
    RecyclerView.Adapter<SizeAdapter.SizeViewHolder>() {

    var onClickListener: ((pos: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeViewHolder {

        val binding = ItemColorBinding.inflate(LayoutInflater.from(parent.context))
        return SizeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SizeViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class SizeViewHolder(val binding: ItemColorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pos: Int) {
            val sizeModel = list[pos]

            binding.tvName.text = sizeModel.size

            if (sizeModel.isSelected) {
                binding.ivTick.visibility = View.VISIBLE
            } else {
                binding.ivTick.visibility = View.GONE
            }

            itemView.setOnClickListener {
                onClickListener?.invoke(pos)
            }

        }
    }

}