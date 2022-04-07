package com.live.emmazone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.response_model.AllTransactionsResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import kotlinx.android.synthetic.main.item_transaction.view.*

class AllTransactionAdapter(val list: ArrayList<AllTransactionsResponse.Body.Withdrawlist>) :
    RecyclerView.Adapter<AllTransactionAdapter.AllTransactionViewHolder>() {

    lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTransactionViewHolder {

        mContext = parent.context
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.item_transaction, parent, false)
        return AllTransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllTransactionViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class AllTransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(pos: Int) {
            val transaction = list[pos]

            itemView.tvDate.text = AppUtils.secondsToTime(
                transaction.created.toLong(),
                AppConstants.DATE_FORMAT
            )

            /*  itemView.tvTime.text = AppUtils.secondsToTime(
                  transaction.created.toLong(),
                  AppConstants.TIME_FORMAT
              )*/

            itemView.tvPrice.text = "€"+transaction.amount.toString()
            itemView.tvCardName.text = transaction.bank?.bankBranch
            itemView.tvAddDate.text =
                "xxxx xxxx xxxx" + transaction.bank?.accountNumber?.subSequence(
                    (transaction.bank?.accountNumber?.length!! - 4),
                    transaction.bank?.accountNumber?.length!!
                )

            if (transaction.status == 0) {
                itemView.tvStatus.text = mContext.getString(R.string.pending)
            } else if (transaction.status == 1) {
                itemView.tvStatus.text = mContext.getString(R.string.accepted)
            } else if (transaction.status == 2) {
                itemView.tvStatus.text = mContext.getString(R.string.rejected)
            }
        }
    }
}