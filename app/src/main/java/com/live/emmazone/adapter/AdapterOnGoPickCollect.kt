package com.live.emmazone.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.activities.main.ReservedDeliveredDetail
import com.live.emmazone.model.ModelOnGoingOrders
import com.live.emmazone.response_model.SalesResponse

class AdapterOnGoPickCollect(
    private val context: Context,
    private val list: ArrayList<SalesResponse.Body.Response.OrderJson.OrderItem>
) :
    RecyclerView.Adapter<AdapterOnGoPickCollect.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_ongoing_myorders, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
       // holder.imgOnGoingItem.setImageResource(ModelOnGoingOrders.onGoingItem)
        holder.tvonGoingItemName.text = data.name
        holder.tvonGoingItemQuantity.text = data.productQuantity.toString()
        holder.tvproductPrice.text = data.productPrice

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ReservedDeliveredDetail::class.java)
            context.startActivity(intent)
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