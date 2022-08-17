package com.live.emmazone.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.live.emmazone.adapter.AdapterFaqs
import com.live.emmazone.databinding.ActivityFaqBinding
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.FaqListResponse
import com.live.emmazone.view_models.AppViewModel

class FAQ : AppCompatActivity(), Observer<RestObservable> {

    var list= ArrayList<FaqListResponse.Body>()

    private val appViewModel: AppViewModel by viewModels()
    lateinit var binding : ActivityFaqBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appViewModel.faqListApi(this, true)
        appViewModel.getResponse().observe(this, this)
        binding.back.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is FaqListResponse) {
                    list.clear()
                    list.addAll(t.data.body)
                    binding.rvFaqList.adapter = AdapterFaqs(list)
                   /* if(list.size>0){
                        binding.tvNoNotification.visibility= View.GONE
                        binding.recyclerNotifications.visibility= View.VISIBLE

                    }else{
                        binding.tvNoNotification.visibility= View.VISIBLE
                        binding.recyclerNotifications.visibility= View.GONE
                    }*/


                }
            }
            else -> {}
        }

    }
}