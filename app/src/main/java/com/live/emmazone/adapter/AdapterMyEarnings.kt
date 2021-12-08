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
import com.live.emmazone.model.ModelMyEarnings
import com.live.emmazone.model.ModelNotifications
import com.live.emmazone.model.ModelWishList

class AdapterMyEarnings(private val list: ArrayList<ModelMyEarnings>) :
    RecyclerView.Adapter<AdapterMyEarnings.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_wishlist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val ModelMyEarnings = list[position]
        holder.tvproductName.setText(ModelMyEarnings.productName)
        holder.tvMyEarningDate.setText(ModelMyEarnings.tvMyEarningsDate)
        holder.tvearningsDollar.setText(ModelMyEarnings.earningsDollar)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvproductName = itemView.findViewById<TextView>(R.id.productName)
        val tvMyEarningDate = itemView.findViewById<TextView>(R.id.tvMyEarningsDate)
        val tvearningsDollar= itemView.findViewById<TextView>(R.id.earningsDollar)

            }
}