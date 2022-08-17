package com.live.emmazone.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityChangePasswordBinding
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.interfaces.OnPopupClick
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.ChangePasswordResponse
import com.live.emmazone.response_model.ForgotPasswordResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.ToastUtils
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.toast

class ChangePassword : AppCompatActivity(), Observer<RestObservable>, OnPopupClick {

    private val appViewModel: AppViewModel by viewModels()
    lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            onBackPressed()
        }
        binding.btnSubmit.setOnClickListener {
            validateData()

//            onBackPressed()
//            finish()
        }

    }

    private fun validateData() {
        val oldPswrd = binding.editOldPwd.text.toString().trim()
        val newPswrd = binding.editnewPwd.text.toString().trim()
        val confPswrd = binding.edidConfirmPwd.text.toString().trim()

        if (Validator.validateChangePassword(oldPswrd,newPswrd,confPswrd)) {
            //************Forgot Password API hit*********************

            val hashMap = HashMap<String, String>()
            hashMap["old_password"] = oldPswrd
            hashMap["new_password"] = confPswrd

            appViewModel.changePassApi(this, true, hashMap)
            appViewModel.getResponse().observe(this, this)

        } else AppUtils.showMsgOnlyWithoutClick(this, Validator.errorMessage)
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is ChangePasswordResponse) {
                    val response: ChangePasswordResponse = t.data
                    if (response.code == AppConstants.SUCCESS_CODE) {
                        AppUtils.showMsgOnlyWithClick(this, response.message, this)

                    }
                }
            }
            Status.ERROR ->{
//                ToastUtils.showLongToast(t.data.)
            }
            else -> {}
        }

    }

    override fun onPopupClickListener() {
        finish()
    }


}