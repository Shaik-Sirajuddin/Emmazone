package com.live.emmazone.activities.main

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.live.emmazone.R
import com.live.emmazone.activities.TermsCondition
import com.live.emmazone.adapter.AdapterCart
import com.live.emmazone.adapter.YouMyLikeProductAdapter
import com.live.emmazone.databinding.ActivityCartBinding
import com.live.emmazone.model.CartResponsModel
import com.live.emmazone.net.CartUpdateResponse
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.DateHelper
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.toast
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Cart : AppCompatActivity(), Observer<RestObservable> {

    lateinit var binding: ActivityCartBinding
    lateinit var cartAdapter: AdapterCart
    lateinit var youMayLikeProductAdapter: YouMyLikeProductAdapter
    val list = ArrayList<CartResponsModel.Body.CartItem>()
    val listMayLike = ArrayList<CartResponsModel.Body.CartItem.Product>()
    private var selectedDate: Date? = null
    var tvDeliveryDate: TextView? = null
    var adapterPosition: Int? = null
    var response: CartResponsModel? = null
    private val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCartListing()
        setCartAdapter()
        setLikeProductAdapter()
        clicksHandle()

    }

    private fun clicksHandle() {
        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.btnBuyNow.setOnClickListener {
            showBottomDialog()
        }
    }

    private fun setCartAdapter() {
        cartAdapter = AdapterCart(this, list)
        binding.recyclerCart.adapter = cartAdapter

        cartAdapter.onDeleteClick = { pos ->
            adapterPosition = pos
            deleteCartItem(list[pos].id.toString())
        }

        cartAdapter.onPlusMinusClick = { pos, clickOn ->
            if (clickOn == "minus") {
                if (list[pos].qty > 1) {
                    list[pos].qty = list[pos].qty - 1
                    cartAdapter.notifyDataSetChanged()

                    updateCartItem(list[pos].id.toString(), list[pos].qty.toString())
                }
            } else if (clickOn == "plus") {
                if (list[pos].qty < list[pos].product.productQuantity) {
                    list[pos].qty = list[pos].qty + 1
                    cartAdapter.notifyDataSetChanged()

                    updateCartItem(list[pos].id.toString(), list[pos].qty.toString())
                }
            }
        }
    }

    private fun updateCartItem(cartId: String, qty: String) {
        val hashMap = HashMap<String, String>()
        hashMap["id"] = cartId
        hashMap["qty"] = qty

        appViewModel.cartUpdateApi(this, true, hashMap)
    }

    private fun setLikeProductAdapter() {
        youMayLikeProductAdapter = YouMyLikeProductAdapter(this, listMayLike)
        binding.recyclerCartMayLike.adapter = youMayLikeProductAdapter
    }

    private fun getCartListing() {
        appViewModel.cartListing(this, true)
        appViewModel.getResponse().observe(this, this)
    }


    private fun showBottomDialog() {
        val dialog = BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.activity_bottom_sheet_dialog, null)
        dialog.setCancelable(true)
        dialog.setContentView(view)

        tvDeliveryDate = view.findViewById(R.id.tvDeliveryDate)
        val tvChangeDateTime = view.findViewById<TextView>(R.id.tvChangeDateTime)
        val tvChangeDeliveryAdd = view.findViewById<TextView>(R.id.tvChange)
        val tvChangePaymentMethod = view.findViewById<TextView>(R.id.tvPaymentMethodChange)
        val tvTerms = view.findViewById<TextView>(R.id.btnTerms)
        val buy = view.findViewById<TextView>(R.id.btnBuy)
        val tvCountItem = view.findViewById<TextView>(R.id.tvItemCount)
        val tvSubTotalPrice = view.findViewById<TextView>(R.id.tvSubTotalPrice)
        val tvDeliveryChargesPrice = view.findViewById<TextView>(R.id.tvDeliveryChargesPrice)
        val tvTaxPrice = view.findViewById<TextView>(R.id.tvTaxPrice)
        val tvTotalPrice = view.findViewById<TextView>(R.id.tvTotalPrice)

        tvCountItem.text = response!!.body.cartItems.size.toString()
        tvSubTotalPrice.text =
            getString(R.string.euro_symbol, response!!.body.subTotal.toDouble().toString())
        tvDeliveryChargesPrice.text =
            getString(
                R.string.euro_symbol,
                response!!.body.deliveryCharge.toDouble().toString()
            )
        tvTaxPrice.text = response!!.body.tax.toString() + "%"
        tvTotalPrice.text =
            getString(R.string.euro_symbol, response!!.body.total.toDouble().toString())


        tvDeliveryDate?.text = DateHelper.getFormattedDate(Date())

        tvChangeDateTime.setOnClickListener { openDateTimerPicker() }

        tvTerms.setOnClickListener {
            val intent = Intent(this, TermsCondition::class.java)
            startActivity(intent)
        }
        buy.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            val factory = LayoutInflater.from(this)
            val placeOrder: View = factory.inflate(R.layout.dialog_order_placed, null)

            val dialogOrderPlaced = placeOrder.findViewById<Button>(R.id.done)

            dialogOrderPlaced.setOnClickListener {
                onBackPressed()
            }
            alertDialog.setCancelable(true)

            alertDialog.setView(placeOrder)
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
                    response = t.data
                        list.clear()
                        list.addAll(response!!.body.cartItems)
                        cartAdapter.notifyDataSetChanged()

                        binding.tvSubTotalPrice.text =
                            getString(R.string.euro_symbol, response!!.body.subTotal.toDouble().toString())
                        binding.tvDeliveryChargesPrice.text =
                            getString(
                                R.string.euro_symbol,
                                response!!.body.deliveryCharge.toDouble().toString()
                            )
                        binding.tvTaxPrice.text = response!!.body.tax.toString() + "%"
                        binding.tvTotalPrice.text =
                            getString(R.string.euro_symbol, response!!.body.total.toDouble().toString())
                        binding.tvItemCount.text = response!!.body.cartItems.size.toString()



                        for (i in 0 until response!!.body.youMayLikeProducts.size) {
                            response!!.body.youMayLikeProducts[i].apply {
                                listMayLike.add(
                                    CartResponsModel.Body.CartItem.Product(
                                        this.categoryColorId,
                                        this.categoryId,
                                        this.categorySizeId,
                                        this.created,
                                        this.createdAt,
                                        this.description,
                                        this.id,
                                        this.mainImage,
                                        this.name,
                                        this.productHighlight,
                                        this.productPrice,
                                        this.productQuantity,
                                        this.productReview,
                                        this.status,
                                        this.userId
                                    )
                                )

                            }
                        }
                        noDataVisible()



                } else if (t.data is CommonResponse) {
                    list.removeAt(adapterPosition!!)
                    cartAdapter.notifyDataSetChanged()
                    noDataVisible()
                } else if (t.data is CartUpdateResponse) {
                    val response: CartUpdateResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {
                        toast(response.message)
                        getCartListing()
                    }

                }
            }
        }
    }

    private fun deleteCartItem(id: String) {
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