package com.live.emmazone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.databinding.ItemColorBinding
import com.live.emmazone.model.ColorSizeModel

class SizeAdapter(val list: ArrayList<ColorSizeModel>) :
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
            val colorSizeModel = list[pos]

            itemView.setOnClickListener {
                onClickListener?.invoke(pos)
            }

        }
    }

}