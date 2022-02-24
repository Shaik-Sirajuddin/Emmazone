package com.live.emmazone.extensionfuncton

import android.app.Activity
import android.text.TextUtils
import android.util.Patterns
import com.live.emmazone.R
import com.live.emmazone.base.AppController
import com.stripe.android.view.CardNumberEditText

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

    fun validateOtp(otp: String): Boolean {
        return if (TextUtils.isEmpty(otp)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_otp)
            false
        } else true
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


    fun validateAddShop(
        image: String,
        shopName: String,
        shopYear: String,
        address: String,
        desc: String,
        categories: String
    ): Boolean {
        return if (TextUtils.isEmpty(image)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_select_image)
            false
        } else if (TextUtils.isEmpty(shopName)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_shop_name)
            false
        } else if (TextUtils.isEmpty(shopYear)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_shop_year)
            false
        } else if (TextUtils.isEmpty(address)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_address)
            false
        } else if (TextUtils.isEmpty(desc)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_description)
            false
        } else if (TextUtils.isEmpty(categories)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_select_category)
            false
        } else return true
    }

    fun validateChangePassword(pass: String, newpass: String, confirmpass: String): Boolean {
        when {
            pass.isEmpty() -> {
                errorMessage = AppController.instance!!.getString(R.string.msg_enter_old_password)
                return false
            }
            pass.length < 6 -> {
                errorMessage = AppController.instance!!.getString(R.string.msg_password_6_character)
                return false
            }
            newpass.isEmpty() -> {
                errorMessage = AppController.instance!!.getString(R.string.msg_enter_new_password)
                return false
            }
            newpass.length < 6 -> {
                errorMessage = AppController.instance!!.getString(R.string.msg_password_6_character)
                return false
            }
            !newpass.equals(confirmpass) -> {
                errorMessage =
                    AppController.instance!!.getString(R.string.error_pass_confrimpass_not_same)
                return false
            }
            else -> {
                return true
            }
        }
    }

    fun editProfileValidation(
        name: String, mobileNo: String,
    ): Boolean {

        return if (TextUtils.isEmpty(name)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_name)
            false
        } else if (TextUtils.isEmpty(mobileNo)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_contact)
            false
        } else true

    }

    fun addAddressValidation(
        name: String, address: String, city: String, state: String, zipcode: String
    ): Boolean {

        return if (TextUtils.isEmpty(name)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_name)
            false
        } else if (TextUtils.isEmpty(address)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_address)
            false
        } else if (TextUtils.isEmpty(city)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_city)
            false
        } else if (TextUtils.isEmpty(state)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_state)
            false
        } else if (TextUtils.isEmpty(zipcode)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_zipcode)
            false
        } else true

    }


    fun addProductValidation(
        name: String,
        desc: String,
        price: String,
        quantity: String,
        categories: String,
        color: String,
        size: String,
        imageList: ArrayList<String>,
        mainImagePath: String
    ): Boolean {

        return if (mainImagePath.isEmpty()) {
            errorMessage = "Please select main image"
            false
        }
        else if (imageList.size == 0) {
            errorMessage = "Please select atleast one product image"
            false
        } else if (TextUtils.isEmpty(name)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_product_name)
            false
        } else if (TextUtils.isEmpty(desc)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_description)
            false
        } else if (TextUtils.isEmpty(price)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_price)
            false
        } else if (TextUtils.isEmpty(quantity)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_quantity)
            false
        } else if (TextUtils.isEmpty(categories)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_select_category_1)
            false
        } else if (TextUtils.isEmpty(color)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_select_color)
            false
        } else if (TextUtils.isEmpty(size)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_select_size)
            false
        } else true

    }

    fun reviewValidation(rating: Float, comment: String): Boolean {
        return if (rating == 0.0F) {
            errorMessage = AppController.instance!!.getString(R.string.msg_select_rating)
            false
        } else if (TextUtils.isEmpty(comment)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_pls_add_comment)
            false
        } else {
            true
        }
    }

    fun addCardValidation(
        name: String,
        cardEditText: CardNumberEditText,
        expDate: String
    ): Boolean {
        return if (TextUtils.isEmpty(name)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_name)
            false
        } else if (!cardEditText.isCardNumberValid) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_valid_card)
            false
        } else if (TextUtils.isEmpty(expDate)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_exp_date)
            false
        } else {
            true
        }
    }

    fun addAccountValidation(
        ifsc: String, bank: String, accNo: String,
        confirmAccNo: String, name: String
    ): Boolean {

        return if (TextUtils.isEmpty(ifsc)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_ifsc)
            false
        } else if (TextUtils.isEmpty(bank)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_bank_branch)
            false
        } else if (TextUtils.isEmpty(accNo)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_acc_no)
            false
        } else if (accNo != confirmAccNo) {
            errorMessage = AppController.instance!!.getString(R.string.msg_acc_no_not_match)
            false
        }else if (TextUtils.isEmpty(name)) {
            errorMessage = AppController.instance!!.getString(R.string.msg_enter_name)
            false
        } else return true

    }
}