package com.live.emmazone.activities.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterOrderDetail
import com.live.emmazone.databinding.ActivityReservedDeliveredDetailBinding
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.response_model.UserOrderListing
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.loadImage

class ReservedDeliveredDetail : AppCompatActivity(), Observer<RestObservable> {
    private val appViewModel: AppViewModel by viewModels()

    lateinit var binding: ActivityReservedDeliveredDetailBinding
    val list = ArrayList<UserOrderListing.Body.Response.OrderJson.OrderItem>()
    lateinit var adapter: AdapterOrderDetail

    var userData: UserOrderListing.Body.Response? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservedDeliveredDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userData = intent.extras!!.get("data") as UserOrderListing.Body.Response

        setData(userData!!)

        binding.btnQRScanner.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            val factory = LayoutInflater.from(this)
            val view: View = factory.inflate(R.layout.dialog_scan_qr_code, null)
            alertDialog.setView(view)

            val buttonCross = view.findViewById<ImageView>(R.id.crossImage)
            val ivScanQR = view.findViewById<ImageView>(R.id.scanQR)

            ivScanQR.loadImage(userData?.qrCode!!)

            buttonCross.setOnClickListener {
                onBackPressed()
            }

            alertDialog.show()
        }

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.btnCancel.setOnClickListener {
            orderStatusApiHit()
        }

        binding.recyclerOrderDetail.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.recyclerOrderDetail.adapter = AdapterOrderDetail(this, list,true)

    }

    private fun setData(data: UserOrderListing.Body.Response) {
        list.addAll(data.orderJson.orderItems)
        adapter = AdapterOrderDetail(this, list,true)
        binding.tvOrderID.text = data.orderNo
        binding.tvSubTotalPrice.text = data.netAmount
        binding.tvItemCount.text = data.orderJson.orderItems.size.toString()
        binding.tvDeliveryChargesPrice.text = getString(R.string.euro_symbol, data.shippingCharges)
        binding.tvTaxPrice.text = getString(R.string.euro_symbol, data.taxCharged)
        binding.tvTotalPrice.text = getString(R.string.euro_symbol, data.total)
        binding.tvODOrderDate.text = AppUtils.getDateTime(data.created.toLong())

        data.orderJson.userAddress.apply {
            binding.tvOrderPersonName.text = this.name
            binding.tvOrderDeliveryAddress.text =
                this.address + "," + this.city + "," + this.state + "," + this.zipcode
        }
        when (data.orderStatus) {
            0 -> { //  order status  0-> Pending  1-> on the way 2-> Delivered 3-> cancelled
                binding.tvOrderStatus.text = getString(R.string.pending)
                binding.btnCancel.visibility = View.VISIBLE
            }
            1 -> {
                binding.tvOrderStatus.text = getString(R.string.on_the_way)
                binding.btnCancel.visibility = View.GONE
            }
            2 -> {
                binding.tvOrderStatus.text = getString(R.string.completed)
                binding.btnCancel.visibility = View.GONE
                binding.btnQRScanner.visibility = View.GONE
                binding.tvQRCode.visibility = View.GONE
            }
            3 -> {
                binding.tvOrderStatus.text = getString(R.string.cancel)
                binding.btnCancel.visibility = View.GONE
                binding.btnQRScanner.visibility = View.GONE
                binding.tvQRCode.visibility = View.GONE
            }
        }
        when (data.deliveryType) {
            0 -> {   //   0-> click & collect 1-> Lifernado 2-> Own Delivery
                binding.tvDeliveryAddress.visibility = View.GONE
                binding.layoutItemDeliveryAdrs.visibility = View.GONE
            }
            1 -> {
                binding.btnQRScanner.visibility = View.GONE
                binding.tvQRCode.visibility = View.GONE
            }
            2 -> {
                binding.btnQRScanner.visibility = View.GONE
                binding.tvQRCode.visibility = View.GONE
            }
        }

        when (data.paymentMethod) {
            0 -> {   //  0=>Wallet 1=>Card 2=>cash
                binding.tvPaymentType.text = getString(R.string.wallet)
            }
            1 -> {
                binding.tvPaymentType.text = getString(R.string.card)
            }
            2 -> {
                binding.tvPaymentType.text = getString(R.string.cash_on_delivery)
            }
        }

    }

    private fun orderStatusApiHit() {
        val hashMap = HashMap<String, String>()
        hashMap["id"] = userData!!.id.toString()
        hashMap["orderStatus"] = "3" // 0=>pending 1=>On The Way 2=>Delivered 3=>cancelled

        appViewModel.cancelOrderApi(this, hashMap, true)
        appViewModel.getResponse().observe(this, this)

    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is CommonResponse) {
                    onBackPressed()
                }
            }
            else -> {}
        }
    }
}