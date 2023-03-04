package com.live.emmazone.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.live.emmazone.activities.listeners.OnActionListenerNew
import com.live.emmazone.adapter.AdapterOnGoingProducts
import com.live.emmazone.databinding.ActivityReturnItemsChooserBinding
import com.live.emmazone.response_model.UserOrderListing
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils.Companion.showToast

class ReturnItemsChooser : AppCompatActivity() {

    private lateinit var binding: ActivityReturnItemsChooserBinding
    private lateinit var adapter: AdapterOnGoingProducts
    private lateinit var orderItems: ArrayList<UserOrderListing.Body.Response.OrderJson.OrderItem>
    private val checkedList = arrayListOf<Boolean>()
    private var orderId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReturnItemsChooserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        orderItems = intent.getSerializableExtra(AppConstants.ORDER_ITEMS)
                as ArrayList<UserOrderListing.Body.Response.OrderJson.OrderItem>
        orderId = intent.getStringExtra(AppConstants.ORDER_ID).toString()
        orderItems.forEach { _ ->
            checkedList.add(false)
        }

        adapter = AdapterOnGoingProducts(this, orderItems, object : OnActionListenerNew {
            override fun notifyOnClick(pos: Int) {
                checkedList[pos] = !checkedList[pos]
                adapter.notifyItemChanged(pos)
            }
        }, "", checkedList)
        binding.recyclerView.adapter = adapter
        binding.continuButton.setOnClickListener {
            clickHandle()
        }
    }

    private fun clickHandle() {
        val idsList = arrayListOf<Int>()
        for (i in 0 until orderItems.size) {
            if (checkedList[i]) {
                idsList.add(orderItems[i].id)
            }
        }
        if (idsList.isEmpty()) {
            showToast("No products selected")
            return
        }
        val intent = Intent(this, ReturnActivity::class.java)
        intent.putExtra(AppConstants.ORDER_ID, orderId)
        intent.putExtra(AppConstants.ORDER_ITEMS, idsList)
        startActivity(intent)
        finish()
    }
}