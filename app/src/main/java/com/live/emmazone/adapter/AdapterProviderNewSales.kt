package com.live.emmazone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.model.*

class AdapterProviderNewSales(private val list: ArrayList<ModelProviderNewSale>) :
    RecyclerView.Adapter<AdapterProviderNewSales.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.item_provider_new_sale_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val ModelProviderNewSale = list[position]

        holder.tvOrder.setText(ModelProviderNewSale.tvOrder)
        holder.tvOrderID.setText(ModelProviderNewSale.tvOrderID)
        holder.tvUsername.setText(ModelProviderNewSale.tvUsername)
        holder.tvDeliveryType.setText(ModelProviderNewSale.tvDeliveryType)
        holder.tvHomeDelivery.setText(ModelProviderNewSale.tvHomeDelivery)
        holder.tvODOrderDate.setText(ModelProviderNewSale.tvODOrderDate)
        holder.imageSales.setImageResource(ModelProviderNewSale.imageSales)
        holder.imgStatus.setImageResource(ModelProviderNewSale.imgStatus)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvOrder : TextView = itemView.findViewById(R.id.tvOrder)
        val tvOrderID : TextView = itemView.findViewById(R.id.tvOrderID)
        val tvUsername : TextView = itemView.findViewById(R.id.tvUsername)
        val tvDeliveryType : TextView = itemView.findViewById(R.id.tvDeliveryType)
        val tvHomeDelivery : TextView = itemView.findViewById(R.id.tvHomeDelivery)
        val tvODOrderDate : TextView = itemView.findViewById(R.id.tvODOrderDate)
        val imageSales : ImageView = itemView.findViewById(R.id.imageSales)
        val imgStatus : ImageView = itemView.findViewById(R.id.imgStatus)


            }
}