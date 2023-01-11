package com.live.emmazone.activities.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.R
import com.live.emmazone.activities.main.Cart
import com.live.emmazone.activities.main.Notifications
import com.live.emmazone.adapter.ImageSliderCustomeAdapter
import com.live.emmazone.adapter.ShopStoryProviderAdapter
import com.live.emmazone.adapter.ShopStoryUserAdapter
import com.live.emmazone.databinding.FragmentShopStoriesBinding
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.model.ModelShopStory
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.AddFavouriteResponse
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.response_model.ShopStoryResponse
import com.live.emmazone.response_model.WishListResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.toBody
import okhttp3.RequestBody

class ShopStoriesFragment : Fragment(), Observer<RestObservable> {

    private lateinit var binding: FragmentShopStoriesBinding
    private lateinit var adapter: ShopStoryUserAdapter
    private val list = ArrayList<ShopStoryResponse.Shop>()
    private val appViewModel: AppViewModel by viewModels()
    private var position = -1
    private var likeStatus = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopStoriesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickHandle()
    }

    private fun clickHandle() {

        binding.imageNotifications.setOnClickListener {
            val intent = Intent(activity, Notifications::class.java)
            startActivity(intent)
        }

        binding.cart.setOnClickListener {
            val intent = Intent(activity, Cart::class.java)
            startActivity(intent)
        }
        adapter = ShopStoryUserAdapter(requireContext(), list) {
            position = it
            favUnFavApiHit(list[it])
        }

        binding.shopStoryRecyclerView.adapter = adapter

        loadStories()
    }

    private fun favUnFavApiHit(shop: ShopStoryResponse.Shop) {
        val hashMap = HashMap<String, String>()
        hashMap["vendorId"] = shop.userId

        if (shop.isLiked == 1) {
            hashMap["status"] = "0"
            likeStatus = 0
        } else {
            hashMap["status"] = "1"
            likeStatus = 1
        }

        appViewModel.addFavApi(requireActivity(), true, hashMap)
        appViewModel.getResponse().observe(requireActivity(), this)
    }

    private fun loadStories() {
        val map = HashMap<String, RequestBody>()
        val latitude = getPreference(AppConstants.LATITUDE, "")
        val longitude = getPreference(AppConstants.LONGITUDE, "")
        map["latitude"] = toBody(latitude)
        map["longitude"] = toBody(longitude)
        appViewModel.getShopStories(requireActivity(), true, map)
        appViewModel.mResponse.observe(requireActivity(), this)
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is ShopStoryResponse) {
                    if (t.data.code != AppConstants.SUCCESS_CODE) return
                    if (t.data.body.size > 0) {
                        list.clear()
                        list.addAll(t.data.body)
                        adapter.notifyDataSetChanged()
                        if (list.size == 0) {
                            binding.toHide.visibility = View.VISIBLE
                            binding.shopStoryRecyclerView.visibility = View.GONE
                        } else {
                            binding.toHide.visibility = View.GONE
                            binding.shopStoryRecyclerView.visibility = View.VISIBLE
                        }
                    } else {
                        binding.toHide.visibility = View.VISIBLE
                        binding.shopStoryRecyclerView.visibility = View.GONE
                    }
                } else if (t.data is AddFavouriteResponse) {
                    val response: AddFavouriteResponse = t.data
                    if (response.code == AppConstants.SUCCESS_CODE) {
                        if (position >= 0 && position < list.size) {
                            list[position].isLiked = likeStatus
                            adapter.notifyItemChanged(position)
                        }
                        AppUtils.showMsgOnlyWithoutClick(requireActivity(), t.data.message)
                    }
                }
            }
            Status.ERROR -> {
                AppUtils.showMsgOnlyWithoutClick(requireContext(), t.error.toString())
            }
            else -> {}
        }
    }

}