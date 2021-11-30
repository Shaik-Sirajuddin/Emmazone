package com.live.emmazone.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterCart
import com.live.emmazone.adapter.AdapterShopDetailProducts
import com.live.emmazone.databinding.ActivityCartBinding
import com.live.emmazone.model.ModelCart
import com.live.emmazone.model.ModelShopDetailProducts

class Cart : AppCompatActivity() {
    lateinit var bining : ActivityCartBinding
    lateinit var adapter : AdapterCart
    val list = ArrayList<ModelCart>()
    val listMayLike = ArrayList<ModelShopDetailProducts>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bining = ActivityCartBinding.inflate(layoutInflater)
        setContentView(bining.root)

      bining.recyclerCart.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
      bining.recyclerCartMayLike.layoutManager = GridLayoutManager(this, 2)

        list.add(ModelCart(R.drawable.green, R.drawable.bin, "Brend Shoes", "30.00$"))
        list.add(ModelCart(R.drawable.green, R.drawable.bin, "Winter Sweeters", "30.00$"))

        bining.recyclerCart.adapter = AdapterCart(list)

        listMayLike.add(ModelShopDetailProducts(R.drawable.shoe_bernd, "Bernd", "30.00$", "Lorem ipsum dolor",
            "4.8", "Delivery estimate 4-5 days"))

        listMayLike.add(ModelShopDetailProducts(R.drawable.shoes2, "Matrix", "30.00$", "Lorem ipsum dolor",
            "4.8", "Delivery estimate 4-5 days"))

        bining.recyclerCartMayLike.adapter = AdapterShopDetailProducts(listMayLike)
    }
}