package com.live.emmazone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.live.emmazone.R
import com.live.emmazone.response_model.UserOrderListing

class AdapterOrderDetail(
    val context: Context,
    private val list: ArrayList<UserOrderListing.Body.Response.OrderJson.OrderItem>
) :
    RecyclerView.Adapter<AdapterOrderDetail.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_order_detail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        Glide.with(context).load(data.mainImage).into(holder.imageOD)
        holder.productItemNameOD.text = data.name
        holder.tvOrderQuantityNumberOD.text = data.orderedQty.toString()
        holder.productPriceOD.text = context.getString(R.string.euro_symbol, data.productPrice)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageOD: ImageView = itemView.findViewById(R.id.imageOrder)
        val productItemNameOD = itemView.findViewById<TextView>(R.id.productItemName)
        val tvOrderQuantityNumberOD = itemView.findViewById<TextView>(R.id.tvOrderQuantityNumber)
        val productPriceOD = itemView.findViewById<TextView>(R.id.productPrice)

    }
}