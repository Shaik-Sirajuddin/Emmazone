package com.live.emmazone.activities.provider

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.live.emmazone.R
import com.live.emmazone.activities.listeners.OnActionListenerNew
import com.live.emmazone.adapter.AdapterOnGoingOrders
import com.live.emmazone.databinding.ActivityOrderDetailNewSaleBinding
import com.live.emmazone.model.ModelOnGoingOrders
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.response_model.SalesResponse
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel

class OrderDetailNewSaleActivity : AppCompatActivity(), Observer<RestObservable> {

    lateinit var binding: ActivityOrderDetailNewSaleBinding
    private val appViewModel: AppViewModel by viewModels()

    val listChildRecycler = ArrayList<ModelOnGoingOrders>()
    lateinit var adapter: AdapterOnGoingOrders

    private var model: SalesResponse.Body.Response? = null
    val list = ArrayList<SalesResponse.Body.Response.OrderJson.OrderItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailNewSaleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val onActionListenerNew = object : OnActionListenerNew {
            override fun notifyOnClick() {
                //openDetailScreen(model, holder.adapterPosition)
            }
        }

        setAdapter(onActionListenerNew)
        model = intent?.extras!!.get("data") as SalesResponse.Body.Response

        setData(model!!)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.btnReadyDelivery.setOnClickListener {
            orderStatusApiHit()
        }

        binding.rvOrderDetailNewSale.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        //   binding.rvOrderDetailNewSale.adapter = AdapterProvODNewSalesStatus(this, list)

    }

    private fun orderStatusApiHit() {
        val hashMap = HashMap<String, String>()
        hashMap["id"] = model!!.id.toString()
        hashMap["orderStatus"] = "1" // 0=>pending 1=>On The Way 2=>Delivered 3=>cancelled

        appViewModel.orderStatusApi(this, hashMap, true)
        appViewModel.getResponse().observe(this, this)

    }

    private fun setAdapter(onActionListenerNew: OnActionListenerNew) {
        adapter = AdapterOnGoingOrders(this, list, onActionListenerNew, "detail")
        binding.rvOrderDetailNewSale.adapter = adapter
    }

    private fun setData(model: SalesResponse.Body.Response) {
        list.addAll(model.orderJson.orderItems)
        adapter.notifyDataSetChanged()
        binding.tvOrderID.text = model.orderNo
        binding.tvUsername.text = model.customer.username
        Glide.with(this).load(model.customer.image).into(binding.imageSales)
        binding.tvSubTotalPrice.text = getString(R.string.euro_symbol, model.netAmount)
        binding.tvDeliveryChargesPrice.text = getString(R.string.euro_symbol, model.shippingCharges)
        binding.tvTaxPrice.text = getString(R.string.euro_symbol, model.taxCharged)
        binding.tvTotalPrice.text =
            getString(R.string.euro_symbol, model.total.toDouble().toString())
        model.orderJson.userAddress.apply {
            binding.tvOrderPersonName.text = this.name
            binding.tvOrderDeliveryAddress.text =
                this.address + "," + this.city + "," + this.state + "," + this.zipcode
        }
        binding.tvODOrderDate.text = AppUtils.getDateTime(model.created.toLong())
        when (model.orderStatus) {
            0 -> { //  order status  0-> Pending  1-> on the way 2-> Delivered 3-> cancelled
                binding.tvOrderStatus.text = getString(R.string.pending)
            }
            1 -> {
                binding.tvOrderStatus.text = getString(R.string.on_the_way)
            }
            2 -> {
                binding.tvOrderStatus.text = getString(R.string.delivered)
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

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is CommonResponse){
                    onBackPressed()
                }
            }
        }
    }


}