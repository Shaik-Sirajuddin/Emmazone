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
import com.live.emmazone.activities.main.Cart
import com.live.emmazone.activities.main.Notifications
import com.live.emmazone.activities.provider.MessageActivity
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
import com.schunts.extensionfuncton.loadImage

class AccountFragment : Fragment(), Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()
    private lateinit var binding: FragmentAccountBinding
    private var profileLoaded = false
    private var updatedProfiie = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appViewModel.profileApi(requireActivity(), true)
        appViewModel.getResponse().observe(requireActivity(), this)
        clicksHandle()
    }

    private fun clicksHandle() {

        binding.clProfile.setOnClickListener {
            updatedProfiie = true
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.messageLayout.setOnClickListener {
            val intent = Intent(activity, MessageActivity::class.java)
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
            val dialog = LogoutDialog()
            dialog.show(requireActivity().supportFragmentManager, "logoutDialog")
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
        appViewModel.getResponse().observe(requireActivity(), this)
    }

    override fun onResume() {
        super.onResume()
        if(!profileLoaded || updatedProfiie){
            updatedProfiie = false
            appViewModel.profileApi(requireActivity(), true)
        }
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

                        profileLoaded = true

                        if (response.body.notificationCount == 0) {
                            binding.notifyRedBG.visibility = View.GONE
                        } else {
                            binding.notifyRedBG.visibility = View.VISIBLE
                        }

                        if (response.body.cartCount == 0) {
                            binding.ivRedCart.visibility = View.GONE
                        } else {
                            binding.ivRedCart.visibility = View.VISIBLE
                        }

                        val firstLetter = response.body.user.username.subSequence(0, 1)
                        binding.profileText.text = firstLetter
                        binding.tvName.text = response.body.user.username
                        binding.tvEmail.text = response.body.user.email
                        binding.tvPhone.text = response.body.user.countryCode + response.body.user.phone

                        if (!response.body.user.image.isNullOrEmpty()) {
                            binding.pickImage.visibility = View.VISIBLE
                            binding.rlProfile.visibility = View.GONE
                            binding.pickImage.loadImage(AppConstants.IMAGE_USER_URL + response.body.user.image)
                        } else {
                            binding.pickImage.visibility = View.GONE
                            binding.rlProfile.visibility = View.VISIBLE
                        }

                    }


                }
            }
            else -> {}
        }
    }
}