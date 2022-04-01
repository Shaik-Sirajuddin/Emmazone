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

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
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
            mContext.getString(R.string.euro_symbol, modelMyEarnings.total)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvproductName = itemView.findViewById<TextView>(R.id.productName)
        val tvMyEarningDate = itemView.findViewById<TextView>(R.id.tvMyEarningsDate)
        val tvearningsDollar = itemView.findViewById<TextView>(R.id.earningsDollar)

    }
}