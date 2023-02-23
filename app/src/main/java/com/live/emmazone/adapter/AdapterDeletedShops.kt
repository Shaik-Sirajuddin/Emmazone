package com.live.emmazone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.databinding.ItemDeletedShopBinding
import com.live.emmazone.model.DeletedShopsModel
import com.live.emmazone.utils.AppUtils
import java.util.*
import kotlin.collections.ArrayList

class AdapterDeletedShops(
    private val context: Context,
    private val list: ArrayList<DeletedShopsModel>,
    private val delete: (pos: Int) -> Unit,
) : RecyclerView.Adapter<AdapterDeletedShops.ViewHolder>() {

    class ViewHolder(val binding: ItemDeletedShopBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemDeletedShopBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        with(holder.binding) {
            userId.text = item.userId.toString()
            shopName.text = item.shopName
            delete.setOnClickListener {
                delete(position)
            }
            val milliSeconds = Date().time / 1000L - Date(item.created).time
            requestDate.text = AppUtils.getNotificationTimeAgo(milliSeconds)
        }
    }

    override fun getItemCount(): Int = list.size
}
