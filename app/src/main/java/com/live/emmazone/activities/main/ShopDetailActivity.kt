package com.live.emmazone.activities.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.activities.listeners.OnItemClick
import com.live.emmazone.adapter.AdapterShopDetailCategory
import com.live.emmazone.adapter.AdapterShopDetailProducts
import com.live.emmazone.databinding.ActivityShopDetailBinding
import com.live.emmazone.model.ModelShopDetailCategory
import com.live.emmazone.model.ModelShopDetailProducts

class ShopDetailActivity : AppCompatActivity(), OnItemClick {
    lateinit var binding : ActivityShopDetailBinding
    var list = ArrayList<ModelShopDetailCategory>()
    var listSDProduct = ArrayList<ModelShopDetailProducts>()
    lateinit var adapter: AdapterShopDetailCategory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgNotify.setOnClickListener {
            val intent = Intent(this, Notifications::class.java)
            startActivity(intent)
        }

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.imageCart.setOnClickListener {
            val intent= Intent(this, Cart::class.java)
            startActivity(intent)
        }

        binding.imageAskExpert.setOnClickListener {
            val intent = Intent(this, Message::class.java)
            startActivity(intent)
        }

        binding.recyclerShopDetailCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerShopDetailProducts.layoutManager = GridLayoutManager(this, 2)

        list.add(ModelShopDetailCategory(R.drawable.all, "All"))
        list.add(ModelShopDetailCategory(R.drawable.shoe, "goggle"))
        list.add(ModelShopDetailCategory(R.drawable.google, "TimePiece"))
        list.add(ModelShopDetailCategory(R.drawable.time, "T Shirts"))
        list.add(ModelShopDetailCategory(R.drawable.tshiert, "goggle"))

        binding.recyclerShopDetailCategory.adapter = AdapterShopDetailCategory(list)

        listSDProduct.add(ModelShopDetailProducts(R.drawable.shoe_bernd, "Bernd",
            "30.00€", "Lorem ipsum dolor",
        "4.8", "Delivery estimate 4-5 days"))

        listSDProduct.add(ModelShopDetailProducts(R.drawable.shoes2, "Matrix",
            "30.00€", "Lorem ipsum dolor",
            "4.8", "Delivery estimate 4-5 days"))

        listSDProduct.add(ModelShopDetailProducts(R.drawable.shoe_bernd, "Bernd",
            "30.00€", "Lorem ipsum dolor",
            "4.8", "Delivery estimate 4-5 days"))

        listSDProduct.add(ModelShopDetailProducts(R.drawable.shoes2, "Matrix",
            "30.00€", "Lorem ipsum dolor",
            "4.8", "Delivery estimate 4-5 days"))

        binding.recyclerShopDetailProducts.adapter = AdapterShopDetailProducts(listSDProduct, this)
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