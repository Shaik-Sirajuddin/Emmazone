package com.live.emmazone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.databinding.ItemLayoutHomeNearbyShopBinding
import com.live.emmazone.databinding.ItemLayoutPaymentMethodBinding
import com.live.emmazone.databinding.ItemLayoutWishlistBinding
import com.live.emmazone.model.ModelDeliveryAddress
import com.live.emmazone.model.ModelNotifications
import com.live.emmazone.model.ModelWishList
import com.live.emmazone.response_model.WishListResponse
import com.live.emmazone.utils.AppConstants
import com.schunts.extensionfuncton.loadImage
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations

class AdapterWishList(private var list: ArrayList<WishListResponse.Body.Wish>) :
    RecyclerView.Adapter<AdapterWishList.WishListViewHolder>() {

    private lateinit var mContext: Context
    var onClickListener: ((wishListModel: WishListResponse.Body.Wish, clickOn: String) -> Unit)? =
        null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListViewHolder {
        mContext = parent.context
        val binding = ItemLayoutHomeNearbyShopBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WishListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WishListViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class WishListViewHolder(val binding: ItemLayoutHomeNearbyShopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            with(binding) {
                val nearShopModel = list[pos]
                val images = arrayListOf<String>()
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

    fun notifyData(arrayList: ArrayList<WishListResponse.Body.Wish>) {
        list = arrayList
        notifyDataSetChanged()
    }
}