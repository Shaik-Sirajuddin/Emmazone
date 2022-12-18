package com.live.emmazone.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.activities.main.ShopDetailActivity
import com.live.emmazone.databinding.ItemShopStoryProviderBinding
import com.live.emmazone.databinding.ItemShopStoryUserBinding
import com.live.emmazone.model.ModelShopStory
import com.live.emmazone.response_model.ShopStoryResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.schunts.extensionfuncton.loadImage


class ShopStoryUserAdapter(
    val context: Context,
    val list: ArrayList<ShopStoryResponse.Shop>,
    val onLike: (pos: Int) -> Unit
) : RecyclerView.Adapter<ShopStoryUserAdapter.ShopStoryUserViewHolder>() {

    inner class ShopStoryUserViewHolder(val binding: ItemShopStoryUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            val images = arrayListOf<String>()
            val model = list[pos]
            model.stories.forEach {
                images.add(it.image)
            }
            with(binding) {
                itemImageProductDetail.adapter = ImageSliderCustomeAdapter(
                    context,
                    images,
                    true
                )
                indicatorProduct.setViewPager(binding.itemImageProductDetail)
                shopName.text = model.shopName
                distance.text = model.distance.toString()+" "+ context.getString(R.string.miles_away)
                likeShop.setOnClickListener {
                    onLike(pos)
                }
                if (model.isLiked == 1) {
                    binding.likeShop.setImageResource(R.drawable.heart)
                } else {
                    binding.likeShop.setImageResource(R.drawable.heart_unselect)
                }
                root.setOnClickListener {
                    val intent = Intent(context, ShopDetailActivity::class.java)
                    intent.putExtra(AppConstants.SHOP_ID, model.id)
                    intent.putExtra("distance",model.distance)
                    context.startActivity(intent)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopStoryUserViewHolder {
        val binding = ItemShopStoryUserBinding.inflate(LayoutInflater.from(context), parent, false)
        return ShopStoryUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopStoryUserViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = list.size
}
