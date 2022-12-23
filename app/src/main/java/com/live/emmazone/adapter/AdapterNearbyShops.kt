package com.live.emmazone.adapter

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.activities.fragment.HomeFragment
import com.live.emmazone.databinding.ItemLayoutHomeNearbyShopBinding
import com.live.emmazone.response_model.ShopListingResponse
import com.live.emmazone.utils.AppConstants
import com.schunts.extensionfuncton.loadImage
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations

class AdapterNearbyShops(
    private var list: ArrayList<ShopListingResponse.Body.Shop>
) : RecyclerView.Adapter<AdapterNearbyShops.NearByShopViewHolder>() {

    private lateinit var mContext: Context
    var onClickListener: ((shopModel: ShopListingResponse.Body.Shop, clickOn: String) -> Unit)? =
        null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearByShopViewHolder {
        mContext = parent.context
        val binding = ItemLayoutHomeNearbyShopBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NearByShopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NearByShopViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class NearByShopViewHolder(val binding: ItemLayoutHomeNearbyShopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            with(binding) {

                //setting images

                val nearShopModel = list[pos]
                val images = arrayListOf<String>()
                if (nearShopModel.isLiked == 1)
                    nearShopModel.stories.forEach {
                        images.add(it.image)
                    }
                images.add(AppConstants.IMAGE_USER_URL + nearShopModel.image)
                val adapter = ImageSliderCustomeAdapter(
                    mContext,
                    images,
                    false
                ) {
                    onClickListener?.invoke(list[pos], "itemClick")
                }

                slider.setSliderAdapter(adapter)
                slider.setIndicatorAnimation(IndicatorAnimationType.WORM)
                slider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
                slider.scrollTimeInSec = 3
                slider.startAutoCycle()


                //setting data
                tvWishListStoreName.text = nearShopModel.shopName
                tvWishListRatingText.text = nearShopModel.ratings + "/" + "5"
                tvWishListDistance.text = nearShopModel.distance.toString() + " " +
                        mContext.getString(R.string.miles_away)

                if (nearShopModel.ratings.isNotEmpty()) {
                    ratingBarWishList.rating = nearShopModel.ratings.toFloat()
                }

                if (nearShopModel.isLiked == 1) {
                    itemHeartWishList.setImageResource(R.drawable.heart)
                } else {
                    itemHeartWishList.setImageResource(R.drawable.heart_unselect)
                }

                itemHeartWishList.setOnClickListener {
                    onClickListener?.invoke(list[pos], "favourite")
                }
                ratingBarWishList.setOnClickListener {
                    onClickListener?.invoke(list[pos], "rating")
                }

                tvWishListRatingText.setOnClickListener {
                    onClickListener?.invoke(list[pos], "rating")
                }
            }

        }

    }

    fun notifyData(arrayList: ArrayList<ShopListingResponse.Body.Shop>) {
        list = arrayList
        notifyDataSetChanged()
    }

}