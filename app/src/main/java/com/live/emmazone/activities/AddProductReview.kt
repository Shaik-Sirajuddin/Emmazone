package com.live.emmazone.activities

import android.os.Bundle
import android.view.View
import android.widget.RatingBar.OnRatingBarChangeListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.live.emmazone.databinding.ActivityAddProductReviewBinding
import com.live.emmazone.interfaces.OnPopupClick
import com.live.emmazone.net.RestObservable
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.response_model.ProductReviewModel
import com.live.emmazone.response_model.ProductReviewResponse
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.toBody
import kotlinx.android.synthetic.main.activity_add_product_review.*
import okhttp3.RequestBody
import kotlin.math.min
import kotlin.math.roundToInt


class AddProductReview : AppCompatActivity(), Observer<RestObservable> {
    lateinit var binding : ActivityAddProductReviewBinding
    var productId : String = ""
    private val appViewModel: AppViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        productId = intent.getStringExtra("id")!!
        ratingBar.onRatingBarChangeListener =
            OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                if (rating < 1.0f) ratingBar.rating = 1.0f
            }
        binding.back.setOnClickListener {
            finish()
        }
        binding.saveReview.setOnClickListener {
            updateReview()
        }

        getData()
    }

    private fun updateReview() {
        var rating = ratingBar.rating.roundToInt()
        rating = min(rating,5)
        val comment = binding.comment.text.toString().trim()

        val hashMap = HashMap<String,RequestBody>()
        hashMap["productId"] = toBody(productId)
        hashMap["rating"] = toBody(rating.toString())
        hashMap["comment"] = toBody(comment)
        //binding.progressBar.visibility = View.VISIBLE
        appViewModel.addProductReview(this,true,hashMap)
        appViewModel.getResponse().observe(this,this)
    }

    private fun getData(){
        val hashMap = HashMap<String,RequestBody>()
        hashMap["productId"] = toBody(productId)
        appViewModel.getMyProductReview(this,true,hashMap)
        appViewModel.getResponse().observe(this,this)
    }
    private fun setData(model : ProductReviewModel?){
        if(model == null)return
        binding.ratingBar.rating = model.rating.toFloat()
        binding.comment.setText(model.comment)
    }
    override fun onChanged(t: RestObservable?) {
        binding.progressBar.visibility = View.GONE
        if(t==null)return
        if(t.data is ProductReviewResponse){
            if(t.data.review!=null)
            setData(t.data.review)
        }
        else if(t.data is CommonResponse){
            AppUtils.showMsgOnlyWithClick(this,t.data.message,object : OnPopupClick{
                override fun onPopupClickListener() {
                    finish()
                }
            })
        }
    }
}