package com.live.emmazone.activities.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.live.emmazone.adapter.AdapterProviderShopDetailProducts
import com.live.emmazone.databinding.FragmentAddProductProviderBinding
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.*
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.SimpleScannerActivity
import com.live.emmazone.utils.ToastUtils
import com.live.emmazone.view_models.AppViewModel
import kotlinx.android.synthetic.main.fragment_provider_home.view.*

class FragmentProviderAddProduct : Fragment(), Observer<RestObservable> {

    private lateinit var binding: FragmentAddProductProviderBinding
    private val list = ArrayList<ProductGroup>()
    private val appViewModel: AppViewModel by viewModels()
    var productAdapter: AdapterProviderShopDetailProducts? = null
    var pos = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddProductProviderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.back.setOnClickListener {
            val intent = Intent(activity, ProviderMainActivity::class.java)
            startActivity(intent)
        }

        binding.imgNotify.setOnClickListener {
            val intent = Intent(activity, Notifications::class.java)
            startActivity(intent)
        }

        binding.edtSearchAddProduct.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                filterProduct(s.toString())
            }

        })

        binding.scanQR.setOnClickListener {
            checkCameraPermission()
        }
    }

    private fun filterProduct(text: String) {
        val filterList = ArrayList<ProductGroup>()

        list.forEach {
            if (it.name.contains(text, true) ||
                it.id.toString().contains(text) ||
                it.registerCode.toString().contains(text)) {
                filterList.add(it)
            }
        }
        productAdapter?.notifyData(filterList)
    }

    override fun onResume() {
        super.onResume()
        getSellerShopDetails()

    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is SellerShopDetailResponse) {
                    val response: SellerShopDetailResponse = t.data
                    if (response.code == AppConstants.SUCCESS_CODE) {
                        setDetailData(response)
                    }

                }
                if (t.data is CommonResponse) {
                    val response: CommonResponse = t.data
                    if (response.code == AppConstants.SUCCESS_CODE) {
                        ToastUtils.showLongToast(response.message)
                        productAdapter!!.deleteItem(pos)
                    }
                }
                if (t.data is ScanOrderResponse) {
                    AppUtils.showMsgOnlyWithoutClick(requireActivity(), t.data.message)
                }
            }
            Status.ERROR -> {
                ToastUtils.showLongToast(t.error.toString())

            }
            else -> {}
        }
    }

    private fun getSellerShopDetails() {
        appViewModel.sellerShopDetailsApi(requireActivity(), true)
        appViewModel.getResponse().observe(requireActivity(), this)
    }



    private fun setDetailData(response: SellerShopDetailResponse) {
        if (response.body.notificationCount == 0) {
            binding.notifyRedBG.visibility = View.GONE
        } else {
            binding.notifyRedBG.visibility = View.VISIBLE
        }

        val category = Product.Category("", "")
        val product_images: List<Product.ProductImage> =
            ArrayList()

        list.clear()

        list.add(ProductGroup(
            ProductGroup.Category("",""),0,"",-1,"","",0,
        arrayListOf(),"", arrayListOf(),0))

        list.addAll(response.body.groups)
        productAdapter = AdapterProviderShopDetailProducts(requireContext(), list, this)
        binding.rvAdProductProvider.adapter = productAdapter

    }

    fun deleteProductAPIMethod(position: Int, productId: String) {
        pos = position
        appViewModel.deleteProductApi(requireActivity(), true, productId)
        appViewModel.mResponse.observe(requireActivity(), this)
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