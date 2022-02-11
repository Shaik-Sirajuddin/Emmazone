package com.live.emmazone.activities.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.switchmaterial.SwitchMaterial
import com.live.emmazone.R
import com.live.emmazone.activities.FAQ
import com.live.emmazone.activities.PrivacyPolicy
import com.live.emmazone.activities.TermsCondition
import com.live.emmazone.activities.auth.ChangePassword
import com.live.emmazone.activities.auth.ProfileActivity
import com.live.emmazone.activities.auth.UserLoginChoice
import com.live.emmazone.activities.main.Cart
import com.live.emmazone.activities.main.Notifications
import com.live.emmazone.databinding.FragmentAccountBinding
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.extensionfuncton.savePreference
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.NotificationStatusResponse
import com.live.emmazone.response_model.ProfileResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel
import com.makeramen.roundedimageview.RoundedImageView
import com.schunts.extensionfuncton.loadImage

class AccountFragment : Fragment(), Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()

    private lateinit var binding: FragmentAccountBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appViewModel.profileApi(requireActivity(), true)
        appViewModel.getResponse().observe(this, this)
        clicksHandle()
    }

    private fun clicksHandle() {
        binding.pickImage.setOnClickListener {
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.cart.setOnClickListener {
            val intent = Intent(activity, Cart::class.java)
            startActivity(intent)
        }

        binding.imageNotifications.setOnClickListener {
            val intent = Intent(activity, Notifications::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            activity?.finishAffinity()
            val intent = Intent(activity, UserLoginChoice::class.java)
            startActivity(intent)
        }
        binding.changePwdLayout.setOnClickListener {
            val intent = Intent(activity, ChangePassword::class.java)
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

        binding.switchNotification.isChecked =
            getPreference(AppConstants.NOTIFICATION_TYPE, "") == "1"


        binding.switchNotification.setOnClickListener {
            if (getPreference(AppConstants.NOTIFICATION_TYPE, "0") == "1")
                hitNotificationUpdateApi("0")
            else
                hitNotificationUpdateApi("1")
        }
    }

    private fun hitNotificationUpdateApi(type: String) {
        val hashMap = HashMap<String, String>()
        hashMap["notification_status"] = type

        appViewModel.notificationStatusApi(requireActivity(), true, hashMap)
        appViewModel.getResponse().observe(this, this)
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
                        binding.pickImage.loadImage(AppConstants.IMAGE_USER_URL + response.body.image)
                        binding.tvName.text = response.body.username
                        binding.tvEmail.text = response.body.email
                        binding.tvPhone.text = response.body.countryCode + response.body.phone
                    }


                }
            }


        }


    }
}