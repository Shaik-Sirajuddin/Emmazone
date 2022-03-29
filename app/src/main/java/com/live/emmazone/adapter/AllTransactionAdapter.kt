package com.live.emmazone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R

class AllTransactionAdapter(/*val list: ArrayList<AllTransactionsResponse.Body.Transaction>*/) :
    RecyclerView.Adapter<AllTransactionAdapter.AllTransactionViewHolder>() {

    lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTransactionViewHolder {

        mContext = parent.context
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.item_transaction, parent, false)
        return AllTransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllTransactionViewHolder, position: Int) {
//        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return 5
    }

    inner class AllTransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


//        fun bind(pos: Int) {
//            val transaction = list[pos]
//
//            itemView.tvDate.text = AppUtils.longToTime(
//                transaction.created_at.toLong(),
//                AppConstant.DATE_FORMAT
//            )
//
//            itemView.tvTime.text = AppUtils.longToTime(
//                transaction.created_at.toLong(),
//                AppConstant.TIME_FORMAT
//            )
//
//            itemView.tvPrice.text = transaction.amount
//            itemView.tvCardName.text = transaction.user_bank.bank_branch
//            itemView.tvAddDate.text =
//                "xxxx xxxx xxxx" + transaction.user_bank.account_number.subSequence(
//                    (transaction.user_bank.account_number.length - 4),
//                    transaction.user_bank.account_number.length
//                )
//
//            if (transaction.status == 0){
//                itemView.tvStatus.text = mContext.getString(R.string.pending)
//            }else if (transaction.status == 1){
//                itemView.tvStatus.text = mContext.getString(R.string.accepted)
//            }else if (transaction.status == 2){
//                itemView.tvStatus.text = mContext.getString(R.string.rejected)
//            }
//        }
    }
}