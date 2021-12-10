package com.live.emmazone.activities.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterProviderNewSales
import com.live.emmazone.model.ModelProviderNewSale

class NewSalesProviderFragment : Fragment(){
    val list = ArrayList<ModelProviderNewSale>()
    lateinit var adapter : AdapterProviderNewSales

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = LayoutInflater.from(context).inflate(R.layout.fragment_new_sales_provider, container, false)

        val rv : RecyclerView = view.findViewById(R.id.rvNewSales)

        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        list.add(ModelProviderNewSale("Order ID:", "PLU9540572", R.drawable.avtarr_girl,
        "Allen Chandler", "Delivery Type", " Home Delivery",
        R.drawable.ontheway, "29-March-2021"))

        rv.adapter = AdapterProviderNewSales(list)

        return view
    }
}