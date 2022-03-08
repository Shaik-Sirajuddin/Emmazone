package com.live.emmazone.activities.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.activities.listeners.OnActionListenerNew
import com.live.emmazone.activities.main.Cart
import com.live.emmazone.adapter.AdapterOnGoingUserOrders
import com.live.emmazone.adapter.AdapterOrderCancel
import com.live.emmazone.databinding.PastFragmentBinding
import com.live.emmazone.net.RestObservable
import com.live.emmazone.response_model.SalesResponse
import com.live.emmazone.view_models.AppViewModel

class PastFragment : Fragment(), View.OnClickListener, Observer<RestObservable> {
    private val appViewModel: AppViewModel by viewModels()

    lateinit var adapter: AdapterOnGoingUserOrders
    lateinit var adapterOrderCancel: AdapterOrderCancel
    var listPastOrders = ArrayList<SalesResponse.SaleResponseBody>()

    private lateinit var binding: PastFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = LayoutInflater.from(context).inflate(R.layout.past_fragment, container, false)

        binding = PastFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClicks()

        val onActionListenerCancel = object : OnActionListenerNew {
            override fun notifyOnClick() {
              //  imageStatusCancel.performClick()
            }
        }
        setAdapter(onActionListenerCancel)

        getPastOrdersApi()

    }

    private fun getPastOrdersApi() {
        val hashMap= HashMap<String,String>()
        hashMap["type"]="2"
        appViewModel.orderListingApi(requireActivity(),hashMap,true)
        appViewModel.mResponse.observe(this,this)
    }

    private fun setAdapter(onActionListenerCancel: OnActionListenerNew) {
        adapter= AdapterOnGoingUserOrders(requireContext(), listPastOrders,onActionListenerCancel)
        binding.rvMyPastDelivered.adapter = adapter
    }

    private fun setOnClicks() {
        binding.btnBuyAgain.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnBuyAgain ->{
                val intent = Intent(activity, Cart::class.java)
                startActivity(intent)
            }
        }

    }

    override fun onChanged(t: RestObservable?) {

    }
}