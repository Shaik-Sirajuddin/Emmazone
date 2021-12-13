package com.live.emmazone.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.activities.Interface.OnItemClick
import com.live.emmazone.activities.provider.OrderDetailNewSaleActivity
import com.live.emmazone.model.*
import org.w3c.dom.Text

class AdapterProvODNewSalesStatus(private val list: ArrayList<ModelNewSaleOrderDetail>, private val
cellClickListener: OnItemClick) :
    RecyclerView.Adapter<AdapterProvODNewSalesStatus.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.item_provider_order_detail_new_sale_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val ModelNewSaleOrderDetail = list[position]

        if (position == 0){
            val intent = Intent(holder.itemView.context, OrderDetailNewSaleActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }

        holder.tvOrderID.setText(ModelNewSaleOrderDetail.tvOrderID)
        holder.imageSales.setImageResource(ModelNewSaleOrderDetail.imagePerson)
        holder.imgStatus.setText(ModelNewSaleOrderDetail.tvUsername)
        holder.imgChat.setImageResource(ModelNewSaleOrderDetail.imgChat)
        holder.recyclerODChildNewSales.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)
        holder.recyclerODChildNewSales.adapter = AdapterOnGoingOrders(ModelNewSaleOrderDetail.list,cellClickListener)

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