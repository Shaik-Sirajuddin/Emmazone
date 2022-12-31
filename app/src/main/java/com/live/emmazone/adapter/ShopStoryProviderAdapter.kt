package com.live.emmazone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.databinding.ItemShopStoryProviderBinding
import com.live.emmazone.model.ModelShopStory
import com.live.emmazone.utils.AppUtils
import com.schunts.extensionfuncton.loadImage

class ShopStoryProviderAdapter(
    val context : Context,
    val list : ArrayList<ModelShopStory>,
    val onDelete:(pos:Int)->Unit
): RecyclerView.Adapter<ShopStoryProviderAdapter.ShopStoryViewHolder>() {

    inner class ShopStoryViewHolder(val binding : ItemShopStoryProviderBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(pos : Int){
            with(binding){
                image.loadImage(list[pos].image)
                delete.setOnClickListener {
                    onDelete(pos)
                }
                var agoText =  AppUtils.getNotificationTimeAgo(list[pos].created)
                if(agoText != "Just now"){
                    agoText += " ago"
                }
                createdDate.text = agoText
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopStoryViewHolder {
        val binding = ItemShopStoryProviderBinding.inflate(LayoutInflater.from(context),parent,false)
        return ShopStoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopStoryViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = list.size
}
