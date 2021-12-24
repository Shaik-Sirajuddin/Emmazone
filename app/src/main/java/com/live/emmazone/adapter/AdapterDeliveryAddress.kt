package com.live.emmazone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.activities.listeners.OnActionListener
import com.live.emmazone.databinding.ItemDeliveryAddressBinding
import com.live.emmazone.model.ModelDeliveryAddress

class AdapterDeliveryAddress(
    private val list: ArrayList<ModelDeliveryAddress>,
    private var onActionListener: OnActionListener<ModelDeliveryAddress>) :
    RecyclerView.Adapter<AdapterDeliveryAddress.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDeliveryAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        with(holder.binding) {
            tvOrderPersonName.text = model.tvOrderPersonName
            tvOrderDeliveryAddress.text = model.tvOrderDeliveryAddress

            radioBtnDeliveryAdrs.isChecked = model.isSelected
            radioBtnDeliveryAdrs.isClickable = false

            layoutItemDeliveryAdrs.setOnClickListener {
                onActionListener.notify(model, holder.adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: ItemDeliveryAddressBinding) :
        RecyclerView.ViewHolder(binding.root)
}