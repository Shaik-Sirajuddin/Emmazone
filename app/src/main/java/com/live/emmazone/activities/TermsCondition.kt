package com.live.emmazone.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityTermsConditionBinding
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.LoginResponse
import com.live.emmazone.response_model.TermsConditionResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.view_models.AppViewModel

class TermsCondition : AppCompatActivity(), Observer<RestObservable> {

    lateinit var binding: ActivityTermsConditionBinding
    private val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermsConditionBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.back.setOnClickListener {
            onBackPressed()
        }


        appViewModel.termsConditionApi(this,true)
        appViewModel.getResponse().observe(this,this)
    }

    override fun onChanged(t: RestObservable?) {
        when (t?.status) {
            Status.SUCCESS -> {
                if (t.data is TermsConditionResponse) {
                    val response: TermsConditionResponse = t.data
                    if (response.code == AppConstants.SUCCESS_CODE) {

                        binding.tvTermsCondition.text =
                            HtmlCompat.fromHtml(
                                response.body.content,
                                HtmlCompat.FROM_HTML_MODE_LEGACY
                            ).toString()

                    }
                }
            }

            else -> {}
        }
    }
}