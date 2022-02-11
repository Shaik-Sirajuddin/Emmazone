package com.live.emmazone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.databinding.ItemLayoutPaymentMethodBinding
import com.live.emmazone.model.ModelDeliveryAddress
import com.live.emmazone.model.ModelNotifications
import com.live.emmazone.model.ModelPaymentCard
import com.live.emmazone.model.ModelWishList

class AdapterAddPaymentCard() :
    RecyclerView.Adapter<AdapterAddPaymentCard.CardViewHolder>() {

    var onItemClickListener: ((pos: Int, clickOn: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = ItemLayoutPaymentMethodBinding.inflate(LayoutInflater.from(parent.context))
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return 1
    }

    inner class CardViewHolder(val binding: ItemLayoutPaymentMethodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pos: Int) {


            itemView.setOnClickListener {
                if (pos == 0) {
                    onItemClickListener?.invoke(pos, "addCard")
                } else {
                    onItemClickListener?.invoke(pos, "selectCard")
                }

            }
        }
    }
}