package com.live.emmazone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.databinding.PostalCodeItemBinding
import com.live.emmazone.response_model.PostalCodesResponse

class ShopPostalCodesAdapter(
    val context: Context,
    val list: ArrayList<PostalCodesResponse.PostalCodeItem>,
    val onClick: (position: Int) -> Unit
) :
    RecyclerView.Adapter<ShopPostalCodesAdapter.ShopPostalCodeViewHolder>() {


    inner class ShopPostalCodeViewHolder(val binding: PostalCodeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pos: Int) {
            binding.pinCode.text = list[pos].postalCode.toString()
            binding.delete.setOnClickListener {
                onClick(pos)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopPostalCodeViewHolder {
        val binding =
            PostalCodeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShopPostalCodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopPostalCodeViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = list.size
}