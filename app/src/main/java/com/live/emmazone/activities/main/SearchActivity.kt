package com.live.emmazone.activities.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import com.live.emmazone.R
import com.live.emmazone.activities.Interface.OnItemClick
import com.live.emmazone.adapter.AdapterShopDetailProducts
import com.live.emmazone.databinding.ActivitySearchBinding
import com.live.emmazone.model.ModelShopDetailProducts

class SearchActivity : AppCompatActivity(), OnItemClick {
    lateinit var binding : ActivitySearchBinding
    val list = ArrayList<ModelShopDetailProducts>()
    lateinit var adapter : AdapterShopDetailProducts

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)


   binding.cart.setOnClickListener {
       val intent = Intent(this, Cart::class.java)
       startActivity(intent)
   }

        binding.imageNotifications.setOnClickListener {
            val intent = Intent(this, Notifications::class.java)
            startActivity(intent)
        }

        binding.imageFilterHome.setOnClickListener {
            val intent = Intent(this, FilterActivity::class.java)
            startActivity(intent)
        }

        binding.imageBack.setOnClickListener {
            onBackPressed()
        }

        binding.recyclerSearch.layoutManager = GridLayoutManager(this, 2)

        list.add(ModelShopDetailProducts(R.drawable.bike2, "Bernd", "30.00€", "Lorem ipsum dolor",
            "4.8", "Delivery estimate 4-5 days"))

        list.add(ModelShopDetailProducts(R.drawable.bike1, "Matrix", "30.00€", "Lorem ipsum dolor",
            "4.8", "Delivery estimate 4-5 days"))

        list.add(ModelShopDetailProducts(R.drawable.bike2, "Bernd", "30.00€", "Lorem ipsum dolor",
            "4.8", "Delivery estimate 4-5 days"))

        list.add(ModelShopDetailProducts(R.drawable.bike3, "Matrix", "30.00€", "Lorem ipsum dolor",
            "4.8", "Delivery estimate 4-5 days"))

        binding.recyclerSearch.adapter = AdapterShopDetailProducts(list, this)

    }

    override fun onCellClickListener() {
       val intent = Intent(this, ProductDetailActivity::class.java)
        startActivity(intent)
    }

    override fun onClick() {
        TODO("Not yet implemented")
    }

    override fun onClickPickCollect() {
        TODO("Not yet implemented")
    }

    override fun onOrderCancelled() {
        TODO("Not yet implemented")
    }
}