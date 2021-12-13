package com.live.emmazone.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.MainActivity
import com.live.emmazone.R
import com.live.emmazone.activities.Interface.OnItemClick
import com.live.emmazone.activities.provider.OrderDetailNewSaleActivity
import com.live.emmazone.model.*

class AdapterProviderNewSales(
    private val context: Context,
    private val list: ArrayList<ModelProviderNewSale>,
    private val cellClickListener: OnItemClick
) :
    RecyclerView.Adapter<AdapterProviderNewSales.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_provider_new_sale_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        holder.tvOrder.setText(model.tvOrder)
        holder.tvOrderID.setText(model.tvOrderID)
        holder.tvUsername.setText(model.tvUsername)
        holder.tvDeliveryType.setText(model.tvDeliveryType)
        holder.tvHomeDelivery.setText(model.tvHomeDelivery)
        holder.tvODOrderDate.setText(model.tvODOrderDate)
        holder.imageSales.setImageResource(model.imageSales)
        holder.imgStatus.setImageResource(model.imgStatus)

        holder.recyclerChildNewsale.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)
        holder.recyclerChildNewsale.adapter = AdapterOnGoingOrders(model.list, cellClickListener)
        holder.recyclerChildNewsale.isNestedScrollingEnabled = false

        holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                openScreen(list[holder.adapterPosition])
            }
        })
    }

    private fun openScreen(model: ModelProviderNewSale) {
        val intent = Intent(context, OrderDetailNewSaleActivity::class.java)
        intent.putExtra("model", model)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvOrder: TextView = itemView.findViewById(R.id.tvOrder)
        val tvOrderID: TextView = itemView.findViewById(R.id.tvOrderID)
        val tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        val tvDeliveryType: TextView = itemView.findViewById(R.id.tvDeliveryType)
        val tvHomeDelivery: TextView = itemView.findViewById(R.id.tvHomeDelivery)
        val tvODOrderDate: TextView = itemView.findViewById(R.id.tvODOrderDate)
        val imageSales: ImageView = itemView.findViewById(R.id.imageSales)
        val imgStatus: ImageView = itemView.findViewById(R.id.imgStatus)
        val recyclerChildNewsale: RecyclerView = itemView.findViewById(R.id.recyclerChildNewSales)
    }
}