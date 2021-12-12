package com.live.emmazone.activities.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.activities.Interface.OnItemClick
import com.live.emmazone.adapter.AdapterProviderNewSales
import com.live.emmazone.model.ModelOnGoingOrders
import com.live.emmazone.model.ModelProviderNewSale

class NewSalesProviderFragment : Fragment(), OnItemClick{
    val list = ArrayList<ModelProviderNewSale>()
    val listChildRecycler = ArrayList<ModelOnGoingOrders>()
    lateinit var adapter : AdapterProviderNewSales

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = LayoutInflater.from(context).inflate(R.layout.fragment_new_sales_provider, container, false)

        val rv : RecyclerView = view.findViewById(R.id.rvNewSales)

        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        listChildRecycler.add(ModelOnGoingOrders(R.drawable.shoe, "Brend Shoe", "03", "90.00"))

        list.add(ModelProviderNewSale("Order ID:", "PLU9540572", R.drawable.avtarr_girl,
        "Allen Chandler", "Delivery Type", " Home Delivery", listChildRecycler, R.drawable.ontheway,
        "29-march-2021"))

        rv.adapter = AdapterProviderNewSales(list, this)

        return view
    }

    override fun onCellClickListener() {
        TODO("Not yet implemented")
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