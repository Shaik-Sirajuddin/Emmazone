package com.live.emmazone.activities.provider

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Filter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityAddNewProductVariantBinding
import com.live.emmazone.interfaces.OnPopupClick
import com.live.emmazone.net.RestObservable
import com.live.emmazone.response_model.*
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.AppUtils.Companion.getFormattedAmount
import com.live.emmazone.utils.AppUtils.Companion.getFormattedAmountForEdit
import com.live.emmazone.utils.AppUtils.Companion.setEuroLocale
import com.live.emmazone.utils.ToastUtils
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.toBody
import kotlinx.android.synthetic.main.fragment_provider_home.view.*
import okhttp3.RequestBody
import java.util.*

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
                val price = it.productPrice.replace(".",",")
                Log.e("herePrice",price)
                binding.productPrice.setText(price)
                binding.proQuantity.setText(it.productQuantity.toString())
            }
        }
    }
    private fun initViews(){
        colorAdapter = MyAdapter(this,android.R.layout.simple_list_item_1,colorNameList)
        sizeAdapter = MyAdapter(this, android.R.layout.simple_list_item_1,sizeNameList)
        binding.pickColor.setAdapter(colorAdapter)
        binding.pickSize.setAdapter(sizeAdapter)
        binding.pickSize.setDropDownBackgroundResource(R.color.white)
        binding.pickColor.setDropDownBackgroundResource(R.color.white)
        binding.pickColor.setOnItemClickListener { adapterView, view, i, l ->
           color = i
        }
        binding.productPrice.addTextChangedListener(MyTextWatcher(binding.productPrice))
        binding.proQuantity.addTextChangedListener(MyTextWatcher(binding.proQuantity))


        binding.pickSize.setOnItemClickListener { adapterView, view, i, l ->
            size = i
        }
        binding.addSize.setOnClickListener {
            if(binding.cardView1.visibility == View.VISIBLE){
                binding.cardView1.visibility = View.GONE
            }
            else{
                binding.cardView1.visibility = View.VISIBLE
            }
        }
        binding.addColor.setOnClickListener {
            if(binding.cardView2.visibility == View.VISIBLE){
                binding.cardView2.visibility = View.GONE
            }
            else{
                binding.cardView2.visibility = View.VISIBLE
            }
        }
        binding.addSizeButton.setOnClickListener {
            addSize()
        }
        binding.addColorButton.setOnClickListener {
            addColor()
        }
    }
    private fun addColor(){
        val color = binding.newColorName.text.toString().trim()
        if(color.isEmpty()){
            AppUtils.showMsgOnlyWithoutClick(this, "Size name cannot be empty.")
            return
        }
        binding.newColorName.setText("")
        val hashMap = HashMap<String, RequestBody>()
        hashMap["categoryId"] = toBody(categoryId.toString())
        hashMap["color"] = toBody(color)
        appViewModel.addColor(this, true, hashMap)
        appViewModel.getResponse().observe(this, this)
    }
    private fun addSize(){
        val size = binding.newSizeName.text.toString().trim()
        if(size.isEmpty()){
            AppUtils.showMsgOnlyWithoutClick(this, "Size name cannot be empty.")
            return
        }
        binding.newSizeName.setText("")
        val hashMap = HashMap<String, RequestBody>()
        hashMap["categoryId"] = toBody(categoryId.toString())
        hashMap["size"] = toBody(size)
        appViewModel.addSize(this, true, hashMap)
        appViewModel.getResponse().observe(this, this)
    }
    private fun getColorSizeApiHit() {
        binding.progressBar.visibility = View.VISIBLE
        val hashMap = HashMap<String, String>()
        hashMap["categoryId"] = categoryId.toString()
        appViewModel.categoryColorSizeApi(this, true, hashMap)
        appViewModel.getResponse().observe(this, this)
    }
    private fun validateAndPush(){
        val text = binding.productPrice.text.toString().replace(".","").trim()
        val priceText = text.replace(",",".")
        val price = priceText.toDoubleOrNull()

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
        else if(t.data is AddCategoryResponse){
            val response: AddCategoryResponse = t.data
            if (response.code == AppConstants.SUCCESS_CODE) {
                ToastUtils.showLongToast(response.message)
                getColorSizeApiHit()
            }
        }
    }
    inner class MyAdapter(context: Context, resource: Int, objects: ArrayList<String>) :
        ArrayAdapter<String>(context, resource, objects) {
        override fun getFilter(): Filter {
            return object:Filter(){
                override fun performFiltering(p0: CharSequence?): FilterResults {
                    return FilterResults()
                }
                override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                }
            }
        }
    }
    inner class MyTextWatcher(private val editText: EditText) : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(e: Editable) {
            if(editText.tag == "this")return
            val text = editText.text.toString().replace(".","").trim()
            val priceText = text.replace(",",".")
            var price = priceText.toDoubleOrNull()
                ?: return
            Log.e("price",price.toString())
            editText.tag = "this"
            price *= 100
            val res = price.toInt()
            price = res.toDouble() / 100
            var formattedText = getFormattedAmountForEdit(price)
            if(text.contains(',')){

                val comma = text.indexOf(',')
                var afterText = ""
                if(comma != text.lastIndex){
                    afterText = text.substring(comma)
                    if(afterText.length > 3){
                        afterText = afterText.substring(0,3)
                    }
                    if(afterText == ",0" || afterText == ",00"){
                        formattedText = "$formattedText$afterText"
                    }
                    else if(afterText.length == 3 && afterText[2]=='0'){
                        formattedText = "${formattedText}0"
                    }
                }
                else{
                    formattedText = "$formattedText,"
                }

            }

            editText.setText(formattedText)

            editText.tag = null
            editText.setSelection(editText.length())
        }

    }

}