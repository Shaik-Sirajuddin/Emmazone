package com.live.emmazone.activities.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterProviderNewSales
import com.live.emmazone.databinding.FragmentNewSalesProviderBinding
import com.live.emmazone.model.ModelOnGoingOrders
import com.live.emmazone.model.ModelProviderNewSale
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.SalesResponse
import com.live.emmazone.view_models.AppViewModel

class NewSalesProviderFragment : Fragment(), Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()

    val list = ArrayList<ModelProviderNewSale>()
    lateinit var adapter: AdapterProviderNewSales

    private lateinit var binding: FragmentNewSalesProviderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewSalesProviderBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNewSalesAdapter()
        apiHitSales()
    }

    private fun apiHitSales() {
        val hashMap = HashMap<String, String>()
        hashMap["status"] = "1" //1=>New Orders, 2=> On going Orders, 3=> Past Orders

        appViewModel.salesListApi(requireActivity(), true, hashMap)
        appViewModel.getResponse().observe(this, this)

    }

    private fun setNewSalesAdapter() {

        val listChildRecycler = ArrayList<ModelOnGoingOrders>()

        listChildRecycler.add(
            ModelOnGoingOrders(
                R.drawable.shoes_square, "Brend Shoe",
                "03", "90.00€", status = "pending"
            )
        )
        listChildRecycler.add(
            ModelOnGoingOrders(
                R.drawable.shoes_square, "Brend Shoe",
                "03", "90.00€", status = "pending"
            )
        )

        list.add(
            ModelProviderNewSale(
                "Order ID:",
                "PLU9540572",
                R.drawable.avtarr_girl,
                "Allen Chandler",
                "Delivery Type",
                " Home Delivery",
                listChildRecycler,
                R.drawable.pendding_g,
                "29-march-2021",
                status = "pending"
            )
        )

        val items = ArrayList<ModelOnGoingOrders>()

        items.add(
            ModelOnGoingOrders(
                R.drawable.shoes_square,
                "Brend Shoe",
                "03",
                "90.00€",
                status = "pending"
            )
        )

        list.add(
            ModelProviderNewSale(
                "Order ID:", "PLU9540572", R.drawable.avtarr_girl, "Allen Chandler",
                "Delivery Type", " Home Delivery", items, R.drawable.pendding_g, "29-march-2021",
                status = "pending"
            )
        )

        binding.rvNewSales.adapter = AdapterProviderNewSales(requireContext(), list)

    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is SalesResponse) {

                }
            }
        }
    }

}