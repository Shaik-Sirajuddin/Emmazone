package com.live.emmazone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.response_model.NotificationListingResponse
import com.live.emmazone.utils.AppUtils
import com.schunts.extensionfuncton.loadImage

class AdapterNotifications(private val list: ArrayList<NotificationListingResponse.Body>) :
    RecyclerView.Adapter<AdapterNotifications.ViewHolder>() {

    private lateinit var mContext: Context
    var onItemClick: ((pos: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_notifications, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvOrderPerson.text = list[position].user.username
        holder.tvDeliveryAddress.text = list[position].message
        holder.tvnotifyTime.text = AppUtils.getNotificationTimeAgo(list[position].created.toLong())

        holder.image.loadImage(list[position].user.image)

        if (list[position].isRead == 1) {
            holder.itemView.background =
                ContextCompat.getDrawable(mContext, R.drawable.bg_shop_detail_product)
            holder.tvOrderPerson.setTextColor(mContext.getColor(R.color.black))
            holder.tvDeliveryAddress.setTextColor(mContext.getColor(R.color.black))
            holder.tvnotifyTime.setTextColor(mContext.getColor(R.color.black))
        } else {
            holder.itemView.background = ContextCompat.getDrawable(mContext, R.drawable.bg_spinner)
            holder.tvOrderPerson.setTextColor(mContext.getColor(R.color.white))
            holder.tvDeliveryAddress.setTextColor(mContext.getColor(R.color.white))
            holder.tvnotifyTime.setTextColor(mContext.getColor(R.color.white))
        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image: ImageView = itemView.findViewById(R.id.profileImage)
        val tvOrderPerson = itemView.findViewById<TextView>(R.id.tvOrderPersonName)
        val tvDeliveryAddress = itemView.findViewById<TextView>(R.id.tvOrderDeliveryAddress)
        val tvnotifyTime = itemView.findViewById<TextView>(R.id.tvNotificationTime)

    }
}