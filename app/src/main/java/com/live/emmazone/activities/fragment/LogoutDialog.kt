package com.live.emmazone.activities.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.activities.auth.UserLoginChoice
import com.live.emmazone.databinding.DialogLogoutBinding
import com.live.emmazone.extensionfuncton.clearPreferences
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.LogoutResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.view_models.AppViewModel


class LogoutDialog : DialogFragment(), Observer<RestObservable> {

    private lateinit var binding: DialogLogoutBinding
    private val appViewModel: AppViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DialogLogoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnYes.setOnClickListener {
            appViewModel.logoutApi(requireActivity(), true)
            appViewModel.getResponse().observe(requireActivity(), this)
        }

        binding.btnNo.setOnClickListener {
            dismiss()
        }
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is LogoutResponse) {
                    val response: LogoutResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {
                        clearPreferences()
                        val intent = Intent(activity, UserLoginChoice::class.java)
                        startActivity(intent)
                        activity?.finishAffinity()
                    }
                }
            }
        }
    }
}