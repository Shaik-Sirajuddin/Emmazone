package com.live.emmazone.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.tubb.smrv.SwipeHorizontalMenuLayout
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_message.view.*

class MessageAdapter :
    RecyclerView.Adapter<MessageAdapter.MessageListViewHolder>() {

    var onItemCLick: ((pos: Int, clickOn: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return MessageListViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageListViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return 5
    }

    inner class MessageListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val civProfile: CircleImageView = itemView.civProfile
        private val tvName: TextView = itemView.tvName
        private val tvMessage: TextView = itemView.tvMessage
        private val tvMessageTime: TextView = itemView.tvTime
        private val tvMessageCount: TextView = itemView.tvMessageCount

        fun bind(pos: Int) {
            itemView.setOnClickListener {
                onItemCLick?.invoke(pos, "itemClick")

            }

        }
    }
}