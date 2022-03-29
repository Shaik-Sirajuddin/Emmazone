package com.live.emmazone.activities.main

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.live.emmazone.R
import com.live.emmazone.activities.TermsCondition
import com.live.emmazone.activities.auth.LoginActivity
import com.live.emmazone.adapter.ImageSliderCustomeAdapter
import com.live.emmazone.base.AppController
import com.live.emmazone.databinding.ActivityProductDetailBinding
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.interfaces.OnPopupClick
import com.live.emmazone.model.ShopProductDetailResponse
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.AddOrderResponse
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.DateHelper
import com.live.emmazone.view_models.AppViewModel
import java.text.DecimalFormat
import java.util.*


class ProductDetailActivity : AppCompatActivity(), Observer<RestObservable>, OnPopupClick,
    View.OnClickListener {

    lateinit var binding: ActivityProductDetailBinding

    private var selectedDate: Date? = null

    private val appViewModel: AppViewModel by viewModels()
    var qty = 0
    var totalQty = 0

    var productId = ""
    private var selectedPaymentType = "" // 0=>Wallet 1=>Card 2=>cash
    private var selectedCardId = ""
    private var selectedCardCvv = ""

    private var tvDeliveryDate: TextView? = null
    private var tvOrderPersonName: TextView? = null
    private var tvOrderDeliveryAddress: TextView? = null
    private var tvSelectAddress: TextView? = null
    private var tvSelectPayment: TextView? = null
    private var textPaymentMethod: TextView? = null
    private var imagePaymentMethod: ImageView? = null
    private var llPaymentMethod: LinearLayout? = null
    private var rlAddress: RelativeLayout? = null

    private var shopProductDetailResponse: ShopProductDetailResponse? = null
    val hashMap = HashMap<String, String>()

    private val launcherPayment =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == RESULT_OK) {
                llPaymentMethod?.visibility = View.VISIBLE
                tvSelectPayment?.visibility = View.GONE

                val data = result.data
                val paymentType = data?.getStringExtra("paymentType")
                selectedCardId = data?.getStringExtra("cardId")!!
                selectedCardCvv = data.getStringExtra("cardCVV")!!
                selectedPaymentType = paymentType!!

                if (paymentType == "0") {
//                    imagePaymentMethod?.setImageResource(R.drawable.wallet)
                    textPaymentMethod?.text = getString(R.string.wallet)
                } else if (paymentType == "1") {
//                    imagePaymentMethod?.setImageResource(R.drawable.credit)
                    textPaymentMethod?.text = getString(R.string.credit_card_debit_card)
                } else if (paymentType == "2") {
//                    imagePaymentMethod?.setImageResource(R.drawable.wallet)
                    textPaymentMethod?.text = getString(R.string.cash_on_delivery)
                } else if (paymentType == "3") {
//                    imagePaymentMethod?.setImageResource(R.drawable.paypal)
                    textPaymentMethod?.text = getString(R.string.paypal)
                }


            }

        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        qty = binding.tvCount.text.toString().toInt()
        if (intent.getStringExtra("productId") != null) {
            productId = intent.getStringExtra("productId")!!

        }

        setOnClicks()




        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.imageCart.setOnClickListener {
            startActivity(Intent(this, Cart::class.java))
        }


    }

    private fun productDetailApiHit() {
        val hashMap = HashMap<String, String>()
        hashMap["id"] = productId!!

        appViewModel.shopProductDetailApi(this, true, hashMap)
        appViewModel.getResponse().observe(this, this)


    }

    private fun setOnClicks() {
        binding.ivPlus.setOnClickListener(this)
        binding.ivMinus.setOnClickListener(this)
        binding.btnBuyDeliver.setOnClickListener(this)
        binding.btnClickCollect.setOnClickListener(this)
        binding.imageAskExpert.setOnClickListener(this)

    }

    private fun addToCart() {
        val hashMap = HashMap<String, String>()
        hashMap["productId"] = productId
        hashMap["qty"] = binding.tvCount.text.toString()
        appViewModel.addToCart(this, true, hashMap)

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
        val tvDeliveryCharges = view.findViewById<TextView>(R.id.tvDeliveryCharges)
        val tvDeliveryChargesPrice = view.findViewById<TextView>(R.id.tvDeliveryChargesPrice)
        val tvTaxPrice = view.findViewById<TextView>(R.id.tvTaxPrice)
        val tvTotalPrice = view.findViewById<TextView>(R.id.tvTotalPrice)
        val tvDeliveryAddress = view.findViewById<TextView>(R.id.tvDeliveryAddress)
        tvSelectAddress = view.findViewById<TextView>(R.id.tvSelectAddress)
        tvSelectPayment = view.findViewById<TextView>(R.id.tvSelectPayment)
        tvOrderPersonName = view.findViewById(R.id.tvOrderPersonName)
        tvOrderDeliveryAddress = view.findViewById(R.id.tvOrderDeliveryAddress)
        textPaymentMethod = view.findViewById(R.id.textPaymentMethod)
        imagePaymentMethod = view.findViewById(R.id.imagePaymentMethod)
        llPaymentMethod = view.findViewById(R.id.paymentMethodLayout)
        rlAddress = view.findViewById(R.id.rlAddress)

        tvDeliveryChargesPrice.visibility = View.GONE
        tvDeliveryCharges.visibility = View.GONE
        tvDeliveryAddress.visibility = View.GONE
        tvSelectAddress?.visibility = View.GONE

        val subTotal = shopProductDetailResponse!!.body.productPrice.toFloat() * qty
        val tax = shopProductDetailResponse!!.body.taxValue!!.value.toInt()
        val taxCharged = ((subTotal / 100) * tax)
        val totalPrice = taxCharged + subTotal

        tvCountItem.text = "1"
        tvSubTotalPrice.text = DecimalFormat("##.##").format(subTotal.toDouble())
        tvTaxPrice.text = taxCharged.toString()
        tvTotalPrice.text = DecimalFormat("##.##").format(totalPrice.toDouble())


        val milliSeconds = System.currentTimeMillis() + 604800000L //after 7 days
        tvDeliveryDate?.text = AppUtils.milliSecondsToTime(milliSeconds, AppConstants.DATE_FORMAT)

        /*tvChangeDateTime.setOnClickListener { openDateTimerPicker() }*/

        getSavedPaymentType()

        tvTerms.setOnClickListener {
            val intent = Intent(this, TermsCondition::class.java)
            startActivity(intent)
        }
        buy.setOnClickListener {
            validateData()
        }

//        tvSelectAddress?.setOnClickListener {
//            val intent = Intent(this, DeliveryAddress::class.java)
//            launcherAddress.launch(intent)
//        }

//        tvChangeDeliveryAdd.setOnClickListener {
//            val intent = Intent(this, DeliveryAddress::class.java)
//            launcherAddress.launch(intent)
//        }

        tvSelectPayment?.setOnClickListener {
            val intent = Intent(this, PaymentMethod::class.java)
            launcherPayment.launch(intent)
        }

        tvChangePaymentMethod.setOnClickListener {
            val intent = Intent(this, PaymentMethod::class.java)
            launcherPayment.launch(intent)
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


    private fun validateData() {

        if (Validator.buyProduct(selectedPaymentType)) {
            if (selectedPaymentType == "1") {
                if (TextUtils.isEmpty(selectedCardId)) {
                    Validator.errorMessage =
                        AppController.instance!!.getString(R.string.please_select_card)
                    return
                }
                hashMap["cardId"] = selectedCardId
                hashMap["cvv"] = selectedCardCvv
                addOderApi()

            } else {
                addOderApi()
            }
        } else {
            AppUtils.showMsgOnlyWithoutClick(this, Validator.errorMessage)
        }
    }


    private fun addOderApi() {
        hashMap["deliveryType"] = "0"  //0=>click&collect 1=>lifernado 2=>ownDelivery
        hashMap["paymentMethod"] = selectedPaymentType  //0=>Wallet 1=>Card 2=>cash
        hashMap["productId"] = shopProductDetailResponse!!.body.id.toString()
        hashMap["qty"] = qty.toString()
        appViewModel.addOrderApi(this, true, hashMap)
        appViewModel.getResponse().observe(this, this)
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is ShopProductDetailResponse) {
                    shopProductDetailResponse = t.data
                    val model = t.data.body
                    binding.productItemName.text = model.name
                    try {
                        binding.ratingBarProductDetail.rating = model.productReview.toFloat()
                    } catch (e: Exception) {
                    }

                    binding.tvShopDetailProductText.text =
                        "${binding.ratingBarProductDetail.rating}/5"
                    binding.tvDesc.text = model.shortDescription
                    binding.tvDelivery.text = model.description
                    binding.tvSize.text = model.productSize.size
                    binding.tvColor.text = model.productColor.color
                    binding.tvQty.text = getString(R.string.of, model.productQuantity.toString())
                    totalQty = model.productQuantity

                    binding.itemImageProductDetail.setAdapter(
                        ImageSliderCustomeAdapter(
                            this@ProductDetailActivity,
                            model.productImages
                        )
                    )
                    binding.indicatorProduct.setViewPager(binding.itemImageProductDetail)


                    binding.tvPriceInteger.text = "${model.productPrice} €"

                    if (model.productQuantity == 0) {
                        binding.tvOutOfStock.visibility = View.VISIBLE
                        binding.btnBuyDeliver.visibility = View.GONE
                        binding.btnClickCollect.visibility = View.GONE
                    } else {
                        binding.tvOutOfStock.visibility = View.GONE
                        binding.btnBuyDeliver.visibility = View.VISIBLE
                        binding.btnClickCollect.visibility = View.VISIBLE
                    }


                    if (model.cartCount == 0) {
                        binding.ivRedCart.visibility = View.GONE
                    } else {
                        binding.ivRedCart.visibility = View.VISIBLE
                    }

                } else if (t.data is CommonResponse) {
                    AppUtils.showMsgOnlyWithClick(this, "Item add to cart successfully", this)
                } else if (t.data is AddOrderResponse) {
                    val response: AddOrderResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {
                        thankYouDialog()
                    }
                }
            }
        }

    }


    private fun thankYouDialog() {
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

    private fun getSavedPaymentType() {
        selectedCardId = getPreference(AppConstants.SAVED_CARD_ID, "")
        selectedCardCvv = getPreference(AppConstants.SAVED_CVV, "")
        selectedPaymentType = getPreference(AppConstants.PAYMENT_TYPE, "")

        if (selectedPaymentType.isNotEmpty()) {
            llPaymentMethod?.visibility = View.VISIBLE
            tvSelectPayment?.visibility = View.GONE
            if (selectedPaymentType == "0") {
//                    imagePaymentMethod?.setImageResource(R.drawable.wallet)
                textPaymentMethod?.text = getString(R.string.wallet)
            } else if (selectedPaymentType == "1") {
//                    imagePaymentMethod?.setImageResource(R.drawable.credit)
                textPaymentMethod?.text = getString(R.string.credit_card_debit_card)
            } else if (selectedPaymentType == "2") {
//                    imagePaymentMethod?.setImageResource(R.drawable.wallet)
                textPaymentMethod?.text = getString(R.string.cash_on_delivery)
            } else if (selectedPaymentType == "3") {
//                    imagePaymentMethod?.setImageResource(R.drawable.paypal)
                textPaymentMethod?.text = getString(R.string.paypal)
            }
        }

    }

    override fun onPopupClickListener() {
        startActivity(Intent(this, Cart::class.java))

    }


    override fun onClick(v: View?) {
        when (v?.id!!) {
            R.id.imageAskExpert -> {
                if (getPreference(AppConstants.PROFILE_TYPE, "") == AppConstants.GUEST) {
                    showLoginDialog()
                    return
                }
                val intent = Intent(this, Message::class.java)
                startActivity(intent)
            }
            R.id.btnBuyDeliver -> {
                if (qty > 0) {
                    addToCart()
                } else {
                    AppUtils.showMsgOnlyWithoutClick(this, "Please select atleast one item")
                }
            }
            R.id.btnClickCollect -> {
                showBottomDialog()
            }
            R.id.ivPlus -> {
                if (qty < totalQty) {
                    qty += 1
                    binding.tvCount.text = qty.toString()
                }
            }
            R.id.ivMinus -> {
                if (qty > 1) {
                    qty -= 1
                    binding.tvCount.text = qty.toString()
                }

            }

        }
    }


    override fun onResume() {
        super.onResume()

        productDetailApiHit()
    }
}