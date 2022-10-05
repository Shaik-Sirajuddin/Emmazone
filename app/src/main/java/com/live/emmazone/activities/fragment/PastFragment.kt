package com.live.emmazone.activities.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.R
import com.live.emmazone.activities.main.Cart
import com.live.emmazone.adapter.AdapterOnGoingUserOrders
import com.live.emmazone.databinding.PastFragmentBinding
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.ShopListingResponse
import com.live.emmazone.response_model.UserOrderListing
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.view_models.AppViewModel

class PastFragment : Fragment(), View.OnClickListener, Observer<RestObservable> {
    private val appViewModel: AppViewModel by viewModels()

    lateinit var adapter: AdapterOnGoingUserOrders
    var listPastOrders = ArrayList<UserOrderListing.Body.Response>()

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
        setAdapter()
    }

    private fun getPastOrdersApi() {
        val hashMap = HashMap<String, String>()
        hashMap["status"] = "2"   // 1 =>ongoing orders , 2 => past orders
        appViewModel.orderListingApi(requireActivity(), hashMap, true)
        appViewModel.mResponse.observe(requireActivity(), this)
    }

    private fun setAdapter() {
        adapter = AdapterOnGoingUserOrders(requireContext(), listPastOrders,false)
        binding.rvMyPastDelivered.adapter = adapter
    }

    private fun setOnClicks() {
//        binding.btnBuyAgain.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnBuyAgain -> {
                val intent = Intent(activity, Cart::class.java)
                startActivity(intent)
            }
        }

    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is UserOrderListing) {
                    val response:UserOrderListing = t.data

                    if (response.code ==AppConstants.SUCCESS_CODE){
                        if (t.data.body.notificationCount == 0) {
                            FragmentMyOrders.notifyRedBG.visibility = View.GONE
                        } else {
                            FragmentMyOrders.notifyRedBG.visibility = View.VISIBLE
                        }


                        if (t.data.body.cartCount == 0) {
                            FragmentMyOrders.ivRedCartDot.visibility = View.GONE
                        } else {
                            FragmentMyOrders.ivRedCartDot.visibility = View.VISIBLE
                        }


                        listPastOrders.clear()
                        listPastOrders.addAll(t.data.body.response)
                        if (listPastOrders.size > 0) {
                            binding.tvNoData.visibility = View.GONE
                            binding.rvMyPastDelivered.visibility = View.VISIBLE
                            adapter.notifyDataSetChanged()
                        } else {
                            binding.tvNoData.visibility = View.VISIBLE
                            binding.rvMyPastDelivered.visibility = View.GONE
                        }
                    }

                }
            }
            else -> {}
        }
    }

    override fun onResume() {
        super.onResume()
        getPastOrdersApi()
    }
}