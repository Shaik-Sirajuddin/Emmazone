package com.live.emmazone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.model.*

class AdapterProvODNewSalesStatus(private val context : Context, private val list: ArrayList<ModelNewSaleOrderDetail>) :
    RecyclerView.Adapter<AdapterProvODNewSalesStatus.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.item_provider_order_detail_new_sale_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val ModelNewSaleOrderDetail = list[position]

        holder.tvOrderID.setText(ModelNewSaleOrderDetail.tvOrderID)
        holder.imageSales.setImageResource(ModelNewSaleOrderDetail.imagePerson)
        holder.imgStatus.setText(ModelNewSaleOrderDetail.tvUsername)
        holder.imgChat.setImageResource(ModelNewSaleOrderDetail.imgChat)
        holder.recyclerODChildNewSales.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)
        holder.recyclerODChildNewSales.adapter = AdapterOnGoingOrders(context, ModelNewSaleOrderDetail.list)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvOrderID : TextView = itemView.findViewById(R.id.tvOrderID)
        val imageSales : ImageView = itemView.findViewById(R.id.imageSales)
        val imgStatus : TextView = itemView.findViewById(R.id.tvUsername)
        val imgChat : ImageView = itemView.findViewById(R.id.imgChat)
        val recyclerODChildNewSales : RecyclerView = itemView.findViewById(R.id.rvODChildNewSales)

            }
}