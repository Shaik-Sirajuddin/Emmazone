package com.live.emmazone.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.live.emmazone.R
import com.live.emmazone.activities.main.Cart
import com.live.emmazone.databinding.ActivityProfileBinding
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.LoginResponse
import com.live.emmazone.response_model.NotificationStatusResponse
import com.live.emmazone.response_model.ProfileResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.loadImage
import com.schunts.extensionfuncton.toast

class ProfileActivity : AppCompatActivity(), Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()

    lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.btnEditProfile.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        appViewModel.profileApi(this, true)
        appViewModel.getResponse().observe(this, this)

    }

    override fun onChanged(t: RestObservable?) {

        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is ProfileResponse) {
                    val response: ProfileResponse = t.data
                    binding.ivProfile.loadImage(AppConstants.IMAGE_USER_URL+response.body.image)

                    binding.tvUserName.text = response.body.username
                    binding.tvUserEmail.text = response.body.email
                    binding.tvUserPhone.text = response.body.countryCode+response.body.phone

                }
            }
        }

    }
}