package com.live.emmazone.activities.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    lateinit var binding : ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}