package com.live.emmazone.activities.provider

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterProvODNewSalesStatus
import com.live.emmazone.databinding.ActivityOrderDetailNewSaleBinding
import com.live.emmazone.model.ModelOnGoingOrders
import com.live.emmazone.response_model.SalesResponse
import com.live.emmazone.utils.AppUtils

class OrderDetailNewSaleActivity : AppCompatActivity() {

    lateinit var binding: ActivityOrderDetailNewSaleBinding
    val list = ArrayList<SalesResponse.SaleResponseBody>()
    val listChildRecycler = ArrayList<ModelOnGoingOrders>()
    lateinit var adapter: AdapterProvODNewSalesStatus

    private var model: SalesResponse.SaleResponseBody? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailNewSaleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = intent?.extras!!.get("data") as SalesResponse.SaleResponseBody

        setData(model!!)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.btnReadyDelivery.setOnClickListener {
            onBackPressed()
        }

        binding.rvOrderDetailNewSale.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


     //   binding.rvOrderDetailNewSale.adapter = AdapterProvODNewSalesStatus(this, list)

    }

    private fun setData(model: SalesResponse.SaleResponseBody) {
        binding.tvSubTotalPrice.text= model.netAmount
        binding.tvTotalPrice.text=  getString(R.string.euro_symbol,model.total.toDouble().toString())
        model.orderJson.userAddress.apply {
            binding.tvOrderPersonName.text= this.name
            binding.tvOrderDeliveryAddress.text= this.address+","+this.city+","+this.state+","+this.zipcode
        }
       binding.tvODOrderDate.text= AppUtils.getDateTime(model.created)
        when (model.orderStatus) {
            0 -> { //  order status  0-> Pending  1-> on the way 2-> Delivered 3-> cancelled
                binding.tvOrderStatus.text= getString(R.string.pending)
            }
            1 -> {
                binding.tvOrderStatus.text= getString(R.string.on_the_way)
            }
            2 -> {
                binding.tvOrderStatus.text= getString(R.string.delivered)
            }
        }

        when (model.deliveryType) {
            0 -> {   //   0-> click & collect 1-> Lifernado 2-> Own Delivery
                binding.tvPayment.text = getString(R.string.click_and_collect)
            }
            1 -> {
                binding.tvPayment.text = getString(R.string.lifernado)
            }
            2 -> {
                binding.tvPayment.text = getString(R.string.own_delivery)
            }
        }



    }


}