package com.live.emmazone.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        list.add(
            ModelRatingReviews(R.drawable.avtar, "Michle", "08-04-2018", "4.3",
            "lorem_ipsum_dolor_sit_amet_consectetur_edipscing_elit_sed_to_mode_tempor_incidedunt_ut_lebore_et_dolor_magna_aliqua_quis_ipsum_suspendisse"))

        list.add(
            ModelRatingReviews(R.drawable.avtar1, "Michle", "08-04-2018", "4.3",
                "lorem_ipsum_dolor_sit_amet_consectetur_edipscing_elit_sed_to_mode_tempor_incidedunt_ut_lebore_et_dolor_magna_aliqua_quis_ipsum_suspendisse"))

        list.add(
            ModelRatingReviews(R.drawable.avtar1, "Michle", "08-04-2018", "4.3",
                "lorem_ipsum_dolor_sit_amet_consectetur_edipscing_elit_sed_to_mode_tempor_incidedunt_ut_lebore_et_dolor_magna_aliqua_quis_ipsum_suspendisse lorem_ipsum_dolor_sit_amet_consectetur_edipscing_elit_sed_to_mode_tempor_incidedunt_ut_lebore_et_dolor_magna_aliqua_quis_ipsum_suspendisse"
            ))
    }
}