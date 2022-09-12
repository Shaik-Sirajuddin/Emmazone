package com.live.emmazone.activities.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.live.emmazone.MainActivity
import com.live.emmazone.activities.VerificationCode
import com.live.emmazone.activities.provider.AddShopDetailActivity
import com.live.emmazone.activities.provider.ProviderMainActivity
import com.live.emmazone.base.SocketManager
import com.live.emmazone.databinding.ActivityLoginBinding
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.extensionfuncton.savePreference
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.LoginResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppConstants.USER_CHOICE
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel

/*
*
************************Google and Firebase credentials********
************************email - cqlsys27@gmail.com*************
************************password - system1234@******************
*
*/

class LoginActivity : AppCompatActivity(), Observer<RestObservable> {

    lateinit var binding: ActivityLoginBinding
    private val appViewModel: AppViewModel by viewModels()
    private var deviceToken = ""
    var userChoice = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Socket Disconnect
        SocketManager.onDisConnect()

        binding.tvForgotPwd.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignIn.setOnClickListener {
            /*     if (getPreference(AppConstants.PROFILE_TYPE, "") == AppConstants.SELLER) {
                     startActivity(Intent(this, ProviderMainActivity::class.java))
                 } else {
                     startActivity(Intent(this, MainActivity::class.java))
                 }*/
            validateData()
        }

        binding.tvCreateAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        userChoice = intent.getStringExtra(USER_CHOICE).toString()

    }

    private fun validateData() {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        if (Validator.validateLogin(email, password)) {
//************************Login api hit****************************
            val hashMap = HashMap<String, String>()
            hashMap["email"] = email
            hashMap["password"] = password
            hashMap["deviceType"] = AppConstants.DEVICE_TYPE
            hashMap["deviceToken"] = deviceToken
            hashMap["type"] = userChoice

            appViewModel.loginApi(this, true, hashMap)
            appViewModel.getResponse().observe(this, this)

        } else {
            AppUtils.showMsgOnlyWithoutClick(this, Validator.errorMessage)
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

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is LoginResponse) {
                    Log.e("login",t.data.message.toString())
                    val response: LoginResponse = t.data
                    if (response.code == AppConstants.SUCCESS_CODE) {
                        savePreference(AppConstants.ROLE, response.body.role.toString())
                        savePreference(AppConstants.USER_ID, response.body.id.toString())
                        savePreference(AppConstants.NAME, response.body.username)
                        savePreference(AppConstants.AUTHORIZATION, response.body.token)
                        Log.d("tAuth" ,response.body.token)
                        savePreference(
                            AppConstants.NOTIFICATION_TYPE,
                            response.body.notificationStatus.toString()
                        )

                        Log.d(AppConstants.USER_ID, response.body.id.toString())

                        //Socket init
                        SocketManager.initSocket()

                        if (response.body.verified == 1) {

                            if (response.body.role == 3) {
                                //role = 1 -> User, 3 -> Seller
                                if (response.body.isShopAdd == 1) {
                                    savePreference(AppConstants.IS_LOGIN, true)
                                    startActivity(Intent(this, ProviderMainActivity::class.java))
                                    finishAffinity()
                                } else {
                                    startActivity(Intent(this, AddShopDetailActivity::class.java))
                                    finishAffinity()
                                }

                            } else {
                                savePreference(AppConstants.IS_LOGIN, true)
                                startActivity(Intent(this, MainActivity::class.java))
                                finishAffinity()

                            }

                        } else {
                            val intent = Intent(this, VerificationCode::class.java)
                            startActivity(intent)
                            finish()
                        }


                    }
                }
            }
            else -> {}
        }
    }

    override fun onResume() {
        super.onResume()
        getDeviceToken()
    }
}