package com.live.emmazone.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.activities.listeners.OnActionListenerNew
import com.live.emmazone.activities.main.ReservedDeliveredDetail
import com.live.emmazone.databinding.ItemLayoutOngoingUserLayoutBinding
import com.live.emmazone.response_model.UserOrderListing
import com.live.emmazone.utils.AppUtils

class AdapterOnGoingUserOrders(
    private val context: Context,
    private val list: ArrayList<UserOrderListing.Body.Response>
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
            tvODOrderDate.text = AppUtils.getDateTime(model.created.toLong())
            if (model.deliveryType == 0) {
                imgCodeScanner.visibility = View.VISIBLE
            } else {
                imgCodeScanner.visibility = View.GONE
            }
            when (model.orderStatus) {
                0 -> { //  order status  0-> Pending  1-> on the way 2-> Delivered 3-> cancelled
                    tvOrderStatus.text = context.getString(R.string.pending)
                }
                1 -> {
                    tvOrderStatus.text = context.getString(R.string.on_the_way)
                }
                2 -> {
                    tvOrderStatus.text = context.getString(R.string.delivered)
                }
            }


            val onActionListenerNew = object : OnActionListenerNew {
                override fun notifyOnClick() {
                    openDetailScreen(model, holder.adapterPosition)
                }
            }
            rvMyOrderOnGoing.layoutManager =
                LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)
            rvMyOrderOnGoing.adapter =
                AdapterOnGoingProducts(
                    context,
                    model.orderJson.orderItems,
                    onActionListenerNew,
                    "list"
                )
            rvMyOrderOnGoing.isNestedScrollingEnabled = false


        }
    }

    private fun openDetailScreen(model: UserOrderListing.Body.Response, position: Int) {
        val intent = Intent(context, ReservedDeliveredDetail::class.java)
        intent.putExtra("data", model)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return list.size

    }
}