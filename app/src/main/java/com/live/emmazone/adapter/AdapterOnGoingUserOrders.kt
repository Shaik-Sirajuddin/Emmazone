package com.live.emmazone.adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.activities.listeners.OnActionListenerNew
import com.live.emmazone.activities.main.ReservedDeliveredDetail
import com.live.emmazone.databinding.ItemLayoutOngoingUserLayoutBinding
import com.live.emmazone.response_model.UserOrderListing
import com.live.emmazone.utils.AppUtils
import com.schunts.extensionfuncton.loadImage

class AdapterOnGoingUserOrders(
    private val context: Context,
    private val list: ArrayList<UserOrderListing.Body.Response>,
    private val onGoingFragment: Boolean
) :
    RecyclerView.Adapter<AdapterOnGoingUserOrders.ViewHolder>() {

    class ViewHolder(val binding: ItemLayoutOngoingUserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLayoutOngoingUserLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        with(holder.binding) {
            tvOrderID.text = model.orderNo
            tvODOrderDate.text = AppUtils.getDateTime(model.created.toLong())


            if (onGoingFragment) {
                if (model.deliveryType == 0) {
                    imgCodeScanner.visibility = View.VISIBLE
                } else {
                    imgCodeScanner.visibility = View.GONE
                }

            } else {
                imgCodeScanner.visibility = View.GONE
            }


            when (model.orderStatus) {
                0 -> { //  order status  0-> Pending  1-> on the way 2-> Delivered 3-> cancelled
                    //7-> Return in transit // 8-> Returned
                    tvOrderStatus.text = context.getString(R.string.pending)
                }
                1 -> {
                    tvOrderStatus.text = context.getString(R.string.on_the_way)
                }
                2 -> {
                    tvOrderStatus.text = context.getString(R.string.completed)
                }
                3 -> {
                    tvOrderStatus.text = context.getString(R.string.cancel)
                }
                7 -> {
                    tvOrderStatus.text = "Return in transit"
                }
                8 -> {
                    tvOrderStatus.text = "Returned"
                }
            }

            imgCodeScanner.setOnClickListener {
                scannerDialog(model.qrCode)
            }

            val onActionListenerNew = object : OnActionListenerNew {
                override fun notifyOnClick(pos: Int) {
                    openDetailScreen(model, holder.adapterPosition)
                }
            }
            rvMyOrderOnGoing.adapter =
                AdapterOnGoingProducts(
                    context,
                    model.orderJson.orderItems,
                    onActionListenerNew,
                    "list"
                )

        }
    }


    private fun openDetailScreen(model: UserOrderListing.Body.Response, position: Int) {
        val intent = Intent(context, ReservedDeliveredDetail::class.java)
        intent.putExtra("data", model)
        intent.putExtra("onGoing", onGoingFragment)
        context.startActivity(intent)
    }


    private fun scannerDialog(scanImage: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)

        dialog.setContentView(R.layout.dialog_scan_qr_code)

        dialog.window?.apply {

            setLayout(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            setBackgroundDrawable(
                ContextCompat.getDrawable(
                    context,
                    android.R.color.transparent
                )
            )
        }

        val backIcon = dialog.findViewById<ImageView>(R.id.crossImage)
        val ivScanQR = dialog.findViewById<ImageView>(R.id.scanQR)

        ivScanQR.loadImage(scanImage)

        backIcon.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}