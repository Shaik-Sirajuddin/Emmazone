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
import com.live.emmazone.R
import com.live.emmazone.activities.auth.LoginActivity
import com.live.emmazone.activities.listeners.OnItemClick
import com.live.emmazone.adapter.AdapterShopDetailProducts
import com.live.emmazone.databinding.ActivitySearchBinding
import com.live.emmazone.model.ModelShopDetailProducts
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.response_model.SellerShopDetailResponse
import com.live.emmazone.response_model.ShopDetailResponse

class SearchActivity : AppCompatActivity(), OnItemClick {
    lateinit var binding: ActivitySearchBinding
    val list = ArrayList<SellerShopDetailResponse.Body.ShopDetails.Product>()
    lateinit var adapter: AdapterShopDetailProducts

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.cart.setOnClickListener {
            if ( getPreference(AppConstants.PROFILE_TYPE,"") == AppConstants.GUEST) {
                showLoginDialog()
                return@setOnClickListener
            }
            val intent = Intent(this, Cart::class.java)
            startActivity(intent)
        }

        binding.imageNotifications.setOnClickListener {
            if ( getPreference(AppConstants.PROFILE_TYPE,"") == AppConstants.GUEST) {
                showLoginDialog()
                return@setOnClickListener
            }
            val intent = Intent(this, Notifications::class.java)
            startActivity(intent)
        }

        binding.imageFilterHome.setOnClickListener {
            onBackPressed()
        }

        binding.imageBack.setOnClickListener {
            onBackPressed()
        }

        binding.recyclerSearch.layoutManager = GridLayoutManager(this, 2)

       /* list.add(
            ModelShopDetailProducts(
                R.drawable.bike2, "Bernd", "30.00€", "Lorem ipsum dolor",
                "4.8", "Delivery estimate 4-5 days"
            )
        )

        list.add(
            ModelShopDetailProducts(
                R.drawable.bike1, "Matrix", "30.00€", "Lorem ipsum dolor",
                "4.8", "Delivery estimate 4-5 days"
            )
        )

        list.add(
            ModelShopDetailProducts(
                R.drawable.bike2, "Bernd", "30.00€", "Lorem ipsum dolor",
                "4.8", "Delivery estimate 4-5 days"
            )
        )

        list.add(
            ModelShopDetailProducts(
                R.drawable.bike3, "Matrix", "30.00€", "Lorem ipsum dolor",
                "4.8", "Delivery estimate 4-5 days"
            )
        )*/

        binding.recyclerSearch.adapter = AdapterShopDetailProducts(this,list, this)

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