package com.live.emmazone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.activities.listeners.OnActionListener
import com.live.emmazone.databinding.ItemImagesBinding
import com.live.emmazone.model.ImageModel

class ImageAdapter(var context: Context, var items: List<ImageModel>, var onActionListener: OnActionListener<ImageModel>
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemImagesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = if (items.size == position)
            ImageModel()
        else
            items[position]

        with(model)
        {
            with(holder.binding) {
                if (imgUrl == null) {
                    layoutAdd.visibility = View.VISIBLE
                    layoutImage.visibility = View.GONE

                    layoutAdd.setOnClickListener {

                        onActionListener.notify(model, position)
                    }

                } else {
                    layoutAdd.visibility = View.GONE
                    layoutImage.visibility = View.VISIBLE
                    imageView.setImageURI(imgUrl)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size + 1
    }
}