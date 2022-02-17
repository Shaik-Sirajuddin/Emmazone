package com.live.emmazone.activities.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
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
import com.live.emmazone.utils.ImagePickerUtility
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.loadImage
import com.schunts.extensionfuncton.prepareMultiPart
import com.schunts.extensionfuncton.toBody
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

class EditProfileActivity : ImagePickerUtility(), Observer<RestObservable> {
    lateinit var binding: ActivityEditProfileBinding

    private val appViewModel: AppViewModel by viewModels()

    private var mImagePath = ""
    private var profileDetail: ProfileResponse? = null


    override fun selectedImage(imagePath: String?, code: Int) {
        if (imagePath != null) {
            mImagePath = imagePath
            binding.ivProfile.loadImage(imagePath)
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

        binding.ivProfile.loadImage(AppConstants.IMAGE_USER_URL + profileDetail!!.body.image)

        binding.edtName.setText(profileDetail!!.body.username)
        binding.edtEditEmail.setText(profileDetail!!.body.email)
        binding.ccp.setCountryForNameCode(profileDetail!!.body.countryCode)
        binding.edtMobile.setText(profileDetail!!.body.phone)
    }

    private fun AlertDialog() {

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
                    AlertDialog()

                }

            }

        }
    }


}