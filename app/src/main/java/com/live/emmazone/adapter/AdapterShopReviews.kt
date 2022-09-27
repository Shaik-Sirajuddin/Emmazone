package com.live.emmazone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.response_model.ShopReviewModel
import com.live.emmazone.utils.AppUtils
import com.schunts.extensionfuncton.loadImage


class AdapterShopReviews(private val list: ArrayList<ShopReviewModel>) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_rating_reviews, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        val date = AppUtils.getNotificationTimeAgo(model.updated)
        holder.image.loadImage(model.user.image)
        holder.name.text = model.user.name
        holder.date.text = date
        holder.rating.text = model.rating.toString()
        holder.comment.text = model.comment
        holder.ratingBar.rating = model.rating.toFloat()
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val image: ImageView = itemView.findViewById(R.id.profileRatingReviews)
    val name: TextView = itemView.findViewById(R.id.tvPersonName)
    val date = itemView.findViewById<TextView>(R.id.tvRatingDate)
    val rating = itemView.findViewById<TextView>(R.id.tvRating)
    val comment = itemView.findViewById<TextView>(R.id.tvRatingText)
    val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)

}

