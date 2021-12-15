package com.live.emmazone.activities.provider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityEditProductBinding

class EditProductActivity : AppCompatActivity() {
    lateinit var binding : ActivityEditProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.btnUpdate.setOnClickListener {
            val alertBuilder = AlertDialog.Builder(this)
            val factory = LayoutInflater.from(this)
            val view : View = factory.inflate(R.layout.dialog_product_detail_updated, null)

            val buttonOk= view.findViewById<Button>(R.id.ok)

            buttonOk.setOnClickListener {
                onBackPressed()
            }

            alertBuilder.setView(view)
            alertBuilder.show()

        }

    }
}