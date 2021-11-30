package com.live.emmazone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.model.ModelDeliveryAddress
import com.live.emmazone.model.ModelNotifications
import com.live.emmazone.model.ModelRatingReviews
import com.live.emmazone.model.ModelWishList

class AdapterRatingReviews(private val list: ArrayList<ModelRatingReviews>) :
    RecyclerView.Adapter<AdapterRatingReviews.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_rating_reviews, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val ModelRatingReviews = list[position]
        holder.imageprofileRR.setImageResource(ModelRatingReviews.profileRatingReviews)
        holder.tvPersonNameRR.setText(ModelRatingReviews.tvPersonName)
        holder.tvWishListRatingTextRR.setText(ModelRatingReviews.tvRatingDate)
        holder.tvRatingRR.setText(ModelRatingReviews.tvRating)
        holder.tvRatingTextRR.setText(ModelRatingReviews.tvRatingText)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageprofileRR : ImageView = itemView.findViewById(R.id.profileRatingReviews)
        val tvPersonNameRR = itemView.findViewById<TextView>(R.id.tvPersonName)
        val tvWishListRatingTextRR = itemView.findViewById<TextView>(R.id.tvRatingDate)
        val tvRatingRR= itemView.findViewById<TextView>(R.id.tvRating)
        val tvRatingTextRR= itemView.findViewById<TextView>(R.id.tvRatingText)

            }
}