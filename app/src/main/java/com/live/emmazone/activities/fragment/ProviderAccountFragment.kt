package com.live.emmazone.activities.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.BuildConfig
import com.live.emmazone.R
import com.live.emmazone.activities.FAQ
import com.live.emmazone.activities.PrivacyPolicy
import com.live.emmazone.activities.TermsCondition
import com.live.emmazone.activities.auth.ChangePassword
import com.live.emmazone.activities.auth.ProfileActivity
import com.live.emmazone.activities.main.Notifications
import com.live.emmazone.activities.provider.DeliverySettings
import com.live.emmazone.activities.provider.MessageActivity
import com.live.emmazone.activities.provider.MyEarningsActivity
import com.live.emmazone.databinding.FragmentProviderAccountBinding
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.extensionfuncton.savePreference
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.NotificationStatusResponse
import com.live.emmazone.response_model.ProfileResponse
import com.live.emmazone.response_model.ScanOrderResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.SimpleScannerActivity
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.loadImage
import java.util.jar.Manifest

class ProviderAccountFragment : Fragment(), Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()
    private lateinit var binding: FragmentProviderAccountBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProviderAccountBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.switchNotification.isChecked =
            getPreference(AppConstants.NOTIFICATION_TYPE, "") == "1"
        clicksHandle()

        appViewModel.profileApi(requireActivity(), true)
        appViewModel.getResponse().observe(requireActivity(), this)
    }

    private fun clicksHandle() {
        binding.myEarningLayout.setOnClickListener {
            val intent = Intent(activity, MyEarningsActivity::class.java)
            startActivity(intent)
        }


        binding.changePwdLayout.setOnClickListener {
            val intent = Intent(activity, ChangePassword::class.java)
            startActivity(intent)
        }

        binding.messageLayout.setOnClickListener {
            val intent = Intent(activity, MessageActivity::class.java)
            startActivity(intent)
        }

        binding.faqLayout.setOnClickListener {
            val intent = Intent(activity, FAQ::class.java)
            startActivity(intent)
        }

        binding.termsConditionLayout.setOnClickListener {
            val intent = Intent(activity, TermsCondition::class.java)
            startActivity(intent)
        }

        binding.privacyPolicyLayout.setOnClickListener {
            val intent = Intent(activity, PrivacyPolicy::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            val dialog = LogoutDialog()
            dialog.show(requireActivity().supportFragmentManager, "logoutDialog")
        }

        binding.layoutProfile.setOnClickListener {
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.imageNotifications.setOnClickListener {
            val intent = Intent(activity, Notifications::class.java)
            startActivity(intent)
        }

        binding.switchNotification.setOnClickListener {
            if (getPreference(AppConstants.NOTIFICATION_TYPE, "0") == "1")
                hitNotificationUpdateApi("0")
            else
                hitNotificationUpdateApi("1")
        }
        binding.scanQR.setOnClickListener {
            checkCameraPermission()
        }
        binding.deliverySettingLayout.setOnClickListener {
            val intent = Intent(requireContext(),DeliverySettings::class.java)
            startActivity(intent)
        }
    }

    private fun hitNotificationUpdateApi(type: String) {
        val hashMap = HashMap<String, String>()
        hashMap["notification_status"] = type

        appViewModel.notificationStatusApi(requireActivity(), true, hashMap)
        appViewModel.getResponse().observe(requireActivity(), this)
    }

    override fun onResume() {
        super.onResume()
        appViewModel.profileApi(requireActivity(), true)
    }

    override fun onChanged(t: RestObservable?) {

        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is NotificationStatusResponse) {
                    val response: NotificationStatusResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {
                        savePreference(
                            AppConstants.NOTIFICATION_TYPE,
                            response.body.notificationStatus.toString()
                        )
                        AppUtils.showMsgOnlyWithoutClick(
                            requireActivity(),
                            requireContext().getString(R.string.notification_status_update)
                        )
                    }

                } else if (t.data is ProfileResponse) {
                    val response: ProfileResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {

                        val firstLetter = response.body.user.username.subSequence(0, 1)
                        binding.profileText.text = firstLetter
                        binding.tvName.text = response.body.user.username
                        binding.tvEmail.text = response.body.user.email
                        binding.tvPhone.text =
                            response.body.user.countryCode + response.body.user.phone


                        if (!response.body.user.image.isNullOrEmpty()) {
                            binding.proAccountImage.visibility = View.VISIBLE
                            binding.rlProfile.visibility = View.GONE
                            binding.proAccountImage.loadImage(AppConstants.IMAGE_USER_URL + response.body.user.image)
                        } else {
                            binding.proAccountImage.visibility = View.GONE
                            binding.rlProfile.visibility = View.VISIBLE
                        }

                        if (response.body.notificationCount == 0) {
                            binding.notifyRedBG.visibility = View.GONE
                        } else {
                            binding.notifyRedBG.visibility = View.VISIBLE
                        }

                    }

                }
                else if (t.data is ScanOrderResponse) {
                    AppUtils.showMsgOnlyWithoutClick(requireActivity(), t.data.message)
                }
            }


            else -> {}
        }
    }
    /** Scan Qr Implementation **/
    // util method
    private val permissions = arrayOf(android.Manifest.permission.CAMERA)

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

                val camera = permissions[android.Manifest.permission.CAMERA]

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
        } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
            checkPermissionDenied(android.Manifest.permission.CAMERA)
        } else {
            Log.e("Permissions", "Request for Permissions")
            requestPermission()
        }
    }

    private fun requestPermission() {
        cameraPermissions.launch(permissions)
    }
}