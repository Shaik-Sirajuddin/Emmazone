package com.live.emmazone.activities.main

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.slider.Slider
import com.live.emmazone.R
import com.live.emmazone.adapter.CategoriesAdapter
import com.live.emmazone.adapter.ColorAdapter
import com.live.emmazone.adapter.SearchProductAdapter
import com.live.emmazone.adapter.SizeAdapter
import com.live.emmazone.databinding.BottomSheetFilterProductDialogBinding
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.extensionfuncton.savePreference
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CategoryColorSizeResponse
import com.live.emmazone.response_model.CategoryListResponse
import com.live.emmazone.response_model.SearchProductResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel
import kotlinx.android.synthetic.main.activity_search_product.*
import kotlinx.android.synthetic.main.bottom_sheet_filter_product_dialog.*
import kotlinx.android.synthetic.main.dialog_category.*

class SearchProductActivity : AppCompatActivity(), Observer<RestObservable> {

    private var searchAdapter: SearchProductAdapter? = null
    private val arrayList = ArrayList<SearchProductResponse.Body>()
    private var bottomDialog: BottomSheetDialog? = null
    lateinit var binding: BottomSheetFilterProductDialogBinding
    var categoryID = ""
    var colorID = ""
    var sizeID = ""
    var catid = ""
    var colId = ""
    var sizId = ""
    var categoryName = ""
    var priceRangeValue: Boolean? = null
    var priceSort = ""
    var dropDownType = 0
    private val colorList = ArrayList<CategoryColorSizeResponse.Body.CategoryColor>()
    private val sizeList = ArrayList<CategoryColorSizeResponse.Body.CategorySize>()
    private lateinit var colorAdapter: ColorAdapter
    private lateinit var sizeAdapter: SizeAdapter
    private val list: java.util.ArrayList<CategoryListResponse.Body> = java.util.ArrayList()
    private val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_product)
        setSearchAdapter()
        clicksHandle()
    }

    private fun clicksHandle() {
        rlBack.setOnClickListener { onBackPressed() }
        ivFilter.setOnClickListener {
            showBottomDialog()
        }
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
                        ivFilter.visibility = View.GONE
                    } else {
                        tvNoData.visibility = View.GONE
                        ivFilter.visibility = View.VISIBLE
                    }
                }

            }

        })
    }

    private fun searchApiHit(s: String) {
        val hashMap = HashMap<String, String>()
        hashMap["keyword"] = s
        appViewModel.searchProductApi(this, hashMap, false)
        appViewModel.getResponse().observe(this, this)
    }

    private fun filterApiHit(s: String) {
        catid = if (categoryID == "0") {
            ""
        } else {
            categoryID
        }
        colId = if (colorID == "0") {
            ""
        } else {
            colorID
        }
        sizId = if (sizeID == "0") {
            ""
        } else {
            sizeID
        }
        val hashMap = HashMap<String, String>()
        hashMap["keyword"] = s
        hashMap["categoryId"] = catid
        hashMap["categoryColorId"] = colId
        hashMap["categorySizeId"] = sizId
        hashMap["max_price"] = bottomDialog!!.seekBar.value.toInt().toString().trim()
        hashMap["price_sort"] = priceSort


        appViewModel.filterProductApi(this, hashMap, false)
        appViewModel.getResponse().observe(this, this)
    }

    private fun setSearchAdapter() {
        searchAdapter = SearchProductAdapter(arrayList)
        rvSearchProduct.adapter = searchAdapter
        searchAdapter?.onItemClick = { pos ->
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra(AppConstants.USER2_NAME, arrayList[pos].vendorDetail.shopName)
            intent.putExtra(AppConstants.USER2_IMAGE, arrayList[pos].vendorDetail.image)
            intent.putExtra(AppConstants.SHOP_NAME_VISIBLE, true)
            intent.putExtra("productId", arrayList[pos].id.toString())
            startActivity(intent)
        }
    }

    private fun showBottomDialog() {
        AppUtils.hideSoftKeyboard(this)
        bottomDialog = BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_filter_product_dialog, null)
        bottomDialog?.setCancelable(true)
        bottomDialog?.setContentView(view)
        (bottomDialog as BottomSheetDialog).behavior.peekHeight = 1800

        var tvSelectCategory = view.findViewById<AppCompatTextView>(R.id.tvSelectCategory)
        val tvSelectColor = view.findViewById<AppCompatTextView>(R.id.tvSelectColor)
        val tvSelectSize = view.findViewById<AppCompatTextView>(R.id.tvSelectSize)
        val seekBar = view.findViewById<Slider>(R.id.seekBar)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
        val radioButton = view.findViewById<RadioButton>(R.id.radioButton)
        val radioButton2 = view.findViewById<RadioButton>(R.id.radioButton2)
        val radioButton3 = view.findViewById<RadioButton>(R.id.radioButton3)
        val btnDone = view.findViewById<Button>(R.id.btnDone)


        if (getPreference(AppConstants.IS_FILTER, false)) {
            tvSelectCategory.text = getPreference(AppConstants.CATEGORY_NAME, "").toString()
            categoryID = getPreference(AppConstants.CATEGORY_ID, "").toString()
            colorID = getPreference(AppConstants.COLOUR_ID, "").toString()
            tvSelectColor.text = getPreference(AppConstants.COLOUR_NAME, "").toString()
            tvSelectSize.text = getPreference(AppConstants.SIZE_NAME, "").toString()
            sizeID = getPreference(AppConstants.SIZE_ID, "").toString()

            when {
                getPreference(AppConstants.PRICE_RANGE, "") == "1" -> {
                    radioButton.isChecked = true
                    priceSort = "1"
                }
                getPreference(AppConstants.PRICE_RANGE, "") == "2" -> {
                    radioButton2.isChecked = true
                    priceSort = "2"
                }
                else -> {
                    radioButton3.isChecked = true
                    priceSort = ""
                }
            }

            if (getPreference(AppConstants.PRICE, "") != "0.0f") {
                seekBar.value = getPreference(AppConstants.PRICE, "").toFloat()
            } else {
                seekBar.value = 0.0f
            }


        }

        tvSelectCategory.setOnClickListener {
            dropDownType = AppConstants.CATEGORY_DROP_DOWN
            appViewModel.categoryListApi(this, true)
            appViewModel.getResponse().observe(this, this)
        }

        tvSelectColor.setOnClickListener {
            if (categoryID.isNotEmpty() && categoryID != "0") {
                dropDownType = AppConstants.COLOUR_DROP_DOWN
                getColorSizeApiHit()

            } else {
                Toast.makeText(applicationContext, "Please select category", Toast.LENGTH_SHORT)
                    .show();
            }
        }
        tvSelectSize.setOnClickListener {
            if (categoryID.isNotEmpty() && categoryID != "0") {
                dropDownType = AppConstants.SIZE_DROP_DOWN
                getColorSizeApiHit()
            } else {
                Toast.makeText(applicationContext, "Please select category", Toast.LENGTH_SHORT)
                    .show();
            }

        }

        btnDone.setOnClickListener {
            savePreference(AppConstants.IS_FILTER, true)
            if (tvSelectCategory.text.toString().trim().isNotEmpty()) {
                savePreference(AppConstants.CATEGORY_NAME, tvSelectCategory.text.toString().trim())
                savePreference(AppConstants.CATEGORY_ID, categoryID)
            }

            if (tvSelectColor.text.toString().trim().isNotEmpty()) {
                savePreference(AppConstants.COLOUR_NAME, tvSelectColor.text.toString().trim())
                savePreference(AppConstants.COLOUR_ID, colorID)
            }

            if (tvSelectSize.text.toString().trim().isNotEmpty()) {
                savePreference(AppConstants.SIZE_NAME, tvSelectSize.text.toString().trim())
                savePreference(AppConstants.SIZE_ID, sizeID)
            }
            if (seekBar.value != 0.0f) {
                savePreference(AppConstants.PRICE, seekBar.value.toString())


            } else {
                savePreference(AppConstants.PRICE, 0.0f.toString())
            }
            Log.d("seekBar.value", seekBar.value.toString())
            //   if (radioButton.isChecked || radioButton2.isChecked) {
            if (radioButton.isChecked) {
                savePreference(AppConstants.PRICE_RANGE, "1")
                priceSort = "1"
            } else if (radioButton2.isChecked) {
                savePreference(AppConstants.PRICE_RANGE, "2")
                priceSort = "2"

            } else if (radioButton3.isChecked) {
                savePreference(AppConstants.PRICE_RANGE, " ")
                priceSort = ""

            }
            //  }

            filterApiHit(edtSearch.text.toString().trim())
            bottomDialog!!.dismiss()
        }

        bottomDialog?.show()
    }

    private fun getColorSizeApiHit() {
        val hashMap = HashMap<String, String>()
        hashMap["categoryId"] = categoryID

        appViewModel.categoryColorSizeApi(this, true, hashMap)
        appViewModel.getResponse().observe(this, this)
    }

    lateinit var dialog: Dialog
    private fun showCategoryDialog() {
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_category)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val recycle: RecyclerView = dialog.findViewById(R.id.rvAvatars)!!
        val titleTV: TextView = dialog.findViewById(R.id.titleTV)!!
        if (dropDownType == AppConstants.CATEGORY_DROP_DOWN) {
            titleTV.text = "Choose Category"
            setCategoryAdapter(recycle)
        } else if (dropDownType == AppConstants.COLOUR_DROP_DOWN) {
            titleTV.text = "Choose Colour"
            setColorAdapter(recycle)
        } else if (dropDownType == AppConstants.SIZE_DROP_DOWN) {
            titleTV.text = "Choose Size"
            setSizeAdapter()
        }

        dialog.show()
    }

    private fun setCategoryAdapter(recyclerView: RecyclerView) {
        val categoryAdapter = CategoriesAdapter(list)
        recyclerView.adapter = categoryAdapter

        categoryAdapter.onClickListener = { pos ->
            list.forEachIndexed { index, body ->
                body.isSelected = pos == index
            }
            dialog.dismiss()
            categoryID = list[pos].id.toString()
            categoryName = list[pos].name
            bottomDialog!!.tvSelectCategory.text = categoryName
            bottomDialog!!.tvSelectColor.text = "Select Color"
            colorID = ""
            bottomDialog!!.tvSelectSize.text = "Select Size"
            sizeID = ""


        }

    }

    private fun setColorAdapter(recyclerView: RecyclerView) {
        colorAdapter = ColorAdapter(colorList)
        recyclerView.adapter = colorAdapter

        colorAdapter.onClickListener = { pos ->
            colorList.forEachIndexed { index, body ->
                body.isSelected = pos == index
            }
            dialog.dismiss()
            colorID = colorList[pos].id.toString()
            bottomDialog!!.tvSelectColor.text = colorList[pos].color
        }


    }

    private fun setSizeAdapter() {
        sizeAdapter = SizeAdapter(sizeList)
        dialog.rvAvatars.adapter = sizeAdapter


        sizeAdapter.onClickListener = { pos ->
            sizeList.forEachIndexed { index, body ->
                body.isSelected = pos == index
            }
            dialog.dismiss()
            sizeID = sizeList[pos].id.toString()
            bottomDialog!!.tvSelectSize.text = sizeList[pos].size
        }

    }

    @SuppressLint("NotifyDataSetChanged")
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
                            ivFilter.visibility = View.GONE
                        } else {
                            ivFilter.visibility = View.VISIBLE
                            tvNoData.visibility = View.GONE
                        }

                    }

                } else if (t.data is CategoryListResponse) {
                    val response: CategoryListResponse = t.data
                    if (response.code == AppConstants.SUCCESS_CODE) {
                        list.clear()
                        list.addAll(response.body)
                        list.add(0, CategoryListResponse.Body(0, "", "Select Category", false))
                        showCategoryDialog()
                    }
                } else if (t.data is CategoryColorSizeResponse) {
                    val response: CategoryColorSizeResponse = t.data
                    if (response.code == AppConstants.SUCCESS_CODE) {

                        colorList.clear()
                        sizeList.clear()

                        colorList.addAll(response.body.categoryColors)
                        colorList.add(
                            0,
                            CategoryColorSizeResponse.Body.CategoryColor(0, "Select Colour", 0)
                        )
                        sizeList.addAll(response.body.categorySizes)
                        sizeList.add(
                            0,
                            CategoryColorSizeResponse.Body.CategorySize(0, 0, "Select Size")
                        )
                        showCategoryDialog()

                    }
                }
            }
            else -> {}
        }
    }
}