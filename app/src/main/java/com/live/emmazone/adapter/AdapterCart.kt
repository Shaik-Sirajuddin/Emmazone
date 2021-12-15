package com.live.emmazone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.model.ModelCart
import com.live.emmazone.model.ModelDeliveryAddress
import com.live.emmazone.model.ModelNotifications
import com.live.emmazone.model.ModelWishList

class AdapterCart(private val list: ArrayList<ModelCart>) :
    RecyclerView.Adapter<AdapterCart.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_cart_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val ModelCart = list[position]
        holder.imgCart.setImageResource(ModelCart.imageCart)
        holder.imageDelete.setImageResource(ModelCart.imgDelete)
        holder.tvproductItemName.setText(ModelCart.productItemName)
        holder.tvproductPrice.setText(ModelCart.productPrice)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgCart : ImageView = itemView.findViewById(R.id.imageCart)
        val imageDelete : ImageView = itemView.findViewById(R.id.imgDelete)
        val tvproductItemName = itemView.findViewById<TextView>(R.id.productItemName)
        val tvproductPrice = itemView.findViewById<TextView>(R.id.productPrice)

            }
}