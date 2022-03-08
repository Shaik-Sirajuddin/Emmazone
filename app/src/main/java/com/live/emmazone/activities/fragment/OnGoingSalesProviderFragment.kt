package com.live.emmazone.activities.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.adapter.AdapterProviderNewSales
import com.live.emmazone.databinding.FragmentOnGoingSaleProviderBinding
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.SalesResponse
import com.live.emmazone.view_models.AppViewModel

class OnGoingSalesProviderFragment : Fragment(), Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()
    val list = ArrayList<SalesResponse.SaleResponseBody>()
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
        adapter= AdapterProviderNewSales(requireContext(), list)
        binding.rvOnGoingSalesPro.adapter = adapter

    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is SalesResponse) {
                    list.clear()
                    list.addAll(t.data.body)
                    if(list.size>0){
                        binding.tvNoData.visibility= View.GONE
                        binding.rvOnGoingSalesPro.visibility= View.VISIBLE
                        adapter.notifyDataSetChanged()
                    }else{
                        binding.tvNoData.visibility= View.VISIBLE
                        binding.rvOnGoingSalesPro.visibility= View.GONE
                    }

                }
            }
        }
    }

}