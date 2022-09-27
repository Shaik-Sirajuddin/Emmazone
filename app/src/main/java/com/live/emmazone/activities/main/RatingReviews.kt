package com.live.emmazone.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterRatingReviews
import com.live.emmazone.databinding.ActivityRatingReviewsBinding
import com.live.emmazone.model.ModelRatingReviews

class RatingReviews : AppCompatActivity() {
    lateinit var binding: ActivityRatingReviewsBinding
    val list = ArrayList<ModelRatingReviews>()
    lateinit var adapter : AdapterRatingReviews

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRatingReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.recyclerRatingReviews.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


    }
}