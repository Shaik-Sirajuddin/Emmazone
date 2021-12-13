package com.live.emmazone.activities.provider

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.activities.Interface.OnItemClick
import com.live.emmazone.adapter.AdapterProvODNewSalesStatus
import com.live.emmazone.adapter.AdapterProviderNewSales
import com.live.emmazone.databinding.ActivityOrderDetailNewSaleBinding
import com.live.emmazone.model.ModelNewSaleOrderDetail
import com.live.emmazone.model.ModelOnGoingOrders
import com.live.emmazone.model.ModelProviderNewSale

class OrderDetailNewSaleActivity : AppCompatActivity() {

    lateinit var binding: ActivityOrderDetailNewSaleBinding
    val list = ArrayList<ModelNewSaleOrderDetail>()
    val listChildRecycler = ArrayList<ModelOnGoingOrders>()
    lateinit var adapter: AdapterProvODNewSalesStatus

    private var model: ModelProviderNewSale? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailNewSaleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = intent?.getSerializableExtra("model") as ModelProviderNewSale?

        binding.back.setOnClickListener {
            onBackPressed()
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        binding.btnReadyDelivery.setOnClickListener {
            onBackPressed()
        }

        binding.rvOrderDetailNewSale.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        listChildRecycler.add(
            ModelOnGoingOrders(
                R.drawable.shoes_square,
                "Brend Shoe",
                "03",
                "90.00€"
            )
        )
        listChildRecycler.add(
            ModelOnGoingOrders(
                R.drawable.shoes_square,
                "Brend Shoe",
                "03",
                "90.00€"
            )
        )

        list.add(
            ModelNewSaleOrderDetail(
                "PLU9540572", R.drawable.avtarr_girl, "Allen Chandler",
                R.drawable.chat, listChildRecycler
            )
        )

        binding.rvOrderDetailNewSale.adapter = AdapterProvODNewSalesStatus(list)

    }


}