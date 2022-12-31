package com.live.emmazone.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.databinding.ActivityReturnBinding
import com.live.emmazone.interfaces.OnPopupClick
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.response_model.ScanOrderResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel

class ReturnActivity : AppCompatActivity(), Observer<RestObservable> {
    private lateinit var binding: ActivityReturnBinding
    private var isCourier = true
    private val appViewModel: AppViewModel by viewModels()
    private var id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReturnBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.manualRadio.isClickable = false
        binding.courierRadio.isClickable = false

        id = intent.getStringExtra(AppConstants.ORDER_ID).toString()
        binding.courier.setOnClickListener {
            isCourier = true
            toggleRadioButton()
        }
        binding.manual.setOnClickListener {
            isCourier = false
            toggleRadioButton()
        }
        binding.save.setOnClickListener {
            orderStatusApiHit(status = 7)
        }
    }


    private fun toggleRadioButton() {
        binding.manualRadio.isChecked = !isCourier
        binding.courierRadio.isChecked = isCourier
    }

    private fun orderStatusApiHit(status: Int) {
        val hashMap = HashMap<String, String>()
        hashMap["id"] = id
        hashMap["orderStatus"] =
            status.toString() // 0=>pending 1=>On The Way 2=>Delivered 3=>cancelled // 7=> return

        appViewModel.orderStatusApi(this, hashMap, true)

        appViewModel.getResponse().observe(this, this)
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is ScanOrderResponse) {
                    AppUtils.showMsgOnlyWithClick(
                        this, "Return initiated successfully"
                    ,object : OnPopupClick{
                            override fun onPopupClickListener() {
                                finish()
                            }
                        })
                }
            }
            else -> {}
        }
    }
}