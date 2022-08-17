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
import com.live.emmazone.R
import com.live.emmazone.activities.main.Notifications
import com.live.emmazone.activities.provider.ProviderMainActivity
import com.live.emmazone.adapter.AdapterProviderShopDetailProducts
import com.live.emmazone.databinding.FragmentAddProductProviderBinding
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.response_model.SellerShopDetailResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.ToastUtils
import com.live.emmazone.view_models.AppViewModel

class FragmentProviderAddProduct : Fragment(), Observer<RestObservable> {

    private lateinit var binding: FragmentAddProductProviderBinding
    private val list = ArrayList<SellerShopDetailResponse.Body.ShopDetails.Product>()
    private var isChecked = true
    private val appViewModel: AppViewModel by viewModels()
    var productAdapter: AdapterProviderShopDetailProducts? = null

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

        binding.edtSearchAddProduct.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                filterProduct(s.toString())
            }

        })

    }

    private fun filterProduct(text: String) {
        val filterList = ArrayList<SellerShopDetailResponse.Body.ShopDetails.Product>()

        list.forEach {
            if (it.name.contains(text, true)) {
                filterList.add(it)
            }
        }
        productAdapter?.notifyData(filterList)
    }

    override fun onResume() {
        super.onResume()
        getSellerShopDetails()

    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is SellerShopDetailResponse) {
                    val response: SellerShopDetailResponse = t.data
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

    private fun getSellerShopDetails() {
        appViewModel.sellerShopDetailsApi(requireActivity(), true)
        appViewModel.getResponse().observe(requireActivity(), this)
    }



    private fun setDetailData(response: SellerShopDetailResponse) {
        if (response.body.notificationCount == 0) {
            binding.notifyRedBG.visibility = View.GONE
        } else {
            binding.notifyRedBG.visibility = View.VISIBLE
        }

        val category = SellerShopDetailResponse.Body.ShopDetails.Product.Category("", "")
        val product_images: List<SellerShopDetailResponse.Body.ShopDetails.Product.ProductImage> =
            ArrayList()
        list.clear()
        /*list.add(0,
            SellerShopDetailResponse.Body.ShopDetails.Product(
                category, 0, 0, 0, 0, "", "", 0, "", "", 0,
                product_images, "", "", 0, "", 0, 0
            )
        )*/
        list.addAll(response.body.shopDetails.products)
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