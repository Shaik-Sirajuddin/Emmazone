package com.live.emmazone.activities.provider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.activity.viewModels
import com.live.emmazone.model.ProductVariant
import com.live.emmazone.net.RestObservable
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityAddNewProductVariantBinding
import com.live.emmazone.interfaces.OnPopupClick
import com.live.emmazone.response_model.*
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.toBody
import okhttp3.RequestBody
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.roundToInt

class AddNewProductVariant : AppCompatActivity(), Observer<RestObservable> {

    lateinit var binding : ActivityAddNewProductVariantBinding
    private var product : Product? =  null
    private lateinit var group : ProductGroup
    private var categoryId : Int = 0
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
        const val PRODUCT = "product"
        const val GROUP  = "group"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewProductVariantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        group = intent.extras?.get(GROUP) as ProductGroup
        categoryId = group.categoryId
        product = intent.extras?.get(PRODUCT) as Product?
        isNew = product==null
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
            product?.let{
                binding.productPrice.setText(it.productPrice.toDouble().roundToInt().toString())
                binding.proQuantity.setText(it.productQuantity.toString())
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
        val price = binding.productPrice.text.toString().trim().toDoubleOrNull()
        val quantity = binding.proQuantity.text.toString().trim().toIntOrNull()
        var isValid = true
        isValid = price !=null && isValid
        isValid = quantity !=null  && isValid
        isValid = size!=-1  && isValid
        isValid = color!=-1  && isValid

        if(!isValid){
            AppUtils.showMsgOnlyWithoutClick(this,"Please fill in all fields.")
            return
        }

        val hashMap = HashMap<String, RequestBody>()
        hashMap["product_name"] = toBody(group.name)
        hashMap["shortDescription"] = toBody(group.shortDescription)
        hashMap["description"] = toBody(group.description)
        hashMap["categoryId"] = toBody(group.categoryId.toString())
        hashMap["product_highlight"] = toBody(group.productHighlight.toString())
        hashMap["groupId"] = toBody(group.id.toString())
        hashMap["categoryId"] = toBody(categoryId.toString())
        hashMap["colorId"] = toBody(colorList[color].id.toString())
        hashMap["sizeId"] = toBody(sizeList[size].id.toString())
        hashMap["price"] = toBody(price.toString())
        hashMap["product_quantity"] = toBody(quantity.toString())

        if(isNew){
            appViewModel.addProductApi(this,true,hashMap)
            appViewModel.getResponse().observe(this,this)
        }
        else{
            hashMap["id"] = toBody(product!!.id.toString())
            appViewModel.editShopProductApi(this,true,hashMap)
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

        if(!isNew){
            color = colorNameList.indexOf(product!!.productColor.color)
            size = sizeNameList.indexOf(product!!.productSize.size)
            Log.d("indez",color.toString())
            binding.pickColor.setText(colorNameList[color],false)
            binding.pickSize.setText(sizeNameList[size],false)
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
        else if(t.data is AddProductResponse){
            val str = if(isNew)  "added" else "updated"
            AppUtils.showMsgOnlyWithClick(this,"Product variant $str successfully.", object : OnPopupClick{
                override fun onPopupClickListener() {
                    finish()
                }
            })
        }
    }
}