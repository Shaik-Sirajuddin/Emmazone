package com.live.emmazone.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityAddCardBinding
import com.live.emmazone.utils.AppUtils
import java.text.SimpleDateFormat
import java.util.*

class AddCardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCardBinding
    private lateinit var monthYearPickerDialog: MonthYearPickerDialogFragment
    private var selectedMonth = ""
    private var selectedYear = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clicksHandle()
        datePickDialog()
    }

    private fun clicksHandle() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.edtExpDate.setOnClickListener {
            monthYearPickerDialog.show(supportFragmentManager, "DateYearPicker")

        }

        binding.btnSubmit.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val name = binding.edtName.text.toString()
        val cardNo = binding.edtCardNo.text.toString()
    }

    private fun datePickDialog() {
        monthYearPickerDialog = MonthYearPickerDialogFragment
            .getInstance(
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.YEAR)
            )


        monthYearPickerDialog.setOnDateSetListener { year, monthOfYear ->
            val calendar = Calendar.getInstance()
            calendar[Calendar.YEAR] = year
            calendar[Calendar.MONTH] = monthOfYear
            longToMonthYear(calendar.timeInMillis)

        }
    }

    //------------------------Return Month and Year in String------------------//
    private fun longToMonthYear(timeInMillis: Long) {
        val dateFormatMonth = "MM"
        val dateFormatYear = "yyyy"
        val sdfMonth = SimpleDateFormat(dateFormatMonth, Locale.getDefault())
        val sdfYear = SimpleDateFormat(dateFormatYear, Locale.getDefault())
        selectedMonth = sdfMonth.format(timeInMillis)
        selectedYear = sdfYear.format(timeInMillis)
        binding.edtExpDate.setText("$sdfMonth/$sdfYear")
    }
}