package com.live.emmazone.activities.auth

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityEditProfileBinding
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
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


class EditProfileActivity : ImagePickerUtility(), Observer<RestObservable> {
    lateinit var binding: ActivityEditProfileBinding

    private val appViewModel: AppViewModel by viewModels()

    private var mImagePath = ""
    private var byteArray:ByteArray? = null
    private var profileDetail: ProfileResponse? = null


    override fun selectedImage(imagePath: String?, code: Int, bitmap: Bitmap?) {
        if (imagePath != null) {
            mImagePath = imagePath
            binding.ivProfile.loadImage(imagePath)
            byteArray = null
        }
        if(bitmap != null){
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
            if(name.isEmpty()){
                showToast("Name must not be empty!")
                return@setOnClickListener
            }
            byteArray = letterByteArray(name.substring(0,1))
            mImagePath = ""
            binding.ivProfile.loadImage(byteArray!!)
        }
        binding.edtName.doAfterTextChanged {
            if(mImagePath.isNotEmpty())return@doAfterTextChanged
            val text = it.toString().trim()
            if (text.length != 1) return@doAfterTextChanged
            byteArray = letterByteArray(text)
            binding.ivProfile.loadImage(byteArray!!)
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
        else if (byteArray!=null)
            image = prepareMultiPart("image",byteArray)
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

            }

            else -> {}
        }
    }


}