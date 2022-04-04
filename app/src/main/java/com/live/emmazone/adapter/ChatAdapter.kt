package com.live.emmazone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.databinding.ItemChatLeftBinding
import com.live.emmazone.databinding.ItemChatRightBinding
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.response_model.socket_response.ChatModel
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.schunts.extensionfuncton.loadImage


class ChatAdapter(val list: ArrayList<ChatModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemClickListener: ((pos: Int) -> Unit)? = null

    val LEFT_LAYOUT = 1
    val RIGHT_LAYOUT = 2

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        if (viewType == RIGHT_LAYOUT) {
            val rightBinding = ItemChatRightBinding.inflate(LayoutInflater.from(parent.context))
            return RightViewHolder(rightBinding)
        } else {
            val leftBinding = ItemChatLeftBinding.inflate(LayoutInflater.from(parent.context))
            return LeftViewHolder(leftBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == RIGHT_LAYOUT) {
            (holder as RightViewHolder).bind(position)
        } else {
            (holder as LeftViewHolder).bind(position)

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].senderId.toString() == getPreference(AppConstants.USER_ID, "")) {
            RIGHT_LAYOUT
        } else {
            LEFT_LAYOUT
        }
    }

    inner class LeftViewHolder(val binding: ItemChatLeftBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            binding.civUser.loadImage(list[pos].receiverImg)
            binding.tvMsg.text = list[pos].msg
            binding.tvTime.text =
                AppUtils.secondsToTime(list[pos].created.toLong(), AppConstants.DATE_FORMAT)
        }
    }

    inner class RightViewHolder(val binding: ItemChatRightBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pos: Int) {
            val chatModel = list[pos]

            binding.civUser.loadImage(chatModel.senderImg)
            binding.tvMsg.text = chatModel.msg
            binding.tvTime.text =
                AppUtils.secondsToTime(chatModel.created.toLong(), AppConstants.DATE_FORMAT)

        }
    }
}