package com.live.emmazone.activities.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.live.emmazone.R
import com.live.emmazone.adapter.SearchProductAdapter
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.SearchProductResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.view_models.AppViewModel
import kotlinx.android.synthetic.main.activity_search_product.*

class SearchProductActivity : AppCompatActivity(), Observer<RestObservable> {

    private var searchAdapter: SearchProductAdapter? = null
    private val arrayList = ArrayList<SearchProductResponse.Body>()

    private val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_product)

        setSearchAdapter()
        clicksHandle()
    }

    private fun clicksHandle() {
        rlBack.setOnClickListener { onBackPressed() }
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    searchApiHit(s.toString())
                } else {
                    arrayList.clear()
                    searchAdapter?.notifyDataSetChanged()
                    if (arrayList.isEmpty()) {
                        tvNoData.visibility = View.VISIBLE
                    } else {
                        tvNoData.visibility = View.GONE
                    }
                }

            }

        })
    }

    private fun searchApiHit(s: String) {
        val hashMap = HashMap<String, String>()
        hashMap["keyword"] = s

        appViewModel.searchProductApi(this, hashMap, true)
        appViewModel.getResponse().observe(this, this)
    }

    private fun setSearchAdapter() {
        searchAdapter = SearchProductAdapter(arrayList)
        rvSearchProduct.adapter = searchAdapter

        searchAdapter?.onItemClick = { pos ->
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra(AppConstants.USER2_NAME, arrayList[pos].vendorDetail.shopName)
            intent.putExtra(AppConstants.USER2_IMAGE, arrayList[pos].vendorDetail.image)
            intent.putExtra("productId", arrayList[pos].id.toString())
            startActivity(intent)
        }
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is SearchProductResponse) {
                    val response: SearchProductResponse = t.data
                    if (response.code == AppConstants.SUCCESS_CODE) {
                        arrayList.clear()
                        arrayList.addAll(response.body)
                        searchAdapter?.notifyDataSetChanged()


                        if (arrayList.isEmpty()) {
                            tvNoData.visibility = View.VISIBLE
                        } else {
                            tvNoData.visibility = View.GONE
                        }

                    }

                }
            }
        }
    }
}