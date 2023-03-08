package com.live.emmazone.activities.main

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.live.emmazone.MainActivity
import com.live.emmazone.R
import com.live.emmazone.activities.TermsCondition
import com.live.emmazone.adapter.AdapterCart
import com.live.emmazone.adapter.YouMyLikeProductAdapter
import com.live.emmazone.base.AppController
import com.live.emmazone.databinding.ActivityCartBinding
import com.live.emmazone.extensionfuncton.*
import com.live.emmazone.model.CartResponsModel
import com.live.emmazone.net.CartUpdateResponse
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.AddOrderResponse
import com.live.emmazone.response_model.AddressListResponse
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.response_model.SearchProductResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.AppUtils.Companion.showToast
import com.live.emmazone.utils.DateHelper
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.toast
import java.text.DecimalFormat
import java.util.*

class Cart : AppCompatActivity(), Observer<RestObservable> {

    private var dialog: BottomSheetDialog? = null
    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: AdapterCart
    private lateinit var youMayLikeProductAdapter: YouMyLikeProductAdapter
    private val list = ArrayList<CartResponsModel.Body.CartItem>()
    private val listMayLike = ArrayList<CartResponsModel.Body.CartItem.Product>()
    private var selectedDate: Date? = null
    private var tvDeliveryDate: TextView? = null
    private var tvOrderPersonName: TextView? = null
    private var tvOrderDeliveryAddress: TextView? = null
    private var tvSelectAddress: TextView? = null
    private var tvSelectPayment: TextView? = null
    private var textPaymentMethod: TextView? = null
    private var imagePaymentMethod: ImageView? = null
    private var llPaymentMethod: LinearLayout? = null
    private var rlAddress: RelativeLayout? = null
    private var adapterPosition: Int? = null
    private var response: CartResponsModel? = null
    private val appViewModel: AppViewModel by viewModels()

    private var selectedPaymentType = "" // 0=>Wallet 1=>Card 2=>cash
    private var selectedCardId = ""
    private var selectedCardCvv = ""
    private var selectedAddressId = ""
    val hashMap = HashMap<String, String>()
    private var deleteClick = false
    private var showDialog = false
    private val launcherAddress =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data =
                    result.data?.getSerializableExtra(AppConstants.Address_LIST_RESPONSE) as
                            AddressListResponse.Body

                showDialog = true
                savePrefObject(AppConstants.SAVED_ADDRESS_RESPONSE, data)
                selectedAddressId = data.id.toString()
                tvOrderPersonName?.text = data.name
                tvOrderDeliveryAddress?.text = data.address + " , ".plus(data.city)
                rlAddress?.visibility = View.VISIBLE
                tvSelectAddress?.visibility = View.GONE
                getCartListing()


            }
        }


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

                savePreference(AppConstants.PAYMENT_TYPE, paymentType)
                savePreference(AppConstants.SAVED_CARD_ID, selectedCardId)
                savePreference(AppConstants.SAVED_CVV, selectedCardCvv)

            }

        }


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
        val hashMap = HashMap<String, String>()
        val postalCode = getSavedZipCode()
        postalCode?.let {
            hashMap["postal_code"] = it
        }
        appViewModel.cartListing(hashMap, this, true)
        appViewModel.getResponse().observe(this, this)
    }


    private fun showBottomDialog() {
        dialog = BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.activity_bottom_sheet_dialog, null)
        dialog!!.setCancelable(true)
        dialog!!.setContentView(view)

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
        tvSelectAddress = view.findViewById<TextView>(R.id.tvSelectAddress)
        tvSelectPayment = view.findViewById<TextView>(R.id.tvSelectPayment)
        tvOrderPersonName = view.findViewById(R.id.tvOrderPersonName)
        tvOrderDeliveryAddress = view.findViewById(R.id.tvOrderDeliveryAddress)
        textPaymentMethod = view.findViewById(R.id.textPaymentMethod)
        imagePaymentMethod = view.findViewById(R.id.imagePaymentMethod)
        llPaymentMethod = view.findViewById(R.id.paymentMethodLayout)
        rlAddress = view.findViewById(R.id.rlAddress)

        tvCountItem.text = response!!.body.cartItems.size.toString()
        tvSubTotalPrice.text =
            getString(R.string.euro_symbol, AppUtils.getFormattedAmount(response!!.body.subTotal))
        tvDeliveryChargesPrice.text =
            getString(
                R.string.euro_symbol,
                response!!.body.deliveryCharge.toDouble().toString()
            )
        tvTaxPrice.text = response!!.body.tax.toString() + "%"
        tvTotalPrice.text =
            getString(R.string.euro_symbol, DecimalFormat("##.##").format(response!!.body.total))


        val milliSeconds = System.currentTimeMillis() + 604800000L //after 7 days
        tvDeliveryDate?.text = AppUtils.milliSecondsToTime(milliSeconds, AppConstants.DATE_FORMAT)
