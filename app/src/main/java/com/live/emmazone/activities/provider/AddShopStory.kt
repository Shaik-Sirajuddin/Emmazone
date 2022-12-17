package com.live.emmazone.activities.provider

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.databinding.ActivityAddShopStoryBinding
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.interfaces.OnPopupClick
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils.Companion.showMsgOnlyWithClick
import com.live.emmazone.utils.AppUtils.Companion.showMsgOnlyWithoutClick
import com.live.emmazone.utils.AppUtils.Companion.showToast
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.loadImage
import com.schunts.extensionfuncton.prepareMultiPart
import com.schunts.extensionfuncton.toBody
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class AddShopStory : AppCompatActivity(), Observer<RestObservable> {

    private lateinit var binding : ActivityAddShopStoryBinding
    lateinit var image : String
    private val nameList = arrayOf("1 Day", "1 Week" , "2 Weeks")
    private val timeList = arrayOf(86400,7*86400,14*86400)
    private var index = 0
    private val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddShopStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        image = intent.getStringExtra("imagePath")?:""
        if(image.isEmpty()){
            showToast("No image selected")
            finish()
        }
        binding.image.loadImage(image)
        val adapter = ArrayAdapter<String>(
            this,
            R.layout.simple_spinner_dropdown_item,
            nameList
        )
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?,
                arg1: View?,
                arg2: Int,
                arg3: Long
            ) {
                index = arg2
            }
            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        }
        binding.post.setOnClickListener {
            addStory()
        }
        binding.back.setOnClickListener {
            finish()
        }
    }
    private fun addStory(){
        val hashMap = HashMap<String, RequestBody>()
        hashMap["vendorId"] = toBody(getPreference(AppConstants.VENDOR_ID,""))
        hashMap["expiryDate"] = toBody(timeList[index].toString())
        var mainImage: MultipartBody.Part? = null
        if (image.isNotEmpty()) {
            mainImage = prepareMultiPart("mainImage", File(image))
        }
        appViewModel.addShopStory(this,true,hashMap,mainImage)
        appViewModel.mResponse.observe(this,this)
    }

    override fun onChanged(t: RestObservable?) {
        when(t!!.status){
            Status.SUCCESS->{
                showMsgOnlyWithClick(this,"Story Added Successfully", object : OnPopupClick{
                    override fun onPopupClickListener() {
                        finish()
                    }
                })
            }
            Status.ERROR->{
                showMsgOnlyWithoutClick(this,t.error.toString())
            }
            else->{

            }
        }
    }

}