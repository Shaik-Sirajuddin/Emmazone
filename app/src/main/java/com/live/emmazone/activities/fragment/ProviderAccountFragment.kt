package com.live.emmazone.activities.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.R
import com.live.emmazone.activities.FAQ
import com.live.emmazone.activities.PrivacyPolicy
import com.live.emmazone.activities.TermsCondition
import com.live.emmazone.activities.auth.ChangePassword
import com.live.emmazone.activities.auth.ProfileActivity
import com.live.emmazone.activities.main.Message
import com.live.emmazone.activities.main.Notifications
import com.live.emmazone.activities.provider.MyEarningsActivity
import com.live.emmazone.databinding.FragmentProviderAccountBinding
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.extensionfuncton.savePreference
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.NotificationStatusResponse
import com.live.emmazone.response_model.ProfileResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.loadImage

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
        appViewModel.getResponse().observe(this, this)
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
            val intent = Intent(activity, Message::class.java)
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

        binding.proAccountImage.setOnClickListener {
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
                        binding.proAccountImage.loadImage(AppConstants.IMAGE_USER_URL + response.body.image)
                        binding.tvName.text = response.body.username
                        binding.tvEmail.text = response.body.email
                        binding.tvPhone.text = response.body.countryCode + response.body.phone
                    }


                }
            }


        }


    }
}