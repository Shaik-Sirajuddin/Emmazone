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
import com.live.emmazone.model.ModelWishList

class AdapterWishList(private val list: ArrayList<ModelWishList>) :
    RecyclerView.Adapter<AdapterWishList.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_wishlist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val ModelWishList = list[position]
        holder.imageStore.setImageResource(ModelWishList.itemImageWishList)
        holder.imageHeart.setImageResource(ModelWishList.itemHeartWishList)
        holder.imageRating.setImageResource(ModelWishList.ratingBarWishList)
        holder.imageLoc.setImageResource(ModelWishList.imageLocation)
        holder.tvWishListStore.setText(ModelWishList.tvWishListStoreName)
        holder.tvWishRatingText.setText(ModelWishList.tvWishListRatingText)
        holder.tvWishtDistance.setText(ModelWishList.tvWishListDistance)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageStore : ImageView = itemView.findViewById(R.id.itemImageWishList)
        val imageHeart : ImageView = itemView.findViewById(R.id.itemHeartWishList)
        val imageRating : ImageView = itemView.findViewById(R.id.ratingBarWishList)
        val imageLoc: ImageView = itemView.findViewById(R.id.imageLocation)
        val tvWishListStore = itemView.findViewById<TextView>(R.id.tvWishListStoreName)
        val tvWishRatingText = itemView.findViewById<TextView>(R.id.tvWishListRatingText)
        val tvWishtDistance= itemView.findViewById<TextView>(R.id.tvWishListDistance)

            }
}