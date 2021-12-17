package com.live.emmazone.activities.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial
import com.live.emmazone.R
import com.live.emmazone.activities.FAQ
import com.live.emmazone.activities.Privacypolicy
import com.live.emmazone.activities.TermsCondition
import com.live.emmazone.activities.auth.ChangePassword
import com.live.emmazone.activities.auth.LoginActivity
import com.live.emmazone.activities.auth.ProfileActivity
import com.live.emmazone.activities.auth.UserLoginChoice
import com.live.emmazone.activities.main.Message
import com.live.emmazone.activities.main.Notifications
import com.live.emmazone.activities.provider.MyEarningsActivity

class FragmentProviderAccount : Fragment() {
    var isNotification = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.fragment_provider_account, container, false)

        val earningLayout: ConstraintLayout = view.findViewById(R.id.myEarningLayout)
        val pwdLayout: ConstraintLayout = view.findViewById(R.id.changePwdLayout)
        val msgLayout: ConstraintLayout = view.findViewById(R.id.messageLayout)
        val faqLayout: ConstraintLayout = view.findViewById(R.id.faqLayout)
        val tcLayout: ConstraintLayout = view.findViewById(R.id.termsConditionLayout)
        val pcLayout: ConstraintLayout = view.findViewById(R.id.privacyPolicyLayout)
        val btnLogout: Button = view.findViewById(R.id.btn_logout)
        val imgNotify: ImageView = view.findViewById(R.id.image_notifications)
        val proAccountImage: ImageView = view.findViewById(R.id.proAccountImage)
        val toggle: SwitchMaterial = view.findViewById(R.id.switch_notification)

//        toggle.setOnClickListener {
//
//            isNotification = !isNotification
//            toggle.setImageResource(
//                if (isNotification)
//                    R.drawable.on
//                else
//                    R.drawable.off
//            )
//        }

        proAccountImage.setOnClickListener {
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
        }

        imgNotify.setOnClickListener {
            val intent = Intent(activity, Notifications::class.java)
            startActivity(intent)
        }

        earningLayout.setOnClickListener {
            val intent = Intent(activity, MyEarningsActivity::class.java)
            startActivity(intent)
        }
        pwdLayout.setOnClickListener {
            val intent = Intent(activity, ChangePassword::class.java)
            startActivity(intent)
        }
        msgLayout.setOnClickListener {
            val intent = Intent(activity, Message::class.java)
            startActivity(intent)
        }
        faqLayout.setOnClickListener {
            val intent = Intent(activity, FAQ::class.java)
            startActivity(intent)
        }
        tcLayout.setOnClickListener {
            val intent = Intent(activity, TermsCondition::class.java)
            startActivity(intent)
        }
        pcLayout.setOnClickListener {
            val intent = Intent(activity, Privacypolicy::class.java)
            startActivity(intent)
        }
        btnLogout.setOnClickListener {

            activity?.finishAffinity()

            val intent = Intent(activity, UserLoginChoice::class.java)
            startActivity(intent)
        }

        return view
    }

}