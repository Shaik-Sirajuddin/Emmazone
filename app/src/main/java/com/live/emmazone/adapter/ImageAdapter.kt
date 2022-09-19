package com.live.emmazone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.databinding.ItemImagesBinding
import com.live.emmazone.response_model.Product
import com.live.emmazone.response_model.SellerShopDetailResponse
import com.live.emmazone.response_model.ShopDetailResponse
import com.schunts.extensionfuncton.loadImage

class ImageAdapter(
    var list: ArrayList<Product.ProductImage>
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {


    var onItemClickListener: ((pos: Int, clickOn: String) -> Unit)? = null
    var onDeleteImage: ((pos: Int, data: Product.ProductImage) -> Unit)? =
        null


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
                binding.ivDeletePhoto.visibility = View.VISIBLE
                binding.ivAdd.visibility = View.GONE
                binding.rivProduct.loadImage(list[pos].image)
            } else {
                binding.ivDeletePhoto.visibility = View.GONE
                binding.ivAdd.visibility = View.VISIBLE
                binding.rivProduct.setImageResource(R.drawable.sqaure_dotted)
            }


            itemView.setOnClickListener {
                if (pos == list.size) {
                    onItemClickListener?.invoke(pos,"addImage")
                }else{
                    onItemClickListener?.invoke(pos,"viewImage")
                }
            }

            binding.ivDeletePhoto.setOnClickListener {
                onDeleteImage?.invoke(pos, list[pos])
            }
        }
    }

}