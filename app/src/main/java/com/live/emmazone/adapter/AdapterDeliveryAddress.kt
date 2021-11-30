package com.live.emmazone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.model.ModelDeliveryAddress

class AdapterDeliveryAddress(private val list: ArrayList<ModelDeliveryAddress>) :
    RecyclerView.Adapter<AdapterDeliveryAddress.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.item_delivery_address, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val ModelDeliveryAddress = list[position]
        holder.tvOrderPerson.setText(ModelDeliveryAddress.tvOrderPersonName)
        holder.tvDeliveryAddress.setText(ModelDeliveryAddress.tvOrderDeliveryAddress)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvOrderPerson = itemView.findViewById<TextView>(R.id.tvOrderPersonName)
        val tvDeliveryAddress = itemView.findViewById<TextView>(R.id.tvOrderDeliveryAddress)
        //val radioButtonDeliveryAdrs = itemView.findViewById<RadioButton>(R.id.radioBtnDeliveryAdrs)

    }

}