package com.live.emmazone.activities.auth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.live.emmazone.databinding.ActivityForgotPasswordBinding
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.interfaces.OnPopupClick
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.ForgotPasswordResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel

class ForgotPasswordActivity : AppCompatActivity(),
    Observer<RestObservable>, OnPopupClick {

    private val appViewModel: AppViewModel by viewModels()
    lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageArrowback.setOnClickListener {
            onBackPressed()
        }

        binding.btnSubmit.setOnClickListener {
            validateData()
        }

    }


    private fun validateData() {
        val email = binding.edtEmail.text.toString().trim()

        if (Validator.validateForgot(email)) {
            //************Forgot Password API hit*********************

            val hashMap = HashMap<String, String>()
            hashMap["email"] = email

            appViewModel.forgotPassApi(this, true, hashMap)
            appViewModel.getResponse().observe(this, this)

        } else AppUtils.showMsgOnlyWithoutClick(this, Validator.errorMessage)
    }


    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is ForgotPasswordResponse) {
                    val response: ForgotPasswordResponse = t.data
                    if (response.code == AppConstants.SUCCESS_CODE) {
                        AppUtils.showMsgOnlyWithClick(this, response.message, this)

                    }
                }
            }
        }
    }

    override fun onPopupClickListener() {
        finish()
    }
}