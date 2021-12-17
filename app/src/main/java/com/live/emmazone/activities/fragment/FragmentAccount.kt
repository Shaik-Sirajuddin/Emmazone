package com.live.emmazone.activities.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
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
import com.live.emmazone.activities.main.Cart
import com.live.emmazone.activities.main.Notifications
import com.makeramen.roundedimageview.RoundedImageView

class FragmentAccount : Fragment() {

    var isNotification = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = LayoutInflater.from(context).inflate(R.layout.fragment_account, container, false)

        val logout = view.findViewById<Button>(R.id.btn_logout)
        val changePwdLayout = view.findViewById<ConstraintLayout>(R.id.changePwdLayout)
        val faqLayout = view.findViewById<ConstraintLayout>(R.id.faqLayout)
        val tcLayout = view.findViewById<ConstraintLayout>(R.id.termsConditionLayout)
        val privacyPolicyLayout = view.findViewById<ConstraintLayout>(R.id.privacyPolicyLayout)
        val cart = view.findViewById<ImageView>(R.id.cart)
        val notifications = view.findViewById<ImageView>(R.id.image_notifications)
        val profileImage = view.findViewById<RoundedImageView>(R.id.pickImage)
        val toggle = view.findViewById<SwitchMaterial>(R.id.switch_notification)

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

        profileImage.setOnClickListener {
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
        }

        cart.setOnClickListener {
            val intent = Intent(activity, Cart::class.java)
            startActivity(intent)
        }

        notifications.setOnClickListener {
            val intent = Intent(activity, Notifications::class.java)
            startActivity(intent)
        }

        logout.setOnClickListener {
            activity?.finishAffinity()
            val intent = Intent(activity, UserLoginChoice::class.java)
            startActivity(intent)
        }
        changePwdLayout.setOnClickListener {
            val intent = Intent(activity, ChangePassword::class.java)
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
        privacyPolicyLayout.setOnClickListener {
            val intent = Intent(activity, Privacypolicy::class.java)
            startActivity(intent)
        }

        return view
    }
}