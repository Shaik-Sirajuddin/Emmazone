package com.live.emmazone.activities.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterProviderNewSales
import com.live.emmazone.databinding.FragmentOnGoingSaleProviderBinding
import com.live.emmazone.model.ModelOnGoingOrders
import com.live.emmazone.model.ModelProviderNewSale
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.SalesResponse
import com.live.emmazone.view_models.AppViewModel

class OnGoingSalesProviderFragment : Fragment(), Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()
    val list = ArrayList<ModelProviderNewSale>()
    lateinit var adapter: AdapterProviderNewSales

    private lateinit var binding: FragmentOnGoingSaleProviderBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnGoingSaleProviderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setonGoingAdapter()
        apiHitSales()
    }

    private fun apiHitSales() {
        val hashMap = HashMap<String, String>()
        hashMap["status"] = "2" //1=>New Orders, 2=> On going Orders, 3=> Past Orders

        appViewModel.salesListApi(requireActivity(), true, hashMap)
        appViewModel.getResponse().observe(this, this)

    }

    private fun setonGoingAdapter() {
        val listChildRecycler = ArrayList<ModelOnGoingOrders>()

        listChildRecycler.add(
            ModelOnGoingOrders(
                R.drawable.shoes_square, "Brend Shoe",
                "03", "90.00€", status = "ongoing"
            )
        )
        listChildRecycler.add(
            ModelOnGoingOrders(
                R.drawable.shoes_square, "Brend Shoe",
                "03", "90.00€", status = "ongoing"
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
                R.drawable.ontheway,
                "29-march-2021",
                status = "ongoing"
            )
        )

        val item = ArrayList<ModelOnGoingOrders>()

        item.add(
            ModelOnGoingOrders(
                R.drawable.shoes_square, "Brend Shoe",
                "03", "90.00€", status = "ongoing"
            )
        )

        list.add(
            ModelProviderNewSale(
                "Order ID:", "PLU9540572", R.drawable.avtarr_girl,
                "Allen Chandler",
                "Delivery Type", " Home Delivery", item, R.drawable.accept,
                "29-march-2021",
                status = "ongoing"
            )
        )

        binding.rvOnGoingSalesPro.adapter = AdapterProviderNewSales(requireContext(), list)

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