package com.live.emmazone.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterProductReviews
import com.live.emmazone.databinding.ActivityProductReviewsBinding

import com.live.emmazone.model.ModelProductReviews

class ProductReviewsActivity : AppCompatActivity() {
    lateinit var binding : ActivityProductReviewsBinding
    private val list = ArrayList<ModelProductReviews>()
    lateinit var adapter : AdapterProductReviews

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvProductReviews.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        list.add(ModelProductReviews(R.drawable.shoes_square, "Brend Shoe", "4.5"))
        list.add(ModelProductReviews(R.drawable.winter, "Winter Sweeters", "4.5"))

        binding.rvProductReviews.adapter = AdapterProductReviews(list)

        binding.back.setOnClickListener {
            onBackPressed()
        }
        binding.btnSubmit.setOnClickListener {
            onBackPressed()
        }
    }
}