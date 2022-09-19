package com.live.emmazone.activities.provider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.live.emmazone.model.ProductVariant
import com.live.emmazone.net.RestObservable
import com.live.emmazone.response_model.CategoryColorSizeResponse
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityAddNewProductVariantBinding
import com.live.emmazone.interfaces.OnPopupClick
import com.live.emmazone.response_model.AddProductVariantResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AddNewProductVariant : AppCompatActivity(), Observer<RestObservable> {

    lateinit var binding : ActivityAddNewProductVariantBinding
    private var productVariant : ProductVariant? =  null
    private var categoryId : Int = 0
    private var productId : Int = 0
    private val colorList = ArrayList<CategoryColorSizeResponse.Body.CategoryColor>()
    private val sizeList = ArrayList<CategoryColorSizeResponse.Body.CategorySize>()
    private val colorNameList = ArrayList<String>()
    private val sizeNameList = ArrayList<String>()
    private lateinit var colorAdapter: ArrayAdapter<String>
    private lateinit var sizeAdapter: ArrayAdapter<String>
    private val appViewModel: AppViewModel by viewModels()
    private var isNew = true
    private var color = -1
    private var size = -1
    companion object{
        const val PRODUCT_ID = "productID"
        const val PRODUCT_VARIANT = "productVariant"
        const val CATEGORY_ID = "categoryId"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewProductVariantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        productVariant = intent.extras?.get(PRODUCT_VARIANT) as ProductVariant?
        categoryId = intent.extras?.get(CATEGORY_ID) as Int
        productId = intent.extras?.get(PRODUCT_ID) as Int
        isNew = productVariant==null
        initViews()
        setData()
        getColorSizeApiHit()
        binding.saveButton.setOnClickListener{
            validateAndPush()
        }

    }
    private fun setData(){
        if(isNew){
            binding.title.text = "Add new variant"
        }
        else{
            binding.title.text = "Edit variant"
            productVariant?.let{
                binding.productPrice.setText(it.price)
              //  binding.quantityVariant.setText(it.quantity)
            }
        }
    }
    private fun initViews(){
        colorAdapter = ArrayAdapter(this, R.layout.dropdown_item,colorNameList)
        sizeAdapter = ArrayAdapter(this, R.layout.dropdown_item,sizeNameList)
        binding.pickColor.setAdapter(colorAdapter)
        binding.pickSize.setAdapter(sizeAdapter)
        binding.pickSize.setDropDownBackgroundResource(R.color.white)
        binding.pickColor.setDropDownBackgroundResource(R.color.white)
        binding.pickColor.setOnItemClickListener { adapterView, view, i, l ->
           color = i
           Log.d("color",color.toString())
        }
        binding.pickSize.setOnItemClickListener { adapterView, view, i, l ->
            size = i
            Log.d("sizer",size.toString())
        }
    }
    private fun getColorSizeApiHit() {
        binding.progressBar.visibility = View.VISIBLE
        val hashMap = HashMap<String, String>()
        hashMap["categoryId"] = categoryId.toString()
        appViewModel.categoryColorSizeApi(this, true, hashMap)
        appViewModel.getResponse().observe(this, this)
    }
    private fun validateAndPush(){
        val price = binding.productPrice.text.toString().trim().toIntOrNull()
        val quantity = binding.quantityVariant.text.toString().trim().toIntOrNull()
        var isValid = true
        isValid = price !=null && isValid
        isValid = quantity !=null  && isValid
        isValid = size!=-1  && isValid
        isValid = color !=-1  && isValid

        if(!isValid){
            AppUtils.showMsgOnlyWithoutClick(this,"Please fill in all fields.")
            return
        }

        val hashMap = HashMap<String, String>()
        hashMap["productId"] = productId.toString()
        hashMap["categoryId"] = categoryId.toString()
        hashMap["categoryColorId"] = colorList[color].id.toString()
        hashMap["categorySizeId"] = sizeList[size].id.toString()
        hashMap["price"] = price.toString()
        hashMap["quantity"] = quantity.toString()

        if(isNew){
            appViewModel.addProductVariant(this,true,hashMap)
            appViewModel.getResponse().observe(this,this)
        }
        else{
            hashMap["id"] = productVariant!!.id.toString()
            appViewModel.editProductVariant(this,true,hashMap)
            appViewModel.getResponse().observe(this,this)
        }
    }


    private fun updateList(){
        colorNameList.clear()
        colorList.forEach {
            colorNameList.add(it.color)
        }
        sizeNameList.clear()
        sizeList.forEach {
            sizeNameList.add(it.size)
        }
        colorAdapter.notifyDataSetChanged()
        sizeAdapter.notifyDataSetChanged()
        Log.e("Size",colorNameList.size.toString())

        if(!isNew){
            Log.e("product",productVariant?.color.toString())
            color = colorNameList.indexOf(productVariant!!.color)
            size = sizeNameList.indexOf(productVariant!!.size)

            binding.pickColor.setSelection(color)
            binding.pickSize.setSelection(size)
        }
    }
    override fun onChanged(t: RestObservable) {
        if (t.data is CategoryColorSizeResponse) {
            binding.progressBar.visibility = View.GONE
            val response: CategoryColorSizeResponse = t.data
            if (response.code == AppConstants.SUCCESS_CODE) {

                colorList.clear()
                sizeList.clear()

                colorList.addAll(response.body.categoryColors)
                sizeList.addAll(response.body.categorySizes)
                updateList()
            }
        }
        else if(t.data is AddProductVariantResponse){
            val str = if(isNew)  "added" else "updated"
            AppUtils.showMsgOnlyWithClick(this,"Product variant $str successfully.", object : OnPopupClick{
                override fun onPopupClickListener() {
                    finish()
                }
            })
        }
    }
}