package com.live.emmazone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.response_model.MyEarningResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils

class AdapterMyEarnings(private val list: ArrayList<MyEarningResponse.Body.FindOrder>) :
    RecyclerView.Adapter<AdapterMyEarnings.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_my_earnings, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val modelMyEarnings = list[position]
        holder.tvproductName.text = modelMyEarnings.orderNo
        holder.tvMyEarningDate.text =
            AppUtils.secondsToTime(modelMyEarnings.created.toLong(), AppConstants.DATE_FORMAT)
        holder.tvearningsDollar.text =
            context.getString(R.string.euro_symbol, modelMyEarnings.netAmount)
        when (modelMyEarnings.orderStatus) {
            0 -> { //  order status  0-> Pending  1-> on the way 2-> Delivered 3-> cancelled
                //7-> Return in transit // 8-> Returned
                holder.tvOrderStatus.text = context.getString(R.string.pending)
            }
            1 -> {
                holder.tvOrderStatus.text = context.getString(R.string.on_the_way)
            }
            2 -> {
                holder.tvOrderStatus.text = context.getString(R.string.completed)
            }
            3 -> {
                holder.tvOrderStatus.text = context.getString(R.string.cancel)
            }
            7->{
                holder.tvOrderStatus.text = context.getString(R.string.return_in_transit)
            }
            8->{
                holder.tvOrderStatus.text = context.getString(R.string.returned)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvproductName = itemView.findViewById<TextView>(R.id.productName)
        val tvMyEarningDate = itemView.findViewById<TextView>(R.id.tvMyEarningsDate)
        val tvearningsDollar = itemView.findViewById<TextView>(R.id.earningsDollar)
        val tvOrderStatus = itemView.findViewById<TextView>(R.id.orderStaus)

    }
}