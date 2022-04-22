package com.live.emmazone.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.live.emmazone.MainActivity
import com.live.emmazone.activities.provider.AddShopDetailActivity
import com.live.emmazone.databinding.ActivityVerificationCodeBinding
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.extensionfuncton.savePreference
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.OtpResendResponse
import com.live.emmazone.response_model.OtpVerifyResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel

class VerificationCode : AppCompatActivity(), Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()

    lateinit var binding: ActivityVerificationCodeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        clicksHandle()

    }

    private fun clicksHandle() {
        binding.imageArrowback.setOnClickListener {
            onBackPressed()
        }


        binding.tvResend.setOnClickListener {
            appViewModel.resendOtpApi(this, true)
            appViewModel.getResponse().observe(this, this)
        }

        binding.btnSubmit.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val otp = binding.otpPin.text.toString().trim()

        if (Validator.validateOtp(otp)) {

            val hashMap = HashMap<String, String>()
            hashMap["otp"] = otp

            appViewModel.otpVerifyApi(this, true, hashMap)
            appViewModel.getResponse().observe(this, this)
        } else AppUtils.showMsgOnlyWithoutClick(this, Validator.errorMessage)
    }

    override fun onChanged(t: RestObservable?) {
        when (t?.status) {
            Status.SUCCESS -> {
                if (t.data is OtpVerifyResponse) {
                    val response: OtpVerifyResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {

                        if (getPreference(AppConstants.PROFILE_TYPE, "") == "seller") {
                            startActivity(Intent(this, AddShopDetailActivity::class.java))
                            finish()
                        } else {
                            savePreference(AppConstants.ROLE, AppConstants.USER_ROLE)
                            savePreference(AppConstants.IS_LOGIN, true)
                            startActivity(Intent(this, MainActivity::class.java))
                            finishAffinity()
                        }

                    }
                } else if (t.data is OtpResendResponse) {
                    val response: OtpResendResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {

                        AppUtils.showMsgOnlyWithoutClick(this, response.message)
                    }
                }
            }
            else -> {
            }
        }
    }
}