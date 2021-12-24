package com.live.emmazone.activities.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.live.emmazone.R
import com.live.emmazone.activities.TermsCondition
import com.live.emmazone.databinding.ActivityProductDetailBinding
import com.live.emmazone.utils.Constants
import com.live.emmazone.utils.helper.getProfileType

class ProductDetailActivity : AppCompatActivity()
{
    lateinit var binding : ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnBuyDeliver.setOnClickListener {

            if (getProfileType() == Constants.GUEST){

                return@setOnClickListener
            }
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

        binding.btnClickCollect.setOnClickListener {
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




        binding.imageAskExpert.setOnClickListener {
            val intent = Intent(this, Message::class.java)
            startActivity(intent)
        }

        binding.imageCart.setOnClickListener {
            val intent = Intent(this, Cart::class.java)
            startActivity(intent)
        }

        binding.imageCart.setOnClickListener {
            val intent = Intent(this, Cart::class.java)
            startActivity(intent)
        }
        binding.back.setOnClickListener {
            onBackPressed()
        }

    }
}