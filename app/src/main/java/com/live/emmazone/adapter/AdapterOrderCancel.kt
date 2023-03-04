package com.live.emmazone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.activities.listeners.OnActionListenerNew
import com.live.emmazone.model.ModelOnGoingOrders

class AdapterOrderCancel(
    private val list: ArrayList<ModelOnGoingOrders>,
    private val onActionListenerNew: OnActionListenerNew? = null
) :
    RecyclerView.Adapter<AdapterOrderCancel.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_ongoing_myorders, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ModelOnGoingOrders = list[position]
        holder.imgOnGoingItem.setImageResource(ModelOnGoingOrders.onGoingItem)
        holder.tvonGoingItemName.setText(ModelOnGoingOrders.onGoingItemName)
        holder.tvonGoingItemQuantity.setText(ModelOnGoingOrders.onGoingItemQuantity)
        holder.tvproductPrice.setText(ModelOnGoingOrders.productPrice)

        holder.itemView.setOnClickListener {
            onActionListenerNew?.notifyOnClick(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgOnGoingItem: ImageView = itemView.findViewById(R.id.onGoingItem)
        val tvonGoingItemName: TextView = itemView.findViewById(R.id.onGoingItemName)
        val tvonGoingItemQuantity = itemView.findViewById<TextView>(R.id.onGoingItemQuantity)
        val tvproductPrice = itemView.findViewById<TextView>(R.id.productPrice)

    }
}