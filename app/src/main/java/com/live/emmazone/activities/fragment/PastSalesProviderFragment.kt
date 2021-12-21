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
import com.live.emmazone.model.ModelOnGoingOrders
import com.live.emmazone.model.ModelProviderNewSale

class PastSalesProviderFragment : Fragment() {

    val list = ArrayList<ModelProviderNewSale>()
    lateinit var adapter : AdapterProviderNewSales

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = LayoutInflater.from(context).inflate(R.layout.fragment_past_sale_provider, container, false)

        val rv : RecyclerView = view.findViewById(R.id.rvPastSalesPro)

        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val listChildRecycler = ArrayList<ModelOnGoingOrders>()

        listChildRecycler.add(ModelOnGoingOrders(R.drawable.shoes_square, "Brend Shoe",
            "03", "90.00€",  status = "past"))
        listChildRecycler.add(ModelOnGoingOrders(R.drawable.shoes_square, "Brend Shoe",
            "03", "90.00€"  ,status = "past"))

        list.add(
            ModelProviderNewSale("Order ID:", "PLU9540572", R.drawable.avtarr_girl,
                "Allen Chandler",
                "Delivery Type", " Home Delivery", listChildRecycler,
                R.drawable.delivered, "29-march-2021"
                ,status = "past")
        )

        val itemNew = ArrayList<ModelOnGoingOrders>()

        itemNew.add(
            ModelOnGoingOrders(R.drawable.shoes_square, "Brend Shoe", "03",
                "90.00€",status = "past" )
        )

        list.add(
            ModelProviderNewSale("Order ID:", "PLU9540572", R.drawable.avtarr_girl,
                "Allen Chandler",
                "Delivery Type", " Home Delivery", itemNew, R.drawable.puck,
                "29-march-2021",
                status = "past")
        )

        val item1 = ArrayList<ModelOnGoingOrders>()

        itemNew.add(
            ModelOnGoingOrders(R.drawable.shoes_square, "Brend Shoe",
                "03", "90.00€" ,status = "past")
        )

        list.add(
            ModelProviderNewSale("Order ID:", "PLU9540572", R.drawable.avtarr_girl,
                "Allen Chandler",
                "Delivery Type", " Home Delivery", itemNew, R.drawable.canceled,
                "29-march-2021",
                status = "past")
        )


        rv.adapter = AdapterProviderNewSales(requireContext(), list)

        return view
    }

}