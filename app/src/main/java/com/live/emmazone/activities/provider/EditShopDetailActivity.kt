package com.live.emmazone.activities.provider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterEditShop
import com.live.emmazone.databinding.ActivityEditShopDetailBinding
import com.live.emmazone.model.ModelEditShopCategory

class EditShopDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityEditShopDetailBinding
    val list = ArrayList<ModelEditShopCategory>()
    lateinit var adapter : AdapterEditShop

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditShopDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

      binding.imageArrowback.setOnClickListener {
          onBackPressed()
      }
        binding.btnUpdate.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            val factory = LayoutInflater.from(this)
            val view : View = factory.inflate(R.layout.dialog_shop_profile_updated, null)

            val buttonOk= view.findViewById<Button>(R.id.ok)

            buttonOk.setOnClickListener {
                onBackPressed()
            }

            alertDialog.setView(view)
            alertDialog.show()
        }

        binding.rvEditShop.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        list.add(ModelEditShopCategory(R.drawable.shoe_round, "Shoes", "Edit Image"))
        list.add(ModelEditShopCategory(R.drawable.gogg, "Goggles", "Edit Image"))
        list.add(ModelEditShopCategory(R.drawable.clock3, "Watches", "Edit Image"))

        binding.rvEditShop.adapter = AdapterEditShop(list)
    }
}