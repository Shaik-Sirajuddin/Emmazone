package com.live.emmazone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.activities.listeners.OnActionListener
import com.live.emmazone.databinding.ItemDeliveryAddressBinding
import com.live.emmazone.response_model.AddressListResponse

class AdapterDeliveryAddress(
    private val list: ArrayList<AddressListResponse.Body>
) :
    RecyclerView.Adapter<AdapterDeliveryAddress.ViewHolder>() {

    var onClickListener: ((pos: Int, clickOn: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDeliveryAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        with(holder.binding) {
            tvOrderPersonName.text = model.name
            tvOrderDeliveryAddress.text = model.address + " , ".plus(model.city)

            radioBtnDeliveryAdrs.isChecked = model.isSelected
            radioBtnDeliveryAdrs.isClickable = false

            ivDelete.setOnClickListener {
                onClickListener?.invoke(position, "delete")
            }

            layoutItemDeliveryAdrs.setOnClickListener {
                onClickListener?.invoke(position, "item")
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: ItemDeliveryAddressBinding) :
        RecyclerView.ViewHolder(binding.root)

}