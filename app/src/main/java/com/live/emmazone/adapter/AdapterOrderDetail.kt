package com.live.emmazone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.model.ModelDeliveryAddress
import com.live.emmazone.model.ModelNotifications
import com.live.emmazone.model.ModelOrderDetail
import com.live.emmazone.model.ModelWishList

class AdapterOrderDetail(private val list: ArrayList<ModelOrderDetail>) :
    RecyclerView.Adapter<AdapterOrderDetail.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_order_detail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val ModelOrderDetail = list[position]
        holder.imageOD.setImageResource(ModelOrderDetail.imageOrder)
        holder.productItemNameOD.setText(ModelOrderDetail.productItemName)
        holder.tvOrderQuantityNumberOD.setText(ModelOrderDetail.tvOrderQuantityNumber)
        holder.productPriceOD.setText(ModelOrderDetail.productPrice)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageOD : ImageView = itemView.findViewById(R.id.imageOrder)
        val productItemNameOD = itemView.findViewById<TextView>(R.id.productItemName)
        val tvOrderQuantityNumberOD = itemView.findViewById<TextView>(R.id.tvOrderQuantityNumber)
        val productPriceOD = itemView.findViewById<TextView>(R.id.productPrice)

            }
}