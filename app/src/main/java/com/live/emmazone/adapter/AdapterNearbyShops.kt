package com.live.emmazone.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.MainActivity
import com.live.emmazone.R
import com.live.emmazone.activities.main.ShopDetailActivity
import com.live.emmazone.activities.main.ShopReviewsActivity
import com.live.emmazone.databinding.ItemLayoutHomeNearbyShopBinding
import com.live.emmazone.model.*
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.response_model.ShopListingResponse

class AdapterNearbyShops(private val context: Context, private val list: ArrayList<ShopListingResponse.Body>) :
    RecyclerView.Adapter<AdapterNearbyShops.ViewHolder>() {

    var onClickListener:((pos:Int,type:String)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutHomeNearbyShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val model = list[position]

        with(holder.binding){
//            itemImageHome.setImageResource(model.itemImageHome)
//            tvWishListStoreName.text = model.tvWishListStoreName
//            ratingBarWishList.setImageResource(model.ratingBarWishList)
//            tvWishListRatingText.text = model.tvWishListRatingText
//            tvWishListDistance.text = model.tvWishListDistance

            tvWishListStoreName.text = list[position].shopName
//            itemImageHome.setImageResource(list[position].i.itemImageHome)
//            ratingBarWishList.setImageResource(model.ratingBarWishList)
            tvWishListRatingText.text = list[position].ratings+"/"+"5"
            tvWishListDistance.text = list[position].distance.toString()

            if (position == 2){
                itemImageHome.setImageResource(R.drawable.banner)
                tvWishListStoreName.visibility = View.GONE
                ratingBarWishList.visibility = View.GONE
                tvWishListRatingText.visibility = View.GONE
                tvWishListDistance.visibility = View.GONE
                itemHeartWishList.visibility = View.GONE
                imageLocation.visibility = View.GONE
            }

            itemHeartWishList.setOnClickListener {

                onClickListener?.invoke(position,"1")

            }


            itemImageHome.setOnClickListener {

                onClickListener?.invoke(position,"2")

//
//                val intent = Intent(context.applicationContext, ShopDetailActivity::class.java)
//                context.startActivity(intent)
            }

            ratingBarWishList.setOnClickListener {

                onClickListener?.invoke(position,"3")
//
//                    if ( getPreference(AppConstants.PROFILE_TYPE,"") == AppConstants.GUEST) {
//                        (context.applicationContext as MainActivity).showLoginDialog()
//                        return@setOnClickListener
//                    }
//                    val intent = Intent(context.applicationContext, ShopReviewsActivity::class.java)
//                    context.startActivity(intent)
            }
        }
            }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: ItemLayoutHomeNearbyShopBinding) : RecyclerView.ViewHolder(binding.root)
}