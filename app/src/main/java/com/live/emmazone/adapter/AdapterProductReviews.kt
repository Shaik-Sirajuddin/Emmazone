package com.live.emmazone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.databinding.ItemProductReviewsBinding
import com.live.emmazone.model.*

class AdapterProductReviews(private val list: ArrayList<ModelProductReviews>) :
    RecyclerView.Adapter<AdapterProductReviews.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val binding = ItemProductReviewsBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val model = list[position]

        with(holder.binding){
            imageReviews.setImageResource(model.imageReviews)
            productItemName.text = model.productItemName
            tvRating.text = model.tvRating

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding : ItemProductReviewsBinding) : RecyclerView.ViewHolder(binding.root)

}