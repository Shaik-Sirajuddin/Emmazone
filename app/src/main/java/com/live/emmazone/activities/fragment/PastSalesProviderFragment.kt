package com.live.emmazone.activities.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.adapter.AdapterProviderNewSales
import com.live.emmazone.databinding.FragmentPastSaleProviderBinding
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.SalesResponse
import com.live.emmazone.view_models.AppViewModel

class PastSalesProviderFragment(
    private val isReturnsActivity: Boolean = false
): Fragment(), Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()
    val list = ArrayList<SalesResponse.Body.Response>()
    lateinit var adapter: AdapterProviderNewSales

    private lateinit var binding: FragmentPastSaleProviderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPastSaleProviderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPastSalesAdapter()
        apiHitSales()
    }

    private fun apiHitSales() {
        val hashMap = HashMap<String, String>()
        hashMap["status"] = "3" //1=>New Orders, 2=> On going Orders, 3=> Past Orders

        appViewModel.salesListApi(requireActivity(), true, hashMap)
        appViewModel.getResponse().observe(requireActivity(), this)

    }

    private fun setPastSalesAdapter() {
        adapter= AdapterProviderNewSales(requireContext(), list)
        binding.rvPastSalesPro.adapter = adapter


    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is SalesResponse) {
                    list.clear()
                    if (isReturnsActivity) {
                        list.addAll(
                            t.data.body.response
                                .filter {
                                    it.orderStatus == 8
                                }
                        )
                    } else {
                        list.addAll(
                            t.data.body.response
                                .filter {
                                    it.orderStatus != 8
                                }
                        )
                    }
                    if(list.size>0){
                        binding.tvNoData.visibility= View.GONE
                        binding.rvPastSalesPro.visibility= View.VISIBLE
                        adapter.notifyDataSetChanged()
                    }else{
                        binding.tvNoData.visibility= View.VISIBLE
                        binding.rvPastSalesPro.visibility= View.GONE
                    }
                    if(!isReturnsActivity){
                        if (t.data.body.notificationCount ==0){
                            FragmentProviderSale.imageRedDot.visibility = View.GONE
                        }else{
                            FragmentProviderSale.imageRedDot.visibility = View.VISIBLE
                        }
                    }

                }
            }
            else -> {}
        }
    } 
}