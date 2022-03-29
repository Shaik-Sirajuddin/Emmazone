package com.live.emmazone.activities.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.MainActivity
import com.live.emmazone.activities.main.Cart
import com.live.emmazone.activities.main.Notifications
import com.live.emmazone.activities.main.ShopDetailActivity
import com.live.emmazone.activities.main.ShopReviewsActivity
import com.live.emmazone.adapter.AdapterWishList
import com.live.emmazone.databinding.FragmentWishlistBinding
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.AddFavouriteResponse
import com.live.emmazone.response_model.WishListResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel

class WishListFragment : Fragment(), Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()
    private val wishList = ArrayList<WishListResponse.Body.Wish>()
    lateinit var wishListAdapter: AdapterWishList
    private var selectedPos: Int? = null

    private lateinit var binding: FragmentWishlistBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWishlistBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickHandle()


    }

    private fun wishListApiHit() {
        appViewModel.wishListApi(requireActivity(), true)
        appViewModel.getResponse().observe(requireActivity(), this)
    }

    private fun setWishListAdapter() {
        wishListAdapter = AdapterWishList(wishList)
        binding.recyclerWishList.adapter = wishListAdapter


        wishListAdapter.onClickListener = { wishListModel, clickOn ->

            if (getPreference(AppConstants.PROFILE_TYPE, "") == "guest") {
                (context as MainActivity).showLoginDialog()
            } else {
                if (clickOn == "favourite") {
                    selectedPos = wishList.indexOf(wishListModel)
                    favUnFavApiHit(wishListModel)
                } else if (clickOn == "itemClick") {
                    val intent = Intent(requireContext(), ShopDetailActivity::class.java)
                    intent.putExtra(AppConstants.SHOP_ID, wishListModel.id.toString())
                    startActivity(intent)
                } else if (clickOn == "rating") {
                    val intent = Intent(requireContext(), ShopReviewsActivity::class.java)
                    intent.putExtra(AppConstants.WISH_LIST_RESPONSE, wishListModel)
                    startActivity(intent)
                }
            }


        }

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

        binding.edtSearchWishList.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                searchShopsFilter(s.toString())
            }

        })

    }


    private fun favUnFavApiHit(wishListResponse: WishListResponse.Body.Wish) {
        val hashMap = HashMap<String, String>()
        hashMap["vendorId"] = wishListResponse.id.toString()

        if (wishListResponse.isLiked == 1)
            hashMap["status"] = "0"
        else {
            hashMap["status"] = "1"
        }

        appViewModel.addFavApi(requireActivity(), true, hashMap)
//        appViewModel.getResponse().observe(requireActivity(), this)
    }

    private fun searchShopsFilter(text: String) {
        val filterList = ArrayList<WishListResponse.Body.Wish>()

        wishList.forEach {
            if (it.shopName.contains(text, true)) {
                filterList.add(it)
            }
        }
        wishListAdapter.notifyData(filterList)
    }


    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is WishListResponse) {
                    val response: WishListResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {

                        if (response.body.notificationCount == 0) {
                            binding.notifyRedBG.visibility = View.GONE
                        } else {
                            binding.notifyRedBG.visibility = View.VISIBLE
                        }

                        if (response.body.cartCount == 0) {
                            binding.ivRedCart.visibility = View.GONE
                        } else {
                            binding.ivRedCart.visibility = View.VISIBLE
                        }

                        wishList.clear()
                        wishList.addAll(t.data.body.wishList)
                        setWishListAdapter()
                        noDataVisibility()
                    }

                } else if (t.data is AddFavouriteResponse) {
                    val response: AddFavouriteResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {

                        wishList[selectedPos!!].isLiked = response.body.status
                        wishListAdapter.notifyDataSetChanged()
                        AppUtils.showMsgOnlyWithoutClick(requireActivity(), response.message)
                        noDataVisibility()
                    }

                }

            }
        }


    }

    override fun onResume() {
        super.onResume()
        wishListApiHit()
    }


    private fun noDataVisibility() {
        if (wishList.isEmpty()) {
            binding.tvNoData.visibility = View.VISIBLE
            binding.llData.visibility = View.GONE
        } else {
            binding.tvNoData.visibility = View.GONE
            binding.llData.visibility = View.VISIBLE
        }
    }
}