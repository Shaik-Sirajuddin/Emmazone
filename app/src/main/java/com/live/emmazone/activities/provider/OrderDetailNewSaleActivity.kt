package com.live.emmazone.activities.provider

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.live.emmazone.BuildConfig
import com.live.emmazone.R
import com.live.emmazone.activities.listeners.OnActionListenerNew
import com.live.emmazone.activities.main.ChatActivity
import com.live.emmazone.adapter.AdapterOnGoingOrders
import com.live.emmazone.databinding.ActivityOrderDetailNewSaleBinding
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.response_model.SalesResponse
import com.live.emmazone.response_model.ScanOrderResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.SimpleScannerActivity
import com.live.emmazone.view_models.AppViewModel

class OrderDetailNewSaleActivity : AppCompatActivity(), Observer<RestObservable> {

    lateinit var binding: ActivityOrderDetailNewSaleBinding
    private val appViewModel: AppViewModel by viewModels()
    lateinit var adapter: AdapterOnGoingOrders

    private var model: SalesResponse.Body.Response? = null
    val list = ArrayList<SalesResponse.Body.Response.OrderJson.OrderItem>()


    private val permissions = arrayOf(Manifest.permission.CAMERA)

    private val cameraPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

            if (permissions.isNotEmpty()) {
                permissions.entries.forEach {
                    Log.d("permissions", "${it.key} = ${it.value}")
                }

                val camera = permissions[Manifest.permission.CAMERA]

                if (camera == true) {
                    Log.e("permissions", "Permission Granted Successfully")
                    val intent = Intent(this, SimpleScannerActivity::class.java)
                    scanLauncher.launch(intent)
                } else {
                    Log.e("permissions", "Permission not granted")
                    checkCameraPermission()
                }

            }

        }


    private val scanLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val orderId = result.data!!.getStringExtra(AppConstants.ORDER_ID)
                if (model!!.id.toString() == orderId) {
                    orderStatusApiHit("2")
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailNewSaleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val onActionListenerNew = object : OnActionListenerNew {
            override fun notifyOnClick() {
                //openDetailScreen(model, holder.adapterPosition)
            }
        }

        setAdapter(onActionListenerNew)
        model = intent?.extras!!.get("data") as SalesResponse.Body.Response

        setData(model!!)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.btnReadyDelivery.setOnClickListener {
            //  order status  0-> Pending  1-> on the way 2-> Delivered 3-> cancelled
            orderStatusApiHit("1")
        }

        binding.btnReadyPickup.setOnClickListener {
            //  order status  0-> Pending  1-> on the way 2-> Delivered 3-> cancelled
            orderStatusApiHit("1")
        }

        binding.btnDelivered.setOnClickListener {
            orderStatusApiHit("2")
        }

        binding.btnScan.setOnClickListener {
            checkCameraPermission()
        }

        binding.ivChat.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra(AppConstants.USER2_NAME, model!!.customer.username)
            intent.putExtra(AppConstants.USER2_IMAGE, model!!.customer.image)
            intent.putExtra(AppConstants.USER2_ID, model!!.customer.id.toString())
            startActivity(intent)
        }

    }

    private fun orderStatusApiHit(orderStatusUpdate: String) {
        val hashMap = HashMap<String, String>()
        hashMap["id"] = model!!.id.toString()
        hashMap["orderStatus"] =
            orderStatusUpdate // 0=>pending 1=>On The Way 2=>Delivered 3=>cancelled

        appViewModel.orderStatusApi(this, hashMap, true)
        appViewModel.getResponse().observe(this, this)

    }

    private fun setAdapter(onActionListenerNew: OnActionListenerNew) {
        adapter = AdapterOnGoingOrders(this, list, onActionListenerNew, "detail")
        binding.rvOrderDetailNewSale.adapter = adapter
    }


    private fun requestPermission() {
        cameraPermissions.launch(permissions)
    }

    private fun checkCameraPermission() {

        if (hasPermissionsCheck(permissions)) {
            Log.e("Permissions", "Permissions Granted")
            val intent = Intent(this, SimpleScannerActivity::class.java)
            scanLauncher.launch(intent)
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            checkPermissionDenied(Manifest.permission.CAMERA)
        } else {
            Log.e("Permissions", "Request for Permissions")
            requestPermission()
        }
    }


    // util method
    private fun hasPermissionsCheck(permissions: Array<String>): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkPermissionDenied(permissions: String) {
        if (shouldShowRequestPermissionRationale(permissions)) {
            val mBuilder = AlertDialog.Builder(this)
            val dialog: AlertDialog =
                mBuilder.setTitle(R.string.alert).setMessage(R.string.permissionRequired)
                    .setPositiveButton(
                        R.string.ok
                    ) { dialog, which -> requestPermission() }
                    .setNegativeButton(
                        R.string.cancel
                    ) { dialog, which ->

                    }.create()
            dialog.setOnShowListener {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                    ContextCompat.getColor(
                        this, R.color.green
                    )
                )
            }
            dialog.show()
        } else {
            val builder = AlertDialog.Builder(this)
            val dialog: AlertDialog =
                builder.setTitle(R.string.alert).setMessage(R.string.permissionRequired)
                    .setCancelable(
                        false
                    )
                    .setPositiveButton(R.string.openSettings) { dialog, which ->
//finish()
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts(
                                "package",
                                BuildConfig.APPLICATION_ID + ".provider",
                                null
                            )
                        )
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }.create()
            dialog.setOnShowListener {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                    ContextCompat.getColor(
                        this, R.color.green
                    )
                )
            }
            dialog.show()
        }
    }

    private fun setData(model: SalesResponse.Body.Response) {
        /* var orderQty = 0
         model.orderJson.orderItems.forEach {
             orderQty = orderQty + it.orderedQty
         }*/

        list.addAll(model.orderJson.orderItems)
        adapter.notifyDataSetChanged()
        binding.tvOrderID.text = model.orderNo
        binding.tvUsername.text = model.customer.username
        binding.tvItemCount.text = model.orderJson.orderItems.size.toString()
        Glide.with(this).load(model.customer.image).into(binding.imageSales)
        binding.tvSubTotalPrice.text = getString(R.string.euro_symbol, model.netAmount)
        binding.tvDeliveryChargesPrice.text = getString(R.string.euro_symbol, model.shippingCharges)
        binding.tvTaxPrice.text = getString(R.string.euro_symbol, model.taxCharged)
        binding.tvTotalPrice.text =
            getString(R.string.euro_symbol, model.total.toDouble().toString())
        model.orderJson.userAddress.apply {
            binding.tvOrderPersonName.text = this.name
            binding.tvOrderDeliveryAddress.text =
                this.address + "," + this.city + "," + this.state + "," + this.zipcode
        }
        binding.tvODOrderDate.text = AppUtils.getDateTime(model.created.toLong())
        when (model.orderStatus) {
            0 -> { //  order status  0-> Pending  1-> on the way 2-> Delivered 3-> cancelled
                binding.tvOrderStatus.text = getString(R.string.pending)
                binding.btnReadyDelivery.visibility = View.VISIBLE

                if (model.deliveryType == 0) {
                    binding.btnReadyDelivery.visibility = View.GONE
                    binding.btnReadyPickup.visibility = View.VISIBLE
                } else {
                    binding.btnReadyDelivery.visibility = View.VISIBLE
                    binding.btnReadyPickup.visibility = View.GONE
                }

            }
            1 -> {
                binding.tvOrderStatus.text = getString(R.string.on_the_way)
                binding.btnReadyDelivery.visibility = View.GONE
                binding.btnDelivered.visibility = View.VISIBLE
            }
            2 -> {
                binding.tvOrderStatus.text = getString(R.string.completed)
                binding.btnReadyDelivery.visibility = View.GONE
            }
            3 -> {
                binding.tvOrderStatus.text = getString(R.string.cancel)
                binding.btnReadyDelivery.visibility = View.GONE
            }
        }

        when (model.deliveryType) {
            0 -> {   //   0-> click & collect 1-> Lifernado 2-> Home Delivery
                binding.tvDeliveryType.text = getString(R.string.click_and_collect)
                binding.tvDeliveryAddress.visibility = View.GONE
                binding.layoutItemDeliveryAdrs.visibility = View.GONE
            }
            1 -> {
                binding.tvDeliveryType.text = getString(R.string.lifernado)
            }
            2 -> {
                binding.tvDeliveryType.text = getString(R.string.home_delivery)
            }
        }
        when (model.paymentMethod) {
            0 -> {   //  0=>Wallet 1=>Card 2=>cash
                binding.tvPayment.text = getString(R.string.wallet)
            }
            1 -> {
                binding.tvPayment.text = getString(R.string.card)
            }
            2 -> {
                binding.tvPayment.text = getString(R.string.cash_on_delivery)
            }
        }

        if (model.deliveryType == 0 && model.orderStatus == 1) {
            binding.btnScan.visibility = View.VISIBLE
            binding.btnDelivered.visibility = View.GONE
        }

    }


    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is ScanOrderResponse) {
                    onBackPressed()
                }
            }
            else -> {}
        }
    }


}