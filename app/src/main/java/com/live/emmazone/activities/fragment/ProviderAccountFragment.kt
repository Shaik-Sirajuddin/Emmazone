package com.live.emmazone.activities.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.live.emmazone.activities.FAQ
import com.live.emmazone.activities.PrivacyPolicy
import com.live.emmazone.activities.TermsCondition
import com.live.emmazone.activities.auth.ChangePassword
import com.live.emmazone.activities.auth.ProfileActivity
import com.live.emmazone.activities.auth.UserLoginChoice
import com.live.emmazone.activities.main.Message
import com.live.emmazone.activities.main.Notifications
import com.live.emmazone.activities.provider.MyEarningsActivity
import com.live.emmazone.databinding.FragmentProviderAccountBinding

class ProviderAccountFragment : Fragment() {

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

        clicksHandle()

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


    }
}