package com.live.emmazone.activities.provider

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.BuildConfig
import com.live.emmazone.R
import com.live.emmazone.activities.fragment.FragmentProviderSale
import com.live.emmazone.activities.fragment.OnGoingSalesProviderFragment
import com.live.emmazone.activities.fragment.PastSalesProviderFragment
import com.live.emmazone.activities.main.Notifications
import com.live.emmazone.databinding.ActivityReturnsBinding
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.response_model.ScanOrderResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.SimpleScannerActivity
import com.live.emmazone.view_models.AppViewModel

class ReturnsActivity : AppCompatActivity() , Observer<RestObservable> {
    private lateinit var binding:ActivityReturnsBinding
    private val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReturnsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clicksHandle()
        onGoingClick()
    }
    private fun clicksHandle() {


        binding.onGoingSale.setOnClickListener {
            onGoingClick()
        }

        binding.pastSale.setOnClickListener {
            pastClick()
        }
        binding.scanQR.setOnClickListener {
            checkCameraPermission()
        }
    }
    private fun pastClick() {
        openSalesFragment(PastSalesProviderFragment(true))
        binding.onGoingSale.setBackgroundColor(Color.TRANSPARENT)
        binding.pastSale.setBackgroundResource(R.drawable.bg_fill_earning)
    }

    private fun onGoingClick() {
        openSalesFragment(OnGoingSalesProviderFragment(true))
        binding.onGoingSale.setBackgroundResource(R.drawable.bg_fill_earning)
        binding.pastSale.setBackgroundColor(Color.TRANSPARENT)
    }
    private fun openSalesFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentProviderSalesContainer, fragment)
        transaction.commit()
    }
    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is CommonResponse) {

                }
                if (t.data is ScanOrderResponse) {
                    AppUtils.showMsgOnlyWithoutClick(this, t.data.message)
                }
            }
            else -> {}
        }
    }
    /** Scan Qr Implementation **/
    // util method
    private val permissions = arrayOf(Manifest.permission.CAMERA)

    private fun hasPermissionsCheck(permissions: Array<String>): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(
            this,
            it
        ) == PackageManager.PERMISSION_GRANTED
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

    private val scanLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val orderId = result.data!!.getStringExtra(AppConstants.ORDER_ID)
                if (!orderId.isNullOrEmpty()) {
                    orderStatusApiHit("2", orderId)
                }
            }
        }
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

    private fun orderStatusApiHit(orderStatusUpdate: String, id: String) {
        val hashMap = HashMap<String, String>()
        hashMap["id"] = id
        hashMap["orderStatus"] =
            orderStatusUpdate // 0=>pending 1=>On The Way 2=>Delivered 3=>cancelled
        appViewModel.orderStatusApi(this, hashMap, true)
        appViewModel.getResponse().observe(this, this)

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

    private fun requestPermission() {
        cameraPermissions.launch(permissions)
    }
}