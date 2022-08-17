package com.live.emmazone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.databinding.ItemColorBinding
import com.live.emmazone.model.ColorSizeModel
import com.live.emmazone.response_model.CategoryColorSizeResponse
import com.live.emmazone.response_model.ColorListResponse

class ColorAdapter(val list: ArrayList<CategoryColorSizeResponse.Body.CategoryColor>) :
    RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    var onClickListener: ((pos: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {

        val binding = ItemColorBinding.inflate(LayoutInflater.from(parent.context))
        return ColorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ColorViewHolder(val binding: ItemColorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            val colorResponse = list[pos]

            binding.tvName.text = colorResponse.color

            if (colorResponse.isSelected) {
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