package com.live.emmazone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.response_model.GetBankResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import kotlinx.android.synthetic.main.item_my_bank_account.view.*

class MyBankAccountAdapter(var list: ArrayList<GetBankResponse.Body>) :
    RecyclerView.Adapter<MyBankAccountAdapter.MyCardsViewHolder>() {

    var onItemClick: ((pos: Int, clickOn: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCardsViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_my_bank_account, parent, false)
        return MyCardsViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyCardsViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyCardsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val llDelete: LinearLayout = itemView.llDelete
        val tvCardName: TextView = itemView.tvCardName
        val tvCardNo: TextView = itemView.tvCardNo
        val tvAddDate: TextView = itemView.tvAddDate
        val llBank: LinearLayout = itemView.llBank
        val ivSelect: ImageView = itemView.ivSelect

        fun bind(pos: Int) {

            val bank = list[pos]

            tvCardName.text = bank.bankBranch
            tvCardNo.text = "xxxx xxxx xxxx" + bank.accountNumber.subSequence(
                (bank.accountNumber.length - 4),
                bank.accountNumber.length
            )

            tvAddDate.text = "Added on " + AppUtils.toDate(bank.createdAt, AppConstants.DATE_FORMAT)


            llDelete.setOnClickListener {
                onItemClick?.invoke(pos, "delete")
            }

            llBank.setOnClickListener {
                onItemClick?.invoke(pos, "itemClick")
            }

            if (bank.isSelected){
                ivSelect.setImageResource(R.drawable.radio_dot_circle)
            }else{
                ivSelect.setImageResource(R.drawable.radio_circle)
            }
        }
    }
}