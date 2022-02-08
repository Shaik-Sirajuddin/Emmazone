package com.live.emmazone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.activities.listeners.OnActionListener
import com.live.emmazone.databinding.ItemImagesBinding
import com.live.emmazone.model.ImageModel
import com.schunts.extensionfuncton.loadImage

class ImageAdapter(
    var list: ArrayList<String>
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {


    var onItemClickListener: ((pos: Int) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    inner class ViewHolder(val binding: ItemImagesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(pos: Int) {

            if (pos != list.size) {
                binding.ivAdd.visibility = View.GONE
                binding.rivProduct.loadImage(list[pos])
            } else {
                binding.ivAdd.visibility = View.VISIBLE
            }

            itemView.setOnClickListener {
                onItemClickListener?.invoke(pos)
            }
        }
    }
}