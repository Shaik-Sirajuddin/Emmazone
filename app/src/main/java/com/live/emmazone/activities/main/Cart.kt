package com.live.emmazone.activities.main

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.live.emmazone.MainActivity
import com.live.emmazone.R
import com.live.emmazone.activities.listeners.OnItemClick
import com.live.emmazone.activities.TermsCondition
import com.live.emmazone.adapter.AdapterCart
import com.live.emmazone.adapter.AdapterShopDetailProducts
import com.live.emmazone.databinding.ActivityCartBinding
import com.live.emmazone.model.ModelCart
import com.live.emmazone.model.ModelShopDetailProducts
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

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
            val tvChangeDateTime = view.findViewById<TextView>(R.id.tvChangeDateTime)
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
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }

                alertDialog.setView(view)
                alertDialog.show()
                alertDialog.setCancelable(true)

            }

//            tvChangeDateTime.setOnClickListener {
//
//                var day = 0
//                var month: Int = 0
//                var year: Int = 0
//                var minute: Int = 0
//                var myDay = 0
//                var myMonth: Int = 0
//                var myYear: Int = 0
//                var myHour: Int = 0
//                var myMinute: Int = 0
//                var hour: Int = 0
//
//                val calendar: Calendar = Calendar.getInstance()
//                day = calendar.get(Calendar.DAY_OF_MONTH)
//                month = calendar.get(Calendar.MONTH)
//                year = calendar.get(Calendar.YEAR)
//                val datePickerDialog =
//                    DatePickerDialog(this, this, year, month,day)
//                datePickerDialog.show()
//
//                fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
//                    myDay = day
//                    myYear = year
//                    myMonth = month
//                    val calendar: Calendar = Calendar.getInstance()
//                    hour = calendar.get(Calendar.HOUR)
//                    minute = calendar.get(Calendar.MINUTE)
//                    val timePickerDialog = TimePickerDialog(this, this, hour, minute,
//                        DateFormat.is24HourFormat(this))
//                    timePickerDialog.show()
//                }
//                 fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
//                    myHour = hourOfDay
//                    myMinute = minute
//                     tvChangeDateTime.text = "Year: " + myYear + "\n" + "Month: " + myMonth + "\n" + "Day: " + myDay + "\n" + "Hour: " + myHour + "\n" + "Minute: " + myMinute
//                }
//
//
//            }

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