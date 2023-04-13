package com.live.emmazone.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import com.live.emmazone.databinding.ActivityPrivacyPolicyBinding
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.PrivacyPolicyResponse
import com.live.emmazone.response_model.TermsConditionResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.view_models.AppViewModel

class PrivacyPolicy : AppCompatActivity(), Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()
    lateinit var binding: ActivityPrivacyPolicyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivacyPolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.back.setOnClickListener {
            onBackPressed()
        }

        appViewModel.privacyPolicyApi(this,true)
        appViewModel.getResponse().observe(this,this)
    }

    override fun onChanged(t: RestObservable?) {
        when (t?.status) {
            Status.SUCCESS -> {
                if (t.data is PrivacyPolicyResponse) {
                    val response: PrivacyPolicyResponse = t.data
                    if (response.code == AppConstants.SUCCESS_CODE) {
                        binding.tvPrivacyPolicy.text =
                            HtmlCompat.fromHtml(
                                response.body.content,
                                HtmlCompat.FROM_HTML_MODE_LEGACY
                            ).toString()
                        Log.d("privacy" , HtmlCompat.fromHtml(
                            response.body.content,
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                        ).toString())
                    }
                }
            }

            else -> {}
        }
    }

}