package com.live.emmazone.activities.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.live.emmazone.R
import com.live.emmazone.activities.TermsCondition
import com.live.emmazone.activities.VerificationCode
import com.live.emmazone.databinding.ActivitySignUpBinding
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.extensionfuncton.savePreference
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.SignUpResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.ImagePickerUtility
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.loadImage
import com.schunts.extensionfuncton.prepareMultiPart
import com.schunts.extensionfuncton.toBody
import okhttp3.RequestBody
import java.io.File

class SignUpActivity : ImagePickerUtility(), Observer<RestObservable> {

    lateinit var binding: ActivitySignUpBinding
    var termsConditionCheck = false

    private var mImagePath = ""

    private val appViewModel: AppViewModel by viewModels()
    private var deviceToken = ""

    override fun selectedImage(imagePath: String?, code: Int) {
        if (imagePath != null) {
            mImagePath = imagePath
            binding.pickImage.loadImage(imagePath)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clicksHandle()
    }

    private fun clicksHandle() {
        binding.checkBox.setOnClickListener {

            if (termsConditionCheck) {
                termsConditionCheck = false
                binding.checkBox.setImageResource(R.drawable.otp_bg)
            } else {
                termsConditionCheck = true
                binding.checkBox.setImageResource(R.drawable.checkbox_tick)
            }

        }

        binding.imageArrowback.setOnClickListener {
            onBackPressed()
        }

        binding.termsConditions.setOnClickListener {
            val intent = Intent(this, TermsCondition::class.java)
            startActivity(intent)
        }

        binding.btnSignUp.setOnClickListener {
            validateData()
        }

        binding.tvSignIn.setOnClickListener {
            onBackPressed()
        }

        binding.pickImage.setOnClickListener {
            getImage(0, false)
        }

    }

    private fun validateData() {
        val name = binding.edtName.text.toString().trim()
        val email = binding.edtEmail.text.toString().trim()
        val mobileNo = binding.edtMobile.text.toString().trim()
        val password = binding.edtPwd.text.toString().trim()
        val confirmPass = binding.edtConfirmPwd.text.toString().trim()
        val image = prepareMultiPart("image", File(mImagePath))

        if (Validator.signUpValidation(
                mImagePath,
                name,
                email,
                mobileNo,
                password,
                confirmPass,
                termsConditionCheck
            )
        ) {

            //Signup API hit
            val hashMap = HashMap<String, RequestBody>()
            hashMap["name"] = toBody(name)
            hashMap["email"] = toBody(email)
            hashMap["phone"] = toBody(mobileNo)
            hashMap["countryCode"] = toBody(binding.ccp.selectedCountryCodeWithPlus)
            hashMap["password"] = toBody(password)
            hashMap["device_type"] = toBody(AppConstants.DEVICE_TYPE)
            hashMap["device_token"] = toBody(deviceToken)
            //Type 1 for user and Type 3 for Seller
               if (getPreference(AppConstants.PROFILE_TYPE,"").equals("user"))
                   hashMap["type"] = toBody(1.toString())
               else
                   hashMap["type"] = toBody(3.toString())

            appViewModel.signUpApi(this, true, hashMap, image)
            appViewModel.getResponse().observe(this, this)
        } else AppUtils.showMsgOnlyWithoutClick(this, Validator.errorMessage)
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is SignUpResponse) {
                    val response: SignUpResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {

                        savePreference(AppConstants.AUTHORIZATION,response.body.token)
                        savePreference(AppConstants.ROLE,response.body.role)

                        val intent = Intent(this, VerificationCode::class.java)
                        startActivity(intent)
                        finishAffinity()
                    }
                }
            }
        }
    }


    private fun getDeviceToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e("DEVICE TOKEN", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.e("DEVICE TOKEN", token.toString())
            deviceToken = token.toString()
        })
    }

    override fun onResume() {
        super.onResume()
        getDeviceToken()
    }

}