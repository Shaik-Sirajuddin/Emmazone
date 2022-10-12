package com.live.emmazone.activities.auth

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.graphics.drawable.toBitmap
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.amulyakhare.textdrawable.TextDrawable
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.live.emmazone.R
import com.live.emmazone.activities.TermsCondition
import com.live.emmazone.activities.VerificationCode
import com.live.emmazone.base.SocketManager
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
import com.schunts.extensionfuncton.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File


/*
*
************************Google and Firebase credentials********
************************email - cqlsys27@gmail.com*************
************************password - system1234@******************
*
*/

class SignUpActivity : ImagePickerUtility(), Observer<RestObservable> {

    lateinit var binding: ActivitySignUpBinding
    var termsConditionCheck = false

    private var mImagePath = ""
    private var byteArray:ByteArray? = null

    private val appViewModel: AppViewModel by viewModels()
    private var deviceToken = ""

    override fun selectedImage(imagePath: String?, code: Int , bitmap: Bitmap?) {
        if (imagePath != null) {
            mImagePath = imagePath
            binding.pickImage.loadImage(imagePath)
        }
        if(bitmap != null){
            byteArray = bitmapToByte(bitmap)
            mImagePath = ""
            binding.pickImage.loadImage(bitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clicksHandle()
        //Socket Disconnect
        SocketManager.onDisConnect()
    }

    private fun clicksHandle() {
        binding.edtName.doAfterTextChanged {
            if(mImagePath.isNotEmpty())return@doAfterTextChanged
            val text = it.toString().trim()
            if(text.length !=1 )return@doAfterTextChanged
            binding.pickImage.loadImage(letterByteArray(text))
        }
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
        var mainImage: MultipartBody.Part? = null
        if (mImagePath.isNotEmpty())
            mainImage = prepareMultiPart("image", File(mImagePath))
        else if (byteArray!=null)
            mainImage = prepareMultiPart("image",byteArray)
        else
            mainImage = prepareMultiPart("image", "")

        if (Validator.signUpValidation(
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
            hashMap["deviceType"] = toBody(AppConstants.DEVICE_TYPE)
            hashMap["deviceToken"] = toBody(deviceToken)
            //Type 1 for user and Type 3 for Seller
            if (getPreference(AppConstants.PROFILE_TYPE, "") == "user")
                hashMap["type"] = toBody(1.toString())
            else
                hashMap["type"] = toBody(3.toString())

            appViewModel.signUpApi(this, true, hashMap, mainImage)
            appViewModel.getResponse().observe(this, this)
        } else AppUtils.showMsgOnlyWithoutClick(this, Validator.errorMessage)
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is SignUpResponse) {
                    val response: SignUpResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {

                        savePreference(AppConstants.USER_ID, response.body.id.toString())
                        savePreference(AppConstants.AUTHORIZATION, response.body.token)
                        savePreference(AppConstants.NAME, response.body.username)

                        savePreference(
                            AppConstants.NOTIFICATION_TYPE,
                            response.body.notificationStatus.toString()
                        )

                        //Socket init
                        SocketManager.initSocket()

                        val intent = Intent(this, VerificationCode::class.java)
                        startActivity(intent)
                    }
                }
            }
            else -> {}
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