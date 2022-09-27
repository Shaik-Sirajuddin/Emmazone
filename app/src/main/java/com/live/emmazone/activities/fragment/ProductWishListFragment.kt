package com.live.emmazone.activities.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.MainActivity
import com.live.emmazone.R
import com.live.emmazone.activities.main.ShopDetailActivity
import com.live.emmazone.adapter.AdapterWishList
import com.live.emmazone.adapter.SearchProductAdapter
import com.live.emmazone.databinding.FragmentProductWishListBinding
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.AddFavouriteResponse
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.response_model.SearchProductResponse
import com.live.emmazone.response_model.WishListResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.toBody
import kotlinx.android.synthetic.main.activity_add_new_address.*
import kotlinx.android.synthetic.main.fragment_add_product_provider.*
import okhttp3.RequestBody

class ProductWishListFragment : Fragment() , Observer<RestObservable> {

    private lateinit var binding : FragmentProductWishListBinding
    private val appViewModel: AppViewModel by viewModels()
    private val list = ArrayList<SearchProductResponse.Body>()
    private val cachedList = ArrayList<SearchProductResponse.Body>()
    private var selectedPos: Int? = null
    private var listLoaded = false
    private lateinit var adapter : SearchProductAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductWishListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clicksHandle()
        wishListApiHit()
    }

    private fun wishListApiHit() {
        appViewModel.getProductWishList(requireActivity(), true)
        appViewModel.getResponse().observe(requireActivity(), this)
    }
    private fun setWishListAdapter() {
        adapter = SearchProductAdapter(list) {
            selectedPos = it
            favUnFavApiHit(list[it])
        }
        binding.recyclerWishList.adapter = adapter
    }

    private fun clicksHandle() {

        binding.edtSearchWishList.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                searchShopsFilter(s.toString())
            }
        })
        setWishListAdapter()

    }

    private fun favUnFavApiHit(data: SearchProductResponse.Body) {
        val hashMap = HashMap<String, String>()
        hashMap["productId"] = data.id.toString()

        if (data.isLiked == 1)
            hashMap["status"] = "0"
        else {
            hashMap["status"] = "1"
        }
        appViewModel.likeOrDislikeProduct(requireActivity(), true, hashMap)
        appViewModel.getResponse().observe(requireActivity(), this)
    }

    private fun searchShopsFilter(text: String) {
        val filterList = ArrayList<SearchProductResponse.Body>()
        cachedList.forEach {
            if (it.name.contains(text, true)) {
                filterList.add(it)
            }
        }
        list.clear()
        list.addAll(filterList)
        adapter.notifyDataSetChanged()
    }


    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is SearchProductResponse) {
                    val response: ArrayList<SearchProductResponse.Body> = t.data.body

                    if (t.data.code == AppConstants.SUCCESS_CODE) {

//                        if (response.body.notificationCount == 0) {
//                            binding.notifyRedBG.visibility = View.GONE
//                        } else {
//                            binding.notifyRedBG.visibility = View.VISIBLE
//                        }
//
//                        if (response.body.cartCount == 0) {
//                            binding.ivRedCart.visibility = View.GONE
//                        } else {
//                            binding.ivRedCart.visibility = View.VISIBLE
//                        }

                        cachedList.clear()
                        cachedList.addAll(response)
                        list.clear()
                        list.addAll(response)
                        adapter.notifyDataSetChanged()
                        listLoaded = true
                        noDataVisibility()
                    }

                } else if (t.data is CommonResponse) {
                    val response: CommonResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {
                        val item = list[selectedPos!!]
                        cachedList.remove(item)
                        list.removeAt(selectedPos!!)
                        adapter.notifyItemRemoved(selectedPos!!)
                        AppUtils.showMsgOnlyWithoutClick(requireActivity(), response.message)
                        noDataVisibility()
                    }

                }

            }
            else -> {}
        }

    }

    private fun noDataVisibility() {
        if (list.isEmpty()) {
            binding.tvNoData.visibility = View.VISIBLE
           // binding.llData.visibility = View.GONE
        } else {
            binding.tvNoData.visibility = View.GONE
            //binding.llData.visibility = View.VISIBLE
        }
    }

}