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
import com.live.emmazone.model.ModelPaymentCard
import com.live.emmazone.model.ModelWishList

class AdapterAddPaymentCard(private val list: ArrayList<ModelPaymentCard>) :
    RecyclerView.Adapter<AdapterAddPaymentCard.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_payment_method, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val ModelPaymentCard = list[position]
        holder.imageAddPaymentCard.setImageResource(ModelPaymentCard.imageAddPaymentCard)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageAddPaymentCard : ImageView = itemView.findViewById(R.id.imageAddPaymentCard)

            }
}