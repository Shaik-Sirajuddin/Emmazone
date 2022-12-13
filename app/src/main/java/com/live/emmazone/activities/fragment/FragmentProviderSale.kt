package com.live.emmazone.activities.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.BuildConfig
import com.live.emmazone.R
import com.live.emmazone.activities.main.Notifications
import com.live.emmazone.activities.provider.ProviderMainActivity
import com.live.emmazone.activities.provider.ReturnsActivity
import com.live.emmazone.databinding.FragmentSaleProviderBinding
import com.live.emmazone.extensionfuncton.savePreference
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.response_model.NotificationListingResponse
import com.live.emmazone.response_model.ScanOrderResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.SimpleScannerActivity
import com.live.emmazone.view_models.AppViewModel

class FragmentProviderSale(val notificationResponse: NotificationListingResponse.Body?) :
    Fragment() , Observer<RestObservable> {

    companion object {
        lateinit var imageRedDot: ImageView
    }

    private lateinit var binding: FragmentSaleProviderBinding
    private val appViewModel: AppViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSaleProviderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clicksHandle()
        savePreference(AppConstants.NEW_SALE,false)
        (requireActivity() as ProviderMainActivity).hideBadge()
        if (notificationResponse != null) {
            //  orderStatus  0-> Pending  1-> on the way 2-> Delivered 3-> cancelled
                notificationReadApiHit()
            if (notificationResponse.orderStatus == 0) {
                newSaleClick()
            } else if (notificationResponse.orderStatus == 1) {
                onGoingClick()
            } else if (notificationResponse.orderStatus == 2||notificationResponse.orderStatus == 3) {
                pastClick()
            }
            else if(notificationResponse.orderStatus == 7 || notificationResponse.orderStatus == 8 ){
                binding.openReturns.performClick()
            }

        } else {
            newSaleClick()
        }

    }
    private fun notificationReadApiHit() {
        val hashMap = HashMap<String, String>()
        hashMap["id"] = notificationResponse!!.id.toString()

        appViewModel.readNotificationApi(requireActivity(), hashMap, true)
        appViewModel.getResponse().observe(requireActivity(), this)
    }


    private fun clicksHandle() {

        imageRedDot = binding.notifyRedBG

        binding.imageNotifications.setOnClickListener {
            val intent = Intent(activity, Notifications::class.java)
            startActivity(intent)
        }

        binding.newSale.setOnClickListener {
            newSaleClick()
        }

        binding.onGoingSale.setOnClickListener {
            onGoingClick()
        }

        binding.pastSale.setOnClickListener {
            pastClick()
        }
        binding.scanQR.setOnClickListener {
            checkCameraPermission()
        }
        binding.openReturns.setOnClickListener {
            val intent = Intent(requireContext(),ReturnsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun pastClick() {
        openSalesFragment(PastSalesProviderFragment())
        binding.onGoingSale.setBackgroundColor(Color.TRANSPARENT)
        binding.salesLayout.setBackgroundResource(R.drawable.bg_earning)
        binding.newSale.setBackgroundColor(Color.TRANSPARENT)
        binding.pastSale.setBackgroundResource(R.drawable.bg_fill_earning)
    }

    private fun onGoingClick() {
        openSalesFragment(OnGoingSalesProviderFragment())
        binding.onGoingSale.setBackgroundResource(R.drawable.bg_fill_earning)
        binding.newSale.setBackgroundColor(Color.TRANSPARENT)
        binding.salesLayout.setBackgroundResource(R.drawable.bg_earning)
        binding.pastSale.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun newSaleClick() {
        openSalesFragment(NewSalesProviderFragment())
        binding.newSale.setBackgroundResource(R.drawable.bg_fill_earning)
        binding.onGoingSale.setBackgroundColor(Color.TRANSPARENT)
        binding.pastSale.setBackgroundColor(Color.TRANSPARENT)
        binding.salesLayout.setBackgroundResource(R.drawable.bg_earning)
    }


    private fun openSalesFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragmentProviderSalesContainer, fragment)
        transaction?.commit()
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is CommonResponse) {

                }
                if (t.data is ScanOrderResponse) {
                    AppUtils.showMsgOnlyWithoutClick(requireActivity(), t.data.message)
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
            requireContext(),
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkPermissionDenied(permissions: String) {
        if (shouldShowRequestPermissionRationale(permissions)) {
            val mBuilder = AlertDialog.Builder(requireContext())
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
                        requireContext(), R.color.green
                    )
                )
            }
            dialog.show()
        } else {
            val builder = AlertDialog.Builder(requireContext())
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
                        requireContext(), R.color.green
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
                    val intent = Intent(requireContext(), SimpleScannerActivity::class.java)
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
        appViewModel.orderStatusApi(requireActivity(), hashMap, true)
        appViewModel.getResponse().observe(requireActivity(), this)

    }

    private fun checkCameraPermission() {

        if (hasPermissionsCheck(permissions)) {
            Log.e("Permissions", "Permissions Granted")
            val intent = Intent(requireContext(), SimpleScannerActivity::class.java)
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