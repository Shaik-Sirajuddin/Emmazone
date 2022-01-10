package com.live.emmazone.extensionfuncton

import android.text.TextUtils
import android.util.Patterns
import com.live.emmazone.R
import com.live.emmazone.base.AppController

object Validator {

    var errorMessage = ""

    fun signUpValidation(
        image: String, name: String, email: String, mobileNo: String,
        password: String, confirmPass: String, termsCondition: Boolean
    ): Boolean {

        return if (TextUtils.isEmpty(image)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_select_image)
            false
        } else if (TextUtils.isEmpty(name)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_name)
            false
        } else if (TextUtils.isEmpty(email)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_email)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessage = AppController.instance!!.getString(R.string.msg_valid_email)
            false
        } else if (TextUtils.isEmpty(mobileNo)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_contact)
            false
        } else if (TextUtils.isEmpty(password)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_password)
            false
        } else if (password.length < 6) {
            errorMessage = AppController.instance!!.getString(R.string.msg_password_6_character)
            false
        } else if (password != confirmPass) {
            errorMessage = AppController.instance!!.getString(R.string.msg_password_not_match)
            false
        } else if (!termsCondition) {
            errorMessage = AppController.instance!!.getString(R.string.msg_agree_terms_condition)
            false
        } else true

    }


    fun validateOtp(otp:String):Boolean{
        return if (TextUtils.isEmpty(otp)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_otp)
            false
        }else  true
    }


    fun validateLogin(
        email: String,
        password: String
    ): Boolean {
        return if (TextUtils.isEmpty(email)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_email)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessage = AppController.instance!!.getString(R.string.msg_valid_email)
            false
        } else if (TextUtils.isEmpty(password)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_password)
            false
        } else return true
    }

    fun validateForgot(email: String): Boolean {
        return if (TextUtils.isEmpty(email)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_email)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessage = AppController.instance!!.getString(R.string.msg_valid_email)
            false
        } else return true
    }

}