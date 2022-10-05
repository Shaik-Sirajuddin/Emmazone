package com.live.emmazone.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.live.emmazone.R
import com.live.emmazone.activities.listeners.OnActionListenerNew
import com.live.emmazone.databinding.ItemLayoutOngoingMyordersBinding
import com.live.emmazone.response_model.SalesResponse
import com.live.emmazone.response_model.UserOrderListing
import com.live.emmazone.utils.AppConstants

class AdapterOnGoingProducts(
    private val context: Context,
    private val list: ArrayList<UserOrderListing.Body.Response.OrderJson.OrderItem>,
    private val onActionListenerNew: OnActionListenerNew? = null,
    private val type: String
) :
    RecyclerView.Adapter<AdapterOnGoingProducts.ViewHolder>() {

    class ViewHolder(val binding: ItemLayoutOngoingMyordersBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLayoutOngoingMyordersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        with(holder.binding) {
            if(model.mainImage!=null){
                var image = model.mainImage.toString()
                if(image.contains("http")){
                    image = "https://emmazones3.s3.eu-west-2.amazonaws.com/product/" +  image.substring(image.lastIndexOf('/') + 1)
                    Log.e("this",image)
                }
                Glide.with(context).load(image).into(onGoingItem)
            }
            onGoingItemName.text = model.name
            onGoingItemQuantity.text = model.orderedQty.toString()
            productPrice.text = context.getString(R.string.euro_symbol,model.productPrice)
            productPrice.text = context.getString(R.string.euro_symbol,model.productPrice)
        }

        holder.itemView.setOnClickListener {
            onActionListenerNew?.notifyOnClick()
        }
    }

    override fun getItemCount(): Int {
        if(type=="list"){
            if(list.size>=2){
                return 2
            }else{
                return list.size
            }
        }else{
            return list.size
        }
    }
}