//        tvDeliveryDate?.text = DateHelper.getFormattedDate(Date())
        getSavedAddress()
        getSavedPaymentType()

        tvChangeDateTime.setOnClickListener { openDateTimerPicker() }

        tvTerms.setOnClickListener {
            val intent = Intent(this, TermsCondition::class.java)
            startActivity(intent)
        }
        buy.setOnClickListener {
            validateData()
//            thankYouDialog()
        }

        tvSelectAddress?.setOnClickListener {
            val intent = Intent(this, DeliveryAddress::class.java)
            launcherAddress.launch(intent)
        }

        tvChangeDeliveryAdd.setOnClickListener {
            val intent = Intent(this, DeliveryAddress::class.java)
            launcherAddress.launch(intent)
        }

        tvSelectPayment?.setOnClickListener {
            val intent = Intent(this, PaymentMethod::class.java)
            launcherPayment.launch(intent)
        }

        tvChangePaymentMethod.setOnClickListener {
            val intent = Intent(this, PaymentMethod::class.java)
            launcherPayment.launch(intent)
        }
        dialog!!.show()
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

    private fun getSavedAddress() {
        val savedAddress: AddressListResponse.Body? =
            getPrefObject(AppConstants.SAVED_ADDRESS_RESPONSE) as AddressListResponse.Body?

        if (savedAddress != null) {
            selectedAddressId = savedAddress.id.toString()
            tvOrderPersonName?.text = savedAddress.name
            tvOrderDeliveryAddress?.text = savedAddress.address + " , ".plus(savedAddress.city)

            rlAddress?.visibility = View.VISIBLE
            tvSelectAddress?.visibility = View.GONE
        }

    }

    private fun getSavedZipCode(): String? {
        val savedAddress: AddressListResponse.Body? =
            getPrefObject(AppConstants.SAVED_ADDRESS_RESPONSE) as AddressListResponse.Body?
        if (savedAddress != null) {
            return savedAddress.zipcode
        }
        return null
    }

    private fun validateData() {

        if (Validator.buyProduct(selectedAddressId, selectedPaymentType)) {
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
        hashMap["deliveryType"] = "2"  //0=>click&collect 1=>lifernado 2=>ownDelivery
        hashMap["userAddressId"] = selectedAddressId
        hashMap["paymentMethod"] = selectedPaymentType  //0=>Wallet 1=>Card 2=>cash
        selectedDate?.let {
            hashMap["request_date"] = it.time.toString()
        }
        appViewModel.addOrderApi(this, true, hashMap)
        appViewModel.getResponse().observe(this, this)
    }


    private fun thankYouDialog() {
        val alertDialog = AlertDialog.Builder(this)
        val factory = LayoutInflater.from(this)
        val placeOrder: View = factory.inflate(R.layout.dialog_order_placed, null)

        val dialogOrderPlaced = placeOrder.findViewById<Button>(R.id.done)

        dialogOrderPlaced.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(AppConstants.OPEN_BY_CART, true)
            startActivity(intent)
            finishAffinity()
        }
        alertDialog.setCancelable(false)

        alertDialog.setView(placeOrder)
        alertDialog.show()
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
                    val milliSeconds = System.currentTimeMillis() + 604800000L
                    if (date == null) return@run
                    val tempDate = DateHelper.combineDateTime(selectedDate!!, date)
                    if (tempDate != null) {
                        if (tempDate.time < milliSeconds) {
                            showToast("Date cannot be earlier than estimated date")
                            return@run
                        }
                    }
                    selectedDate = tempDate
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
                        getString(
                            R.string.euro_symbol,
                            DecimalFormat("##.##").format(response!!.body.subTotal)
                        )
                    binding.tvDeliveryChargesPrice.text =
                        getString(
                            R.string.euro_symbol,
                            response!!.body.deliveryCharge.toString()
                        )
                    binding.tvTaxPrice.text = response!!.body.tax.toString() + "%"
                    binding.tvTotalPrice.text =
                        getString(
                            R.string.euro_symbol,
                            DecimalFormat("##.##").format(response!!.body.total)
                        )
                    binding.tvItemCount.text = response!!.body.cartItems.size.toString()


                    for (i in 0 until response!!.body.youMayLikeProducts.size) {
                        response!!.body.youMayLikeProducts[i].apply {
                            if (this.group == null) {
                                this.group = SearchProductResponse.Body.Group(
                                    0,
                                    this.productImages
                                )
                            }
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
                                    this.userId,
                                    null,
                                    arrayListOf(),
                                    this.group
                                )
                            )

                        }
                    }
                    noDataVisible()
                    if (showDialog) {
                        dialog?.dismiss()
                        showDialog = false
                        showBottomDialog()
                    }

                } else if (t.data is CommonResponse) {
                    getCartListing()
                    noDataVisible()
                } else if (t.data is CartUpdateResponse) {
                    val response: CartUpdateResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {
                        toast(response.message)
                        getCartListing()
                    }

                } else if (t.data is AddOrderResponse) {
                    val response: AddOrderResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {
                        thankYouDialog()
                    }
                }
            }
            else -> {}
        }
    }

    private fun deleteCartItem(id: String) {
        deleteClick = true
        appViewModel.deleteCartItem(this, true, id)
    }

    private fun noDataVisible() {
        if (list.isEmpty()) {
            binding.tvNoData.visibility = View.VISIBLE
            binding.clData.visibility = View.GONE

            if (deleteClick) {
                onBackPressed()
            }

        } else {
            binding.tvNoData.visibility = View.GONE
            binding.clData.visibility = View.VISIBLE
        }
    }


    override fun onBackPressed() {
        if (list.isEmpty()) {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        } else {
            super.onBackPressed()
        }
    }
}