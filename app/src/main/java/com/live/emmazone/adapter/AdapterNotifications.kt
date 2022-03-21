package com.live.emmazone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.response_model.NotificationListingResponse
import com.live.emmazone.utils.AppUtils
import com.schunts.extensionfuncton.loadImage

class AdapterNotifications(private val list: ArrayList<NotificationListingResponse.Body>) :
    RecyclerView.Adapter<AdapterNotifications.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_notifications, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvOrderPerson.text = list[position].username
        holder.tvDeliveryAddress.text = list[position].message
        holder.tvnotifyTime.text = AppUtils.getNotificationTimeAgo(list[position].created.toLong())

        holder.image.loadImage(list[position].user_image)

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