package com.live.emmazone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.live.emmazone.R
import com.live.emmazone.activities.listeners.OnActionListenerNew
import com.live.emmazone.databinding.ItemLayoutOngoingMyordersBinding
import com.live.emmazone.databinding.ItemLayoutOngoingUserLayoutBinding
import com.live.emmazone.response_model.SalesResponse
import com.live.emmazone.response_model.UserOrderListing
import com.live.emmazone.utils.AppUtils

class AdapterOnGoingUserOrders(
    private val context: Context,
    private val list: ArrayList<UserOrderListing.OrderListBody>,
    private val onActionListenerNew: OnActionListenerNew? = null
) :
    RecyclerView.Adapter<AdapterOnGoingUserOrders.ViewHolder>() {

    class ViewHolder(val binding: ItemLayoutOngoingUserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLayoutOngoingUserLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        with(holder.binding) {
            tvOrderID.text = model.orderNo
            tvODOrderDate.text = AppUtils.getDateTime(model.created)
            if(model.deliveryType==0){
                imgCodeScanner.visibility= View.VISIBLE
            }else{
                imgCodeScanner.visibility=View.GONE
            }

            rvMyOrderOnGoing.layoutManager =
                LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)
            rvMyOrderOnGoing.adapter =
                AdapterOnGoingOrders(context, model.orderJson.orderItems, onActionListenerNew,"list")
            rvMyOrderOnGoing.isNestedScrollingEnabled = false


        }

        holder.itemView.setOnClickListener {
            onActionListenerNew?.notifyOnClick()
        }
    }

    override fun getItemCount(): Int {
            return list.size

    }
}