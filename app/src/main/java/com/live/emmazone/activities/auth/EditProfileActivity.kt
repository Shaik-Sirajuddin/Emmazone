package com.live.emmazone.activities.auth

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityEditProfileBinding
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.extensionfuncton.clearPreferences
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.interfaces.OnPopupClick
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.response_model.EditProfileResponse
import com.live.emmazone.response_model.ProfileResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.AppUtils.Companion.showToast
import com.live.emmazone.utils.ImagePickerUtility
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import kotlin.collections.HashMap


class EditProfileActivity : ImagePickerUtility(), Observer<RestObservable> {
    lateinit var binding: ActivityEditProfileBinding

    private val appViewModel: AppViewModel by viewModels()

    private var mImagePath = ""
    private var byteArray: ByteArray? = null
    private var profileDetail: ProfileResponse? = null


    override fun selectedImage(imagePath: String?, code: Int, bitmap: Bitmap?) {
        if (imagePath != null) {
            mImagePath = imagePath
            binding.ivProfile.loadImage(imagePath)
            byteArray = null
        }
        if (bitmap != null) {
            byteArray = bitmapToByte(bitmap)
            mImagePath = ""
            binding.ivProfile.loadImage(bitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        profileDetail = intent.getSerializableExtra("profile") as ProfileResponse

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.btnUpdate.setOnClickListener {
            editProfileApi()
        }

        binding.apply {
            camera.setOnClickListener {
                getImage(0, false)
            }
        }

        binding.ivProfile.loadImage(AppConstants.IMAGE_USER_URL + profileDetail!!.body.user.image)

        binding.edtName.setText(profileDetail!!.body.user.username)
        binding.edtEditEmail.setText(profileDetail!!.body.user.email)
        binding.ccp.setCountryForNameCode(profileDetail!!.body.user.countryCode)
        binding.edtMobile.setText(profileDetail!!.body.user.phone)
        binding.ccp.registerCarrierNumberEditText(binding.edtMobile)
        binding.ccp.fullNumber =
            profileDetail!!.body.user.countryCode + profileDetail!!.body.user.phone

        binding.imageDelete.setOnClickListener {
//
            val name = binding.edtName.text.toString().trim()
            if (name.isEmpty()) {
                showToast("Name must not be empty!")
                return@setOnClickListener
            }
            byteArray = letterByteArray(name.substring(0, 1))
            mImagePath = ""
            binding.ivProfile.loadImage(byteArray!!)
        }
        binding.edtName.doAfterTextChanged {
            if (mImagePath.isNotEmpty()) return@doAfterTextChanged
            val text = it.toString().trim()
            if (text.length != 1) return@doAfterTextChanged
            byteArray = letterByteArray(text)
            binding.ivProfile.loadImage(byteArray!!)
        }
        binding.deleteAccount.setOnClickListener {
            handleDeleteAccount()
        }
    }

    private fun isSeller() = getPreference(AppConstants.PROFILE_TYPE, "") == "seller"
    private fun handleDeleteAccount() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setContentView(R.layout.dialog_delete_account)

        dialog.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(this, android.R.color.transparent)
        )

        val yesBtn: Button = dialog.findViewById(R.id.btnCancelYes)
        val noBtn: Button = dialog.findViewById(R.id.btnCancelNo)
        val text: TextView = dialog.findViewById(R.id.tv1)

        if (isSeller()) {
            text.setText(R.string.delete_seller_account_text)
        } else {
            text.setText(R.string.delete_user_account_text)
        }

        yesBtn.setOnClickListener {
            dialog.dismiss()
            deleteProfileApiHit()
        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }

    private fun deleteProfileApiHit() {
        if (isSeller()) {
            //seller
            val hashMap = HashMap<String, String>()
            hashMap["vendorId"] = getPreference(AppConstants.VENDOR_ID, "")
            appViewModel.initiateDeleteShopProfile(this, true, hashMap)
            appViewModel.getResponse().observe(this, this)
        } else {
            //user
            appViewModel.deleteProfileApi(this, true)
            appViewModel.getResponse().observe(this, this)
        }
    }

    private fun alertDialog() {

        val alertDialog = AlertDialog.Builder(this)
        val factory = LayoutInflater.from(this)
        val view: View = factory.inflate(R.layout.dialog_profile_updated, null)

        val buttonOk = view.findViewById<Button>(R.id.ok)


        buttonOk.setOnClickListener {
            onBackPressed()
        }

        alertDialog.setView(view)
        alertDialog.show()

    }

    private fun editProfileApi() {
        val image: MultipartBody.Part
        val name = binding.edtName.text.toString().trim()
        val mobileNo = binding.edtMobile.text.toString().trim()
        if (mImagePath.isNotEmpty())
            image = prepareMultiPart("image", File(mImagePath))
        else if (byteArray != null)
            image = prepareMultiPart("image", byteArray)
        else
            image = prepareMultiPart("image", "")

        if (Validator.editProfileValidation(
                name,
                mobileNo,
            )
        ) {

            //EditProfile API hit
            val hashMap = HashMap<String, RequestBody>()
            hashMap["name"] = toBody(name)
            hashMap["phone"] = toBody(mobileNo)
            hashMap["countryCode"] = toBody(binding.ccp.selectedCountryCodeWithPlus)


            appViewModel.editProfileApi(this, true, hashMap, image)
            appViewModel.getResponse().observe(this, this)
        } else AppUtils.showMsgOnlyWithoutClick(this, Validator.errorMessage)
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is EditProfileResponse) {
                    val response: EditProfileResponse = t.data
                    setResult(RESULT_OK)
                    alertDialog()
                }
                if (t.data is CommonResponse) {
                    val response = t.data
                    if (response.code == AppConstants.SUCCESS_CODE) {
                        if (isSeller()) {
                            AppUtils.showMsgOnlyWithClick(
                                this,
                                response.message,
                                object : OnPopupClick {
                                    override fun onPopupClickListener() {
                                        finish()
                                    }
                                }
                            )
                        } else {
                            clearPreferences()
                            val intent = Intent(this, UserLoginChoice::class.java)
                            startActivity(intent)
                            finishAffinity()
                        }
                    }
                }

            }
            else -> {}
        }
    }

}