package com.live.emmazone.activities.fragment

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.BuildConfig
import com.live.emmazone.R
import com.live.emmazone.activities.main.Notifications
import com.live.emmazone.activities.provider.EditShopDetailActivity
import com.live.emmazone.activities.provider.MessageActivity
import com.live.emmazone.adapter.AdapterProShopProducts
import com.live.emmazone.adapter.AdapterShopDetailCategory
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.*
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.AppUtils.Companion.getURLForResource
import com.live.emmazone.utils.SimpleScannerActivity
import com.live.emmazone.utils.ToastUtils
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.letterByteArray
import com.schunts.extensionfuncton.loadImage
import com.schunts.extensionfuncton.prepareMultiPart
import com.schunts.extensionfuncton.toBody
import kotlinx.android.synthetic.main.fragment_provider_home.view.*
import okhttp3.RequestBody
import java.io.File


class FragmentProviderHome : Fragment(), Observer<RestObservable> {
    var productAdapter: AdapterProShopProducts? = null
    val list = ArrayList<SellerShopDetailResponse.Body.ShopDetails.ShopCategory>()
    private val listProSDProducts = ArrayList<ProductGroup>()
    private val cacheProductList = ArrayList<ProductGroup>()
    lateinit var adapter: AdapterShopDetailCategory
    private val appViewModel: AppViewModel by viewModels()
    private lateinit var response: SellerShopDetailResponse
    var selectedCatID = -1

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                getSellerShopDetails()
            }
        }
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
                    val intent = Intent(requireContext(), SimpleScannerActivity::class.java)
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
                if (!orderId.isNullOrEmpty()) {
                    orderStatusApiHit("2" , orderId)
                }
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_provider_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireView().imgNotify.setOnClickListener {
            val intent = Intent(activity, Notifications::class.java)
            startActivity(intent)
        }
        requireView().messagesIcon.setOnClickListener {
            val intent = Intent(requireContext(), MessageActivity::class.java)
            startActivity(intent)
        }
        requireView().image_editShop.setOnClickListener {
            val intent = Intent(activity, EditShopDetailActivity::class.java)
            intent.putExtra(AppConstants.SHOP_DETAIL_RESPONSE, response)
            launcher.launch(intent)
        }
        requireView().addCategoryButton.setOnClickListener {
            addCategory()
        }
        requireView().newCategoryName.doAfterTextChanged {
            val text = it.toString().trim()
            if (text.length != 1) return@doAfterTextChanged
            val byteArray = letterByteArray(text)
            requireView().newCategoryImage.loadImage(byteArray)
        }
        requireView().scanQR.setOnClickListener {
            checkCameraPermission()
        }
    }
    private fun orderStatusApiHit(orderStatusUpdate: String , id:String) {
        val hashMap = HashMap<String, String>()
        hashMap["id"] = id
        hashMap["orderStatus"] =
            orderStatusUpdate // 0=>pending 1=>On The Way 2=>Delivered 3=>cancelled

        appViewModel.orderStatusApi(requireActivity(), hashMap, true)
        appViewModel.getResponse().observe(requireActivity(), this)

    }

    private fun getSellerShopDetails() {
        appViewModel.sellerShopDetailsApi(requireActivity(), true)
        appViewModel.getResponse().observe(requireActivity(), this)
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is SellerShopDetailResponse) {
                    response = t.data
                    if (response.code == AppConstants.SUCCESS_CODE) {
                        setDetailData(response)
                    }

                }
                else if (t.data is CommonResponse) {
                    val response: CommonResponse = t.data
                    if (response.code == AppConstants.SUCCESS_CODE) {
                        ToastUtils.showLongToast(response.message)
                        productAdapter!!.deleteItem(pos)
                    }
                }
                else if( t.data is AddCategoryResponse){
                    val response: AddCategoryResponse = t.data
                    if (response.code == AppConstants.SUCCESS_CODE) {
                        ToastUtils.showLongToast(response.message)
                    }
                    if(requireView().newCategoryName.hasFocus()){
                        hideKeyboardFrom(requireContext(),requireView().newCategoryName)
                    }
                    requireView().cardView.visibility = View.GONE
                    getSellerShopDetails()
                }
                else if(t.data is ScanOrderResponse){
                    AppUtils.showMsgOnlyWithoutClick(requireActivity(),t.data.message)
                }
            }
            Status.ERROR -> {
                ToastUtils.showLongToast(t.error.toString())

            }
            else -> {}
        }
    }

    private fun setDetailData(response: SellerShopDetailResponse) {
        if (response.body.shopDetails.image != null) {
            Log.e("image", response.body.shopDetails.image)
            requireView().imageShopDetail.loadImage(response.body.shopDetails.image)
        }

        if (response.body.notificationCount == 0) {
            requireView().notifyRedBG.visibility = View.GONE
        } else {
            requireView().notifyRedBG.visibility = View.VISIBLE
        }

        requireView().tvWishListStoreName.text = response.body.shopDetails.shopName
        requireView().tvDesc.text = response.body.shopDetails.shopDescription
        requireView().tvFYear.text = response.body.shopDetails.year.toString()
        requireView().tvShopAddress.text = response.body.shopDetails.shopAddress
        requireView().tvHeartsCount.text = response.body.shopDetails.likesCount.toString()
        list.clear()
        listProSDProducts.clear()
        cacheProductList.clear()
        cacheProductList.addAll(response.body.groups)
        listProSDProducts.addAll(response.body.groups)
        list.addAll(response.body.shopDetails.shopCategories)
        list.add(
            SellerShopDetailResponse.Body.ShopDetails.ShopCategory(
                0, requireActivity().getURLForResource(R.drawable.addd), "Add", "", 0, 0, "", 0
            )
        )
        productAdapter = AdapterProShopProducts(requireContext(), listProSDProducts, this)
        requireView().recyclerProviderSDProducts.adapter = productAdapter
//        requireView().recyclerProviderSDProducts.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        requireView().recyclerProviderShopDetailCategory.adapter = AdapterShopDetailCategory(list) {
            Log.e("cat",it.toString())
            when (it) {
                (list.size - 1) -> {
                    toggleAddCategory()
                }
                -1 -> {
                    listProSDProducts.clear()
                    listProSDProducts.addAll(cacheProductList)
                    productAdapter!!.notifyDataSetChanged()
                }
                else -> {
                    Log.e("catId", list[it].categoryId.toString())
                    listProSDProducts.clear()
                    listProSDProducts.addAll(cacheProductList.filter { product ->
                        product.categoryId == list[it].categoryId
                    })
                    productAdapter!!.notifyDataSetChanged()
                }
            }
        }

    }

    private fun addCategory() {
        val name = requireView().newCategoryName.text.toString().trim()
        if (name.isEmpty()) {
            AppUtils.showMsgOnlyWithoutClick(requireContext(), "Name cannot be empty")
            return
        }
        requireView().newCategoryName.setText("")
        val hashMap = HashMap<String, RequestBody>()
        hashMap["name"] = toBody(name)
        hashMap["vendorId"] = toBody(response.body.shopDetails.id.toString())

        val byteArray = letterByteArray(name.substring(0,1))
        val image = prepareMultiPart("image", byteArray)
        appViewModel.addCategory(requireActivity(), true, hashMap, image)
        appViewModel.getResponse().observe(requireActivity(),this)
    }
    private fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    private fun toggleAddCategory() {
        if (requireView().cardView.visibility == View.VISIBLE) {
            if(requireView().newCategoryName.hasFocus()){
                hideKeyboardFrom(requireContext(),requireView().newCategoryName)
            }
            requireView().cardView.visibility = View.GONE
        } else {
            requireView().cardView.visibility = View.VISIBLE
        }
    }


    var pos = 0
    fun deleteProductGroup(position: Int, groupId: String) {
        pos = position
        appViewModel.deleteProductGroupApi(requireActivity(), true, groupId)
        appViewModel.mResponse.observe(requireActivity(), this)
    }

    override fun onResume() {
        super.onResume()
        getSellerShopDetails()
    }

    // util method
    private fun hasPermissionsCheck(permissions: Array<String>): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
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