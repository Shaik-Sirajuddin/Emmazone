package com.live.emmazone.activities.main

import android.content.Intent
import android.icu.util.TimeUnit
import android.os.Bundle
import android.util.Log
import android.util.TimeUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.activities.ReturnActivity
import com.live.emmazone.adapter.AdapterOrderDetail
import com.live.emmazone.databinding.ActivityReservedDeliveredDetailBinding
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.response_model.ScanOrderResponse
import com.live.emmazone.response_model.UserOrderListing
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.loadImage
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ReservedDeliveredDetail : AppCompatActivity(), Observer<RestObservable> {
    private val appViewModel: AppViewModel by viewModels()

    lateinit var binding: ActivityReservedDeliveredDetailBinding
    val list = ArrayList<UserOrderListing.Body.Response.OrderJson.OrderItem>()
    lateinit var adapter: AdapterOrderDetail
    private var isOnGoing = false
    var userData: UserOrderListing.Body.Response? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservedDeliveredDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userData = intent.extras!!.get("data") as UserOrderListing.Body.Response
        isOnGoing = intent.extras!!.get("onGoing") as Boolean
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
            performUpdateStatus()
        }
        binding.rateShop.setOnClickListener {
            rateShop()
        }
        binding.recyclerOrderDetail.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.recyclerOrderDetail.adapter = AdapterOrderDetail(this, list,isOnGoing)
        initRateShop()
    }
    private fun initRateShop(){
        if(isOnGoing){
            binding.rateShop.visibility = View.GONE
        }
        else{
            binding.rateShop.visibility = View.VISIBLE
        }
    }
    private fun rateShop(){
        if(list.size>0){
            val intent = Intent(this,ShopReviewsActivity::class.java)
            intent.putExtra("vendorId",list[0].vendorId.toString())
            startActivity(intent)
        }
    }
    private fun performUpdateStatus(){
        val status = userData!!.orderStatus
        if(status == 0){
            orderStatusApiHit(3)
        }
        else if(status == 2 ){

            val milliSeconds = Date().time/1000L - Date(userData!!.created).time
            val returnPeriodInSeconds = 604800000
            Log.e("dif" , milliSeconds.toString())
            Log.e("time",returnPeriodInSeconds.toString())
            if(milliSeconds>=returnPeriodInSeconds){
                AppUtils.showMsgOnlyWithoutClick(this,"Return period closed")
            }
            else{
                val intent = Intent(this,ReturnActivity::class.java)
                intent.putExtra(AppConstants.ORDER_ID,userData!!.id.toString())
                startActivity(intent)
                finish()
            }
        }
        else if(status == 7){
            orderStatusApiHit(2)
        }
    }

    private fun setData(data: UserOrderListing.Body.Response) {
        list.addAll(data.orderJson.orderItems)

        adapter = AdapterOrderDetail(this, list,isOnGoing)
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
                binding.btnCancel.text = "Return"
                binding.btnCancel.visibility = View.VISIBLE
                binding.btnQRScanner.visibility = View.GONE
                binding.tvQRCode.visibility = View.GONE
            }
            3 -> {
                binding.tvOrderStatus.text = getString(R.string.cancel)
                binding.btnCancel.visibility = View.GONE
                binding.btnQRScanner.visibility = View.GONE
                binding.tvQRCode.visibility = View.GONE
            }
            7->{
                binding.tvOrderStatus.text =  getString(R.string.return_in_transit)
                binding.btnCancel.text = "Cancel Return"
                binding.btnCancel.visibility = View.VISIBLE
            }
            8->{
                binding.tvOrderStatus.text = getString(R.string.returned)
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

    private fun orderStatusApiHit(status : Int) {
        val hashMap = HashMap<String, String>()
        hashMap["id"] = userData!!.id.toString()
        hashMap["orderStatus"] = status.toString() //
        // 0=>pending 1=>On The Way 2=>Delivered 3=>cancelled  7=> Return in transit
        if(status == 3){
            appViewModel.cancelOrderApi(this, hashMap, true)
        }
        else{
            Log.d("status", hashMap["orderStatus"].toString())
            appViewModel.orderStatusApi(this,hashMap,true)
        }
        appViewModel.getResponse().observe(this, this)
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is CommonResponse) {
                    onBackPressed()
                }
                if(t.data is ScanOrderResponse){
                    onBackPressed()
                }
            }
            else -> {}
        }
    }
}