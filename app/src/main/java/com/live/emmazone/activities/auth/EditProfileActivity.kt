package com.live.emmazone.activities.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    lateinit var binding : ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.btnUpdate.setOnClickListener {

            val alertDialog = AlertDialog.Builder(this)
            val factory = LayoutInflater.from(this)
            val view : View = factory.inflate(R.layout.dialog_profile_updated, null)

            val buttonOk= view.findViewById<Button>(R.id.ok)

            buttonOk.setOnClickListener {
                onBackPressed()
            }

            alertDialog.setView(view)
            alertDialog.show()
        }

    }
}