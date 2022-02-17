package com.live.emmazone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.databinding.ItemLayoutPaymentMethodBinding
import com.live.emmazone.response_model.CardListResponse

class AdapterAddPaymentCard(val cardList: ArrayList<CardListResponse.Body>) :
    RecyclerView.Adapter<AdapterAddPaymentCard.CardViewHolder>() {

    var onItemClickListener: ((pos: Int, clickOn: String) -> Unit)? = null
    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        mContext = parent.context
        val binding = ItemLayoutPaymentMethodBinding.inflate(LayoutInflater.from(parent.context))
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return cardList.size + 1
    }

    inner class CardViewHolder(val binding: ItemLayoutPaymentMethodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pos: Int) {

            if (pos != 0) {
                binding.llAddCard.visibility = View.GONE
                binding.rlCard.visibility = View.VISIBLE
                val cardResponse = cardList[pos - 1]

                binding.tvCardNo.text = "**** **** ****" + (cardResponse.cardNumber.substring(
                    (cardResponse.cardNumber.length - 4),
                    cardResponse.cardNumber.length
                ))

                val expDate = cardResponse.month.toString() + "/" + cardResponse.year.toString()
                binding.tvExpiry.text = mContext.getString(R.string.expired, expDate)

            } else {
                binding.llAddCard.visibility = View.VISIBLE
                binding.rlCard.visibility = View.GONE

            }


            if ((pos - 1) % 2 == 0) {
                binding.rlCard.background =
                    ContextCompat.getDrawable(mContext, R.drawable.bg_green_15dp)
            } else {
                binding.rlCard.background =
                    ContextCompat.getDrawable(mContext, R.drawable.bg_light_gray_15dp)
            }


            itemView.setOnClickListener {
                if (pos == 0) {
                    onItemClickListener?.invoke(pos, "addCard")
                } else {
                    onItemClickListener?.invoke((pos - 1), "selectCard")
                }

            }
        }
    }
}