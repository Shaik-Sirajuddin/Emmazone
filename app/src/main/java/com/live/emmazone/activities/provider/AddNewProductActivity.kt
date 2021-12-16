package com.live.emmazone.activities.provider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityAddNewProductBinding

class AddNewProductActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddNewProductBinding
    var isNotifyOn = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgNotifyOnOff.setOnClickListener {

            isNotifyOn = !isNotifyOn
            binding.imgNotifyOnOff.setImageResource(
                if (isNotifyOn)
                    R.drawable.on
            else
                R.drawable.off
            )

        }

        binding.btnSave.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val factory = LayoutInflater.from(this)
            val view : View = factory.inflate(R.layout.dialog_product_added, null)

            val buttonOk= view.findViewById<Button>(R.id.ok)

            buttonOk.setOnClickListener {
                onBackPressed()
            }

            builder.setView(view)
            builder.show()

        }

    }
}