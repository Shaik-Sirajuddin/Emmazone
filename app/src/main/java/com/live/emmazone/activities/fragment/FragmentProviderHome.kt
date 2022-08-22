package com.live.emmazone.activities.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.R
import com.live.emmazone.activities.main.Notifications
import com.live.emmazone.activities.provider.EditShopDetailActivity
import com.live.emmazone.activities.provider.MessageActivity
import com.live.emmazone.adapter.AdapterProShopProducts
import com.live.emmazone.adapter.AdapterShopDetailCategory
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.response_model.SellerShopDetailResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.ToastUtils
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.loadImage
import kotlinx.android.synthetic.main.fragment_provider_home.view.*

class FragmentProviderHome : Fragment(), Observer<RestObservable> {
    var productAdapter: AdapterProShopProducts? = null
    val list = ArrayList<SellerShopDetailResponse.Body.ShopDetails.ShopCategory>()
    val listProSDProducts = ArrayList<SellerShopDetailResponse.Body.ShopDetails.Product>()
    lateinit var adapter: AdapterShopDetailCategory
    private val appViewModel: AppViewModel by viewModels()
    private lateinit var response: SellerShopDetailResponse


    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                getSellerShopDetails()
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_provider_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireView().imgNotify.setOnClickListener {
            val intent = Intent(activity, Notifications::class.java)
            startActivity(intent)
        }
        requireView().messagesIcon.setOnClickListener {
            val intent = Intent(requireContext(), MessageActivity::class.java)
            startActivity(intent)
        }
        requireView().image_editShop.setOnClickListener {
            val intent = Intent(activity, EditShopDetailActivity::class.java)
            intent.putExtra(AppConstants.SHOP_DETAIL_RESPONSE, response)
            launcher.launch(intent)
        }

    }

    private fun getSellerShopDetails() {
        appViewModel.sellerShopDetailsApi(requireActivity(), true)
        appViewModel.getResponse().observe(requireActivity(), this)
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is SellerShopDetailResponse) {
                    response = t.data
                    if (response.code == AppConstants.SUCCESS_CODE) {
                        setDetailData(response)
                    }

                }

                if (t.data is CommonResponse) {
                    val response: CommonResponse = t.data
                    if (response.code == AppConstants.SUCCESS_CODE) {
                        ToastUtils.showLongToast(response.message)
                        productAdapter!!.deleteItem(pos)
                    }

                }
            }
            Status.ERROR -> {
                ToastUtils.showLongToast(t.error.toString())

            }
            else -> {}
        }
    }

    private fun setDetailData(response: SellerShopDetailResponse) {
        if (response.body.shopDetails.image != null) {
            requireView().imageShopDetail.loadImage(response.body.shopDetails.image)
        }

        if (response.body.notificationCount == 0) {
            requireView().notifyRedBG.visibility = View.GONE
        } else {
            requireView().notifyRedBG.visibility = View.VISIBLE
        }

        requireView().tvWishListStoreName.text = response.body.shopDetails.shopName
        requireView().tvDesc.text = response.body.shopDetails.shopDescription
        requireView().tvFYear.text = response.body.shopDetails.year.toString()
        requireView().tvShopAddress.text = response.body.shopDetails.shopAddress
        requireView().tvHeartsCount.text = response.body.shopDetails.likesCount.toString()
        list.clear()
        listProSDProducts.clear()
        listProSDProducts.addAll(response.body.shopDetails.products)
        list.addAll(response.body.shopDetails.shopCategories)
        productAdapter = AdapterProShopProducts(requireContext(), listProSDProducts, this)
        requireView().recyclerProviderSDProducts.adapter = productAdapter
        requireView().recyclerProviderShopDetailCategory.adapter = AdapterShopDetailCategory(list)

    }

    var pos = 0
    fun deleteProductAPIMethod(position: Int, productId: String) {
        pos = position
        appViewModel.deleteProductApi(requireActivity(), true, productId)
        appViewModel.mResponse.observe(requireActivity(), this)
    }

    override fun onResume() {
        super.onResume()
        getSellerShopDetails()
    }
}