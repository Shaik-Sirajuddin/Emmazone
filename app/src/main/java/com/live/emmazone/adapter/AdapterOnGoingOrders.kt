package com.live.emmazone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.activities.listeners.OnActionListenerNew
import com.live.emmazone.databinding.ItemLayoutOngoingMyordersBinding
import com.live.emmazone.model.ModelOnGoingOrders

class AdapterOnGoingOrders(
    private val context: Context,
    private val list: ArrayList<ModelOnGoingOrders>,
    private val onActionListenerNew: OnActionListenerNew? = null
) :
    RecyclerView.Adapter<AdapterOnGoingOrders.ViewHolder>() {

    class ViewHolder(val binding: ItemLayoutOngoingMyordersBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLayoutOngoingMyordersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        with(holder.binding) {
            onGoingItem.setImageResource(model.onGoingItem)
            onGoingItemName.text = model.onGoingItemName
            onGoingItemQuantity.text = model.onGoingItemQuantity
            productPrice.text = model.productPrice
        }

        holder.itemView.setOnClickListener {
            onActionListenerNew?.notifyOnClick()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}