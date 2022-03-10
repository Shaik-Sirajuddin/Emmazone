package com.live.emmazone.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.live.emmazone.R
import com.live.emmazone.activities.listeners.OnActionListenerNew
import com.live.emmazone.activities.provider.OrderDetailNewSaleActivity
import com.live.emmazone.response_model.SalesResponse
import com.live.emmazone.utils.AppUtils
import android.os.Bundle

class AdapterProviderNewSales(
    private val context: Context,
    private val list: ArrayList<SalesResponse.SaleResponseBody>,
) : RecyclerView.Adapter<AdapterProviderNewSales.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_provider_new_sale_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        Glide.with(context).load(model.customer.image).into(holder.imageSales)
        holder.tvOrderID.text = model.orderNo
        holder.tvUsername.text = model.customer.username
        holder.tvODOrderDate.text= AppUtils.getDateTime(model.created)
        when (model.deliveryType) {
            0 -> {   //   0-> click & collect 1-> Lifernado 2-> Own Delivery
                holder.tvHomeDelivery.text = context.getString(R.string.click_and_collect)
            }
            1 -> {
                holder.tvHomeDelivery.text = context.getString(R.string.lifernado)
            }
            2 -> {
                holder.tvHomeDelivery.text = context.getString(R.string.own_delivery)
            }
        }

        when (model.orderStatus) {
            0 -> { //  order status  0-> Pending  1-> on the way 2-> Delivered 3-> cancelled
                holder.tvOrderStatus.text= context.getString(R.string.pending)
            }
            1 -> {
                holder.tvOrderStatus.text= context.getString(R.string.on_the_way)
            }
            2 -> {
                holder.tvOrderStatus.text= context.getString(R.string.delivered)
            }
        }


        val onActionListenerNew = object : OnActionListenerNew {
            override fun notifyOnClick() {
                openDetailScreen(model, holder.adapterPosition)
            }
        }

        holder.recyclerChildNewsale.layoutManager =
            LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)
        holder.recyclerChildNewsale.adapter =
            AdapterOnGoingOrders(context, model.orderJson.orderItems, onActionListenerNew,"list")
        holder.recyclerChildNewsale.isNestedScrollingEnabled = false

        holder.itemView.setOnClickListener {
            openDetailScreen(model, holder.adapterPosition)
        }
    }

    private fun openDetailScreen(model: SalesResponse.SaleResponseBody, position: Int) {
        val intent = Intent(context, OrderDetailNewSaleActivity::class.java)
        intent.putExtra("data",model)
        context.startActivity(intent)

       // when (model.status.toString()) {
          /*  "pending" -> {
                if (position == 0) {
                    val intent = Intent(context, OrderDetailNewSaleActivity::class.java)
                    context.startActivity(intent)
                } else if (position == 1) {
                    val intent = Intent(context, OrderDetailPendingActivity::class.java)
                    context.startActivity(intent)
                }
            }
            "ongoing" -> {
                val intent = Intent(context, OrderDetailProOngoingActivity::class.java)
                context.startActivity(intent)
            }
            "past" -> {
                val intent = Intent(context, OrderDetailDeliveredStatusActivity::class.java)
                context.startActivity(intent)
            }
            else -> {
                // do nothing
            }*/
      //  }
    }

//    private fun openScreen(model: ModelProviderNewSale) {
//        val intent = Intent(context, OrderDetailNewSaleActivity::class.java)
//        intent.putExtra("model", model)
//        context.startActivity(intent)
//    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvOrder: TextView = itemView.findViewById(R.id.tvOrder)
        val tvOrderID: TextView = itemView.findViewById(R.id.tvOrderID)
        val tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        val tvDeliveryType: TextView = itemView.findViewById(R.id.tvDeliveryType)
        val tvHomeDelivery: TextView = itemView.findViewById(R.id.tvHomeDelivery)
        val tvODOrderDate: TextView = itemView.findViewById(R.id.tvODOrderDate)
        val imageSales: ImageView = itemView.findViewById(R.id.imageSales)
        val tvOrderStatus: TextView = itemView.findViewById(R.id.tvOrderStatus)
        val recyclerChildNewsale: RecyclerView = itemView.findViewById(R.id.recyclerChildNewSales)
    }
}