package com.live.emmazone.activities.main

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.activities.auth.LoginActivity
import com.live.emmazone.activities.listeners.OnItemClick
import com.live.emmazone.adapter.AdapterShopDetailCategory
import com.live.emmazone.adapter.AdapterShopDetailProducts
import com.live.emmazone.databinding.ActivityShopDetailBinding
import com.live.emmazone.model.ModelShopDetailCategory
import com.live.emmazone.model.ModelShopDetailProducts
import com.live.emmazone.utils.Constants
import com.live.emmazone.utils.helper.getProfileType

class ShopDetailActivity : AppCompatActivity(), OnItemClick {
    lateinit var binding: ActivityShopDetailBinding
    var list = ArrayList<ModelShopDetailCategory>()
    var listSDProduct = ArrayList<ModelShopDetailProducts>()
    lateinit var adapter: AdapterShopDetailCategory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.imageCart.setOnClickListener {
            if (getProfileType() == Constants.GUEST) {
                showLoginDialog()
                return@setOnClickListener
            }
            val intent = Intent(this, Cart::class.java)
            startActivity(intent)
        }

        binding.imageAskExpert.setOnClickListener {
            if (getProfileType() == Constants.GUEST) {
                showLoginDialog()
                return@setOnClickListener
            }
            val intent = Intent(this, Message::class.java)
            startActivity(intent)
        }

        binding.recyclerShopDetailCategory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerShopDetailProducts.layoutManager = GridLayoutManager(this, 2)

        list.add(ModelShopDetailCategory(R.drawable.all, "All"))
        list.add(ModelShopDetailCategory(R.drawable.shoe, "goggle"))
        list.add(ModelShopDetailCategory(R.drawable.google, "TimePiece"))
        list.add(ModelShopDetailCategory(R.drawable.time, "T Shirts"))
        list.add(ModelShopDetailCategory(R.drawable.tshiert, "goggle"))

        binding.recyclerShopDetailCategory.adapter = AdapterShopDetailCategory(list)

        listSDProduct.add(
            ModelShopDetailProducts(
                R.drawable.shoe_bernd, "Bernd",
                "30.00€", "Lorem ipsum dolor",
                "4.8", "Delivery estimate 4-5 days"
            )
        )

        listSDProduct.add(
            ModelShopDetailProducts(
                R.drawable.shoes2, "Matrix",
                "30.00€", "Lorem ipsum dolor",
                "4.8", "Delivery estimate 4-5 days"
            )
        )

        listSDProduct.add(
            ModelShopDetailProducts(
                R.drawable.shoe_bernd, "Bernd",
                "30.00€", "Lorem ipsum dolor",
                "4.8", "Delivery estimate 4-5 days"
            )
        )

        listSDProduct.add(
            ModelShopDetailProducts(
                R.drawable.shoes2, "Matrix",
                "30.00€", "Lorem ipsum dolor",
                "4.8", "Delivery estimate 4-5 days"
            )
        )

        binding.recyclerShopDetailProducts.adapter = AdapterShopDetailProducts(listSDProduct, this)
    }

    override fun onCellClickListener() {
        val intent = Intent(this, ProductDetailActivity::class.java)
        startActivity(intent)
    }

    override fun onClick() {
        TODO("Not yet implemented")
    }

    override fun onClickPickCollect() {
        TODO("Not yet implemented")
    }

    override fun onOrderCancelled() {
        TODO("Not yet implemented")
    }

    private fun showLoginDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)

        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,
        )
        dialog.setContentView(R.layout.dialog_login)
        //dialog.window?.setBackgroundDrawable(ContextCompat.getDrawable(this, android.R.color.transparent))

        val imgCross = dialog.findViewById<ImageView>(R.id.cross)
        val btnLogin = dialog.findViewById<Button>(R.id.btnLogin)

        imgCross.setOnClickListener {
            dialog.dismiss()
        }

        btnLogin.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        dialog.show()

    }

}