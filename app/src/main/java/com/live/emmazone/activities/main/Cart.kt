package com.live.emmazone.activities.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.live.emmazone.R
import com.live.emmazone.activities.listeners.OnItemClick
import com.live.emmazone.activities.TermsCondition
import com.live.emmazone.adapter.AdapterCart
import com.live.emmazone.adapter.AdapterShopDetailProducts
import com.live.emmazone.databinding.ActivityCartBinding
import com.live.emmazone.model.ModelCart
import com.live.emmazone.model.ModelShopDetailProducts

class Cart : AppCompatActivity(), OnItemClick {
    lateinit var binding: ActivityCartBinding
    lateinit var adapter: AdapterCart
    val list = ArrayList<ModelCart>()
    val listMayLike = ArrayList<ModelShopDetailProducts>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

               binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.btnBuyNow.setOnClickListener {
            val dialog = BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
            val view = layoutInflater.inflate(R.layout.activity_bottom_sheet_dialog, null)

            val tvChangeDeliveryAdd = view.findViewById<TextView>(R.id.tvChange)
            val tvChangePaymentMethod = view.findViewById<TextView>(R.id.tvPaymentMethodChange)
            val tvTerms = view.findViewById<TextView>(R.id.btnTerms)
            val buy = view.findViewById<TextView>(R.id.btnBuy)

            tvTerms.setOnClickListener {
                val intent = Intent(this, TermsCondition::class.java)
                startActivity(intent)
            }

            buy.setOnClickListener {
               val alertDialog = AlertDialog.Builder(this)
                val factory = LayoutInflater.from(this)
                val view : View = factory.inflate(R.layout.dialog_order_placed, null)

                val dialogOrderPlaced = view.findViewById<Button>(R.id.done)

                dialogOrderPlaced.setOnClickListener {
                    onBackPressed()
                }

                alertDialog.setView(view)
                alertDialog.show()
                alertDialog.setCancelable(true)

            }

            tvChangeDeliveryAdd.setOnClickListener {
                val intent = Intent(this, DeliveryAddress::class.java)
                startActivity(intent)
            }

            tvChangePaymentMethod.setOnClickListener {
                val intent = Intent(this, PaymentMethod::class.java)
                startActivity(intent)
            }

            dialog.setCancelable(true)
            dialog.setContentView(view)
            dialog.show()

        }

        binding.recyclerCart.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerCartMayLike.layoutManager = GridLayoutManager(this, 2)

        list.add(ModelCart(R.drawable.shoes2, R.drawable.bin, "Brend Shoes",
            "30.00€"))
        list.add(ModelCart(R.drawable.shoes2, R.drawable.bin, "Brend Shoes",
            "30.00€"))

        binding.recyclerCart.adapter = AdapterCart(list)

        listMayLike.add(
            ModelShopDetailProducts(
                R.drawable.shoe_bernd, "Bernd", "30.00€",
                "Lorem ipsum dolor",
                "4.8", "Delivery estimate 4-5 days"
            )
        )

        listMayLike.add(
            ModelShopDetailProducts(
                R.drawable.shoes2, "Matrix", "30.00€",
                "Lorem ipsum dolor",
                "4.8", "Delivery estimate 4-5 days"
            )
        )
        binding.recyclerCartMayLike.adapter = AdapterShopDetailProducts(listMayLike, this)
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
}