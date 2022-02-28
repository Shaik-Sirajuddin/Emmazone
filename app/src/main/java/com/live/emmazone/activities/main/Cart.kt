package com.live.emmazone.activities.main

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.live.emmazone.R
import com.live.emmazone.activities.TermsCondition
import com.live.emmazone.activities.listeners.OnItemClick
import com.live.emmazone.adapter.AdapterCart
import com.live.emmazone.adapter.AdapterShopDetailProducts
import com.live.emmazone.adapter.ImageSliderCustomeAdapter
import com.live.emmazone.databinding.ActivityCartBinding
import com.live.emmazone.model.CartResponsModel
import com.live.emmazone.model.ModelCart
import com.live.emmazone.model.ModelShopDetailProducts
import com.live.emmazone.model.ShopProductDetailResponse
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.response_model.ShopDetailResponse
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.DateHelper
import com.live.emmazone.view_models.AppViewModel
import java.util.*
import kotlin.collections.ArrayList

class Cart : AppCompatActivity(), OnItemClick, Observer<RestObservable> {
    lateinit var binding: ActivityCartBinding
    lateinit var adapter: AdapterCart
    val list = ArrayList<CartResponsModel.Body.CartItem>()
    val listMayLike = ArrayList<ShopDetailResponse.Body.Product>()
    private var selectedDate: Date? = null
    var tvDeliveryDate: TextView? = null
    var adapterPosition = 0
    private val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        getCartListing()

        binding.btnBuyNow.setOnClickListener { showBottomDialog() }

        binding.recyclerCart.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerCartMayLike.layoutManager = GridLayoutManager(this, 2)

        adapter = AdapterCart(this, list, this)
        binding.recyclerCart.adapter = adapter

        binding.recyclerCartMayLike.adapter = AdapterShopDetailProducts(this, listMayLike, this)
    }

    private fun getCartListing() {
        appViewModel.cartListing(this, true)
        appViewModel.getResponse().observe(this, this)
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

    private fun showBottomDialog() {
        val dialog = BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.activity_bottom_sheet_dialog, null)

        tvDeliveryDate = view.findViewById(R.id.tvDeliveryDate)
        val tvChangeDateTime = view.findViewById<TextView>(R.id.tvChangeDateTime)
        val tvChangeDeliveryAdd = view.findViewById<TextView>(R.id.tvChange)
        val tvChangePaymentMethod = view.findViewById<TextView>(R.id.tvPaymentMethodChange)
        val tvTerms = view.findViewById<TextView>(R.id.btnTerms)
        val buy = view.findViewById<TextView>(R.id.btnBuy)

        tvDeliveryDate?.text = DateHelper.getFormattedDate(Date())

        tvChangeDateTime.setOnClickListener { openDateTimerPicker() }

        tvTerms.setOnClickListener {
            val intent = Intent(this, TermsCondition::class.java)
            startActivity(intent)
        }
        buy.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            val factory = LayoutInflater.from(this)
            val view: View = factory.inflate(R.layout.dialog_order_placed, null)

            val dialogOrderPlaced = view.findViewById<Button>(R.id.done)

            dialogOrderPlaced.setOnClickListener {
                onBackPressed()
            }
            alertDialog.setCancelable(true)

            alertDialog.setView(view)
            alertDialog.show()
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

    private fun openDateTimerPicker() {
        val c = Calendar.getInstance()
        if (selectedDate != null)
            c.time = selectedDate!!

        val year = c[Calendar.YEAR]
        val month = c[Calendar.MONTH]
        val day = c[Calendar.DAY_OF_MONTH]

        val dpd = DatePickerDialog(
            this, { _, sYear, sMonth, sDay ->
                run {
                    selectedDate = DateHelper.getDate(sDay, sMonth, sYear)
                    showTimePicker()
                }
            },
            year,
            month,
            day
        )
        dpd.datePicker.minDate = Calendar.getInstance().timeInMillis
        dpd.show()
    }

    private fun showTimePicker() {
        val now = Calendar.getInstance()
        if (selectedDate != null)
            now.time = selectedDate!!

        val hour = now[Calendar.HOUR_OF_DAY]
        val minute = now[Calendar.MINUTE]

        val mTimePicker = TimePickerDialog(
            this, { _, sHour, sMinute ->
                kotlin.run {
                    val date: Date? = DateHelper.getDate(sHour, sMinute)
                    selectedDate = DateHelper.combineDateTime(selectedDate!!, date!!)
                    tvDeliveryDate?.text = DateHelper.getFormattedDate(selectedDate!!)
                }
            }, hour, minute, false
        )
        mTimePicker.setTitle("Select Time")
        mTimePicker.show()
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is CartResponsModel) {
                    t.data.body.apply {
                        binding.tvSubTotalPrice.text = this.subTotal.toDouble().toString()
                        binding.tvDeliveryChargesPrice.text =
                            this.deliveryCharge.toDouble().toString()
                        binding.tvTaxPrice.text = this.tax.toString() + "%"
                        binding.tvTotalPrice.text = this.total.toDouble().toString()
                        list.addAll(t.data.body.cartItems)
                        adapter.notifyDataSetChanged()
                        noDataVisible()
                    }


                } else if (t.data is CommonResponse) {
                    list.removeAt(adapterPosition)
                    adapter.notifyDataSetChanged()
                    noDataVisible()
                }
            }
        }
    }

    fun deleteCartItem(position: Int, id: String) {
        adapterPosition = position
        appViewModel.deleteCartItem(this, true, id)
    }

    private fun noDataVisible() {
        if (list.isEmpty()) {
            binding.tvNoData.visibility = View.VISIBLE
            binding.clData.visibility = View.GONE
        } else {
            binding.tvNoData.visibility = View.GONE
            binding.clData.visibility = View.VISIBLE
        }
    }

}