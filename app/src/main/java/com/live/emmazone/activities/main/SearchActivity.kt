package com.live.emmazone.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterShopDetailProducts
import com.live.emmazone.databinding.ActivitySearchBinding
import com.live.emmazone.model.ModelShopDetailProducts

class SearchActivity : AppCompatActivity() {
    lateinit var binidng : ActivitySearchBinding
    val list = ArrayList<ModelShopDetailProducts>()
    lateinit var adapter : AdapterShopDetailProducts

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binidng = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binidng.root)

        binidng.recyclerSearch.layoutManager = GridLayoutManager(this, 2)

        list.add(ModelShopDetailProducts(R.drawable.bike, "Bernd", "30.00$", "Lorem ipsum dolor",
            "4.8", "Delivery estimate 4-5 days"))

        list.add(ModelShopDetailProducts(R.drawable.bike1, "Matrix", "30.00$", "Lorem ipsum dolor",
            "4.8", "Delivery estimate 4-5 days"))

        list.add(ModelShopDetailProducts(R.drawable.bike2, "Bernd", "30.00$", "Lorem ipsum dolor",
            "4.8", "Delivery estimate 4-5 days"))

        list.add(ModelShopDetailProducts(R.drawable.bike3, "Matrix", "30.00$", "Lorem ipsum dolor",
            "4.8", "Delivery estimate 4-5 days"))

    binidng.recyclerSearch.adapter = AdapterShopDetailProducts(list)

    }
}