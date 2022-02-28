package com.live.emmazone.activities.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.live.emmazone.R
import com.live.emmazone.activities.main.Notifications
import com.live.emmazone.activities.provider.ProviderMainActivity
import com.live.emmazone.adapter.AdapterProviderShopDetailProducts
import com.live.emmazone.databinding.FragmentAddProductProviderBinding
import com.live.emmazone.model.sellerShopDetails.SellerShopDetailsResponse
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.response_model.ShopDetailResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.ToastUtils
import com.live.emmazone.view_models.AppViewModel

class FragmentProviderAddProduct : Fragment(), Observer<RestObservable> {

    private lateinit var binding: FragmentAddProductProviderBinding
    private val list = ArrayList<ShopDetailResponse.Body.Product>()
    private var isChecked = true
    private val appViewModel: AppViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddProductProviderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.checkBox.setOnClickListener {
            isChecked = !isChecked
            binding.checkBox.setImageResource(
                if (isChecked)
                    R.drawable.checkbox_g
                else
                    R.drawable.checkbox_tick
            )
        }

        binding.checkBoxLife.setOnClickListener {

            isChecked = !isChecked
            binding.checkBoxLife.setImageResource(
                if (isChecked)
                    R.drawable.checkbox_g
                else
                    R.drawable.checkbox_tick
            )
        }

        binding.checkOwnDelvry.setOnClickListener {
            isChecked = !isChecked
            binding.checkOwnDelvry.setImageResource(
                if (isChecked)
                    R.drawable.checkbox_g
                else
                    R.drawable.checkbox_tick
            )
        }


        binding.back.setOnClickListener {
            val intent = Intent(activity, ProviderMainActivity::class.java)
            startActivity(intent)
        }

        binding.imgNotify.setOnClickListener {
            val intent = Intent(activity, Notifications::class.java)
            startActivity(intent)
        }
        binding.rvAdProductProvider.layoutManager = GridLayoutManager(context, 2)


    }

    override fun onResume() {
        super.onResume()
        getSellerShopDetails()

    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is SellerShopDetailsResponse) {
                    val response: SellerShopDetailsResponse = t.data
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
        }
    }

    fun getSellerShopDetails(){
        appViewModel.sellerShopDetailsApi(requireActivity(), true)
        appViewModel.getResponse().observe(requireActivity(), this)
    }
    var productAdapter: AdapterProviderShopDetailProducts?=null

     fun setDetailData(response: SellerShopDetailsResponse) {
         val category = ShopDetailResponse.Body.Product.Category("", "")
         val product_images: List<ShopDetailResponse.Body.Product.ProductImage> = ArrayList()
         list.clear()
         list.add(ShopDetailResponse.Body.Product(category,0,0,0,0,"","",0,"","","",0,
            product_images,""
             ,0,0,0))
         list.addAll(response.body.products)
         productAdapter = AdapterProviderShopDetailProducts(requireContext(), list, this)
         binding.rvAdProductProvider.adapter = productAdapter

    }

    var pos = 0
    fun deleteProductAPIMethod(position: Int, productId: String) {
        pos = position
        appViewModel.deleteProductApi(requireActivity(), true, productId)
        appViewModel.mResponse.observe(requireActivity(), this)
    }
}