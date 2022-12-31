package com.live.emmazone.activities.main

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.live.emmazone.R
import com.live.emmazone.activities.TermsCondition
import com.live.emmazone.activities.auth.LoginActivity
import com.live.emmazone.adapter.AdapterRatingReviews
import com.live.emmazone.adapter.ImageSliderCustomeAdapter
import com.live.emmazone.adapter.ProductSizeAndColorAdapter
import com.live.emmazone.base.AppController
import com.live.emmazone.databinding.ActivityProductDetailBinding
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.extensionfuncton.savePreference
import com.live.emmazone.interfaces.OnPopupClick
import com.live.emmazone.model.ShopProductDetailResponse
import com.live.emmazone.model.SizeAndColorItem
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.*
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.AppUtils.Companion.openGoogleMaps
import com.live.emmazone.utils.DateHelper
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.toBody
import okhttp3.RequestBody
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


class ProductDetailActivity : AppCompatActivity(), Observer<RestObservable>, OnPopupClick,
    View.OnClickListener {

    lateinit var binding: ActivityProductDetailBinding

    private var selectedDate: Date? = null

    private val appViewModel: AppViewModel by viewModels()
    var qty = 0
    var totalQty = 0

    var productId = ""
    var shopName = ""
    var groupId = ""
    var shopImage = ""
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
    private var bottomDialog: BottomSheetDialog? = null
    private var latitude = ""
    private var longitude = ""

    //variant data
    private var selectedPos = 0
    private var selectedColor = 0
    private var selectedSize = 0
    private lateinit var sizeAdapter: ProductSizeAndColorAdapter
    private lateinit var colorAdapter: ProductSizeAndColorAdapter
    private val sizeList = ArrayList<SizeAndColorItem>()
    private val colorList = ArrayList<SizeAndColorItem>()
    private val imageList = arrayListOf<String>()

    //Review Data
    private lateinit var reviewsAdapter: AdapterRatingReviews
    private val reviewsList = ArrayList<ProductReviewModel>()


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
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        qty = binding.tvCount.text.toString().toInt()

        if (intent.getStringExtra("productId") != null) {
            productId = intent.getStringExtra("productId")!!
        }
        if (intent.getStringExtra("groupId") != null) {
            groupId = intent.getStringExtra("groupId")!!
        }

        if (intent.getStringExtra(AppConstants.USER2_NAME) != null &&
            intent.getStringExtra(AppConstants.USER2_IMAGE) != null
        ) {
            shopName = intent.getStringExtra(AppConstants.USER2_NAME)!!
            shopImage = intent.getStringExtra(AppConstants.USER2_IMAGE)!!
        }

        if (intent.getBooleanExtra(AppConstants.SHOP_NAME_VISIBLE, false)) {
            binding.tvShopName.text = shopName
            binding.tvShopName.visibility = View.VISIBLE
        }

        setOnClicks()


        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.imageCart.setOnClickListener {
            if (getPreference(AppConstants.PROFILE_TYPE, "") == "guest") {
                showLoginDialog()
            } else {
                startActivity(Intent(this, Cart::class.java))
            }
        }
        binding.descriptionBox.setOnClickListener {
            toogleDescription()
        }
        binding.reviewBox.setOnClickListener {
            toogleReviews()
        }
        initAdapters()

    }

    private fun toogleDescription() {
        if (binding.containerDescription.visibility == View.VISIBLE) {
            binding.containerDescription.visibility = View.GONE
            binding.toogleDescription.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
        } else {
            binding.containerDescription.visibility = View.VISIBLE
            binding.toogleDescription.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
        }
    }

    private fun toogleReviews() {
        if (binding.containerReviews.visibility == View.VISIBLE) {
            binding.containerReviews.visibility = View.GONE
            binding.toogleReviews.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
        } else {
            binding.containerReviews.visibility = View.VISIBLE
            binding.toogleReviews.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
        }
    }

    private fun initAdapters() {
        reviewsAdapter = AdapterRatingReviews(reviewsList)
        binding.reviewsRecyclerView.adapter = reviewsAdapter
        binding.reviewsRecyclerView.layoutManager = LinearLayoutManager(this)
        sizeAdapter = ProductSizeAndColorAdapter(this, sizeList) {
            selectedSize = it
            updateVariant()
        }
        colorAdapter = ProductSizeAndColorAdapter(this, colorList) {
            selectedColor = it
            updateVariant()
        }
        binding.sizeRecyclerView.adapter = sizeAdapter
        binding.colorRecyclerView.adapter = colorAdapter
        binding.sizeRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.colorRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun updateVariant() {
        try {
            Log.d("this", selectedColor.toString())
            val model = shopProductDetailResponse!!.body.products.find {
                it.categoryColorId == colorList[selectedColor].id &&
                        it.categorySizeId == sizeList[selectedSize].id
            }
            if (model == null) {
                binding.tvQty.text = (0).toString()
                binding.tvOutOfStock.visibility = View.VISIBLE
                binding.btnBuyDeliver.visibility = View.GONE
                binding.btnClickCollect.visibility = View.GONE
                return
            }
            selectedPos = shopProductDetailResponse!!.body.products.indexOf(model)

            binding.productItemName.text = model.name
            Log.e("rating",model.productReview)
            try {
                binding.ratingBarProductDetail.rating = model.productReview.toFloat()
            } catch (e: Exception) {
                Log.e("error",e.message.toString())
            }
            binding.rating.text =
                "${model.productReview}/5"
            binding.tvDesc.text = model.shortDescription
            binding.tvDelivery.text = model.description

            val productImages =
                if (model.group != null && !model.group!!.productImages.isNullOrEmpty()) {
                    model.group!!.productImages
                } else {
                    model.images
                }
            imageList.clear()
            productImages.forEach {
                imageList.add(it.image)
            }
            binding.itemImageProductDetail.adapter = ImageSliderCustomeAdapter(
                this@ProductDetailActivity,
                imageList
            )
            totalQty = model.productQuantity
            qty = minOf(qty, totalQty)

            binding.indicatorProduct.setViewPager(binding.itemImageProductDetail)
            val price = AppUtils.getFormattedAmount(model.productPrice.toDouble())
            binding.tvPriceInteger.text = "$price €"
            binding.tvSize.text = model.productSize.size
            binding.tvColor.text = model.productColor.color
            binding.tvQty.text = getString(R.string.of, model.productQuantity.toString())

            binding.tvCount.text = qty.toString()
            if (model.productQuantity == 0) {
                binding.tvOutOfStock.visibility = View.VISIBLE
                binding.btnBuyDeliver.visibility = View.GONE
                binding.btnClickCollect.visibility = View.GONE
            } else {
                binding.tvOutOfStock.visibility = View.GONE
                binding.btnBuyDeliver.visibility = View.VISIBLE
                binding.btnClickCollect.visibility = View.VISIBLE
            }

        } catch (e: Exception) {
            Log.e("variantFindError", e.message.toString())
        }

    }

    private fun productDetailApiHit() {
        val hashMap = HashMap<String, String>()
        if (groupId.isNotEmpty()) {
            hashMap["groupId"] = groupId
        } else {
            hashMap["id"] = productId
        }
        appViewModel.shopProductDetailApi(this, true, hashMap)
        appViewModel.getResponse().observe(this, this)

    }
    private fun reviewsApiHit(){
        val map = HashMap<String, RequestBody>()
        map["productId"] = toBody(productId)
        appViewModel.getProductReviews(this, true, map)
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
        hashMap["productId"] = shopProductDetailResponse!!.body.products[selectedPos].id.toString()
        hashMap["qty"] = binding.tvCount.text.toString()
        appViewModel.addToCart(this, true, hashMap)
    }

    private fun showBottomDialog() {
        bottomDialog = BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.activity_bottom_sheet_dialog, null)
        bottomDialog?.setCancelable(true)
        bottomDialog?.setContentView(view)

        tvDeliveryDate = view.findViewById(R.id.tvDeliveryDate)
        val tvEstimateDeliveryTime = view.findViewById<TextView>(R.id.estimateDeliveryTime)
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
        val tvChangeDateTime = view.findViewById<TextView>(R.id.tvChangeDateTime)
        tvSelectAddress = view.findViewById(R.id.tvSelectAddress)
        tvSelectPayment = view.findViewById(R.id.tvSelectPayment)
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
        tvEstimateDeliveryTime?.visibility = View.GONE
        tvDeliveryDate?.visibility = View.GONE
        tvChangeDateTime.visibility = View.GONE
        getSavedPaymentType()

        val subTotal =
            shopProductDetailResponse!!.body.products[selectedPos].productPrice.toFloat() * qty
        val tax = shopProductDetailResponse!!.body.taxValue!!.value.toInt()
        val taxCharged = ((subTotal / 100) * tax)
        val totalPrice = taxCharged + subTotal

        tvCountItem.text = "1"
        tvSubTotalPrice.text = AppUtils.getFormattedAmount(subTotal.toDouble())
        tvTaxPrice.text = AppUtils.getFormattedAmount(taxCharged.toDouble())
        tvTotalPrice.text = AppUtils.getFormattedAmount(totalPrice.toDouble())


        val milliSeconds = System.currentTimeMillis() + 604800000L //after 7 days
        tvDeliveryDate?.text = AppUtils.milliSecondsToTime(milliSeconds, AppConstants.DATE_FORMAT)

        /*tvChangeDateTime.setOnClickListener { openDateTimerPicker() }*/


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
        bottomDialog?.show()
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
        hashMap["productId"] = shopProductDetailResponse!!.body.products[selectedPos].id.toString()
        hashMap["qty"] = qty.toString()
        appViewModel.addOrderApi(this, true, hashMap)
        appViewModel.getResponse().observe(this, this)
    }

    private fun createSizeList(products: ArrayList<Product>): ArrayList<SizeAndColorItem> {
        val list = ArrayList<SizeAndColorItem>()
        for (product in products) {
            val item = SizeAndColorItem(
                product.productSize.categorySizeId,
                product.productSize.size,
                true
            )
            if (!list.contains(item)) {
                list.add(item)
            }
        }
        return list
    }

    private fun createColorList(products: ArrayList<Product>): ArrayList<SizeAndColorItem> {
        val list = ArrayList<SizeAndColorItem>()
        for (product in products) {
            val item = SizeAndColorItem(
                product.productColor.categoryColorId,
                product.productColor.color,
                true
            )
            if (!list.contains(item)) {
                list.add(item)
            }
        }
        return list
    }

    private fun setSelectedPosition() {
        val product = shopProductDetailResponse!!.body.products.find {
            it.id == productId.toInt()
        }
        selectedPos = shopProductDetailResponse!!.body.products.indexOf(product)
        Log.d("pos", selectedPos.toString())
        selectedColor = colorList.indexOf(colorList.find {
            it.id == product!!.productColor.categoryColorId
        })
        selectedSize = sizeList.indexOf(sizeList.find {
            it.id == product!!.productSize.categorySizeId
        })

        binding.colorRecyclerView.findViewHolderForAdapterPosition(selectedColor)?.itemView?.performClick()
        binding.sizeRecyclerView.findViewHolderForAdapterPosition(selectedSize)?.itemView?.performClick()

    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is ShopProductDetailResponse) {

                    shopProductDetailResponse = t.data

                    val data = t.data.body

                    sizeList.clear()
                    colorList.clear()
                    sizeList.addAll(createSizeList(data.products))
                    colorList.addAll(createColorList(data.products))
                    sizeAdapter.notifyDataSetChanged()
                    colorAdapter.notifyDataSetChanged()

                    setSelectedPosition()

                    latitude = data.latitude
                    longitude = data.longitude


                    updateVariant()

                    if (data.cartCount == 0) {
                        binding.ivRedCart.visibility = View.GONE
                    } else {
                        binding.ivRedCart.visibility = View.VISIBLE
                    }
                    reviewsApiHit()

                } else if (t.data is CommonResponse) {
                    AppUtils.showMsgOnlyWithClick(this, "Item add to cart successfully", this)
                } else if (t.data is AddOrderResponse) {
                    val response: AddOrderResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {
                        thankYouDialog()
                    }
                } else if (t.data is ReviewsResponse) {
                    updateReviews(t.data.body)
                }
            }
            else -> {}
        }
    }

    private fun updateReviews(data: ArrayList<ProductReviewModel>) {
        reviewsList.clear()
        reviewsList.addAll(data)
        reviewsAdapter.notifyDataSetChanged()
    }


    private fun thankYouDialog() {
        val alertDialog = AlertDialog.Builder(this)
        val factory = LayoutInflater.from(this)
        val placeOrder: View = factory.inflate(R.layout.dialog_order_placed, null)

        val dialogOrderPlaced = placeOrder.findViewById<Button>(R.id.done)

        dialogOrderPlaced.setOnClickListener {
            bottomDialog?.dismiss()
            openGoogleMaps(latitude, longitude)
//
//            val intent = Intent(this, MainActivity::class.java)
//            intent.putExtra(AppConstants.OPEN_BY_CART, true)
//            startActivity(intent)
//            finishAffinity()
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
                openChatScreen()
            }
            R.id.btnBuyDeliver -> {
                if (getPreference(AppConstants.PROFILE_TYPE, "") == "guest") {
                    showLoginDialog()
                } else {
                    if (qty > 0) {
                        addToCart()
                    } else {
                        AppUtils.showMsgOnlyWithoutClick(this, "Please select atleast one item")
                    }
                }

            }
            R.id.btnClickCollect -> {
                if (getPreference(AppConstants.PROFILE_TYPE, "") == "guest") {
                    showLoginDialog()
                } else {
                    if (qty > 0) {
                        showBottomDialog()
                    } else {
                        AppUtils.showMsgOnlyWithoutClick(this, "Please select atleast one item")
                    }
                }

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

    private fun openChatScreen() {

        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra(AppConstants.USER2_NAME, shopName)
        intent.putExtra(AppConstants.USER2_IMAGE, shopImage)
        intent.putExtra(
            AppConstants.USER2_ID,
            shopProductDetailResponse!!.body.products[selectedPos].userId.toString()
        )
        startActivity(intent)
    }


    override fun onResume() {
        super.onResume()
        productDetailApiHit()
    }
}