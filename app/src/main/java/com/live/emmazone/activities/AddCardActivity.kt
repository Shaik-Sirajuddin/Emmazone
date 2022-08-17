package com.live.emmazone.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityAddCardBinding
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.interfaces.OnPopupClick
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.AddCardResponse
import com.live.emmazone.response_model.CardListResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class AddCardActivity : AppCompatActivity(),Observer<RestObservable>,OnPopupClick {

    private val appViewModel: AppViewModel by viewModels()
    private lateinit var binding: ActivityAddCardBinding
    private lateinit var monthYearPickerDialog: MonthYearPickerDialogFragment
    private var selectedMonth = ""
    private var selectedYear = ""
    private var cardType = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clicksHandle()
        datePickDialog()
        cardListener()
    }

    //..............listener function to get card type...........................
    fun cardListener() {
        binding.etCardNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                val ccNum = s.toString()

                if (ccNum.length >= 2) {
                    for (i in 0 until AppUtils.cardTypeModelSet()!!.size) {
                        if (ccNum.substring(0, 2)
                                .matches(AppUtils.cardTypeModelSet()!!.get(i).regx.toRegex())
                        ) {
                            cardType = AppUtils.cardTypeModelSet()!!.get(i).getType()
                            if (cardType.equals("0")) {
                                binding.etCardNumber.setCompoundDrawablesWithIntrinsicBounds(
                                    R.drawable.ic_visa,
                                    0,
                                    0,
                                    0
                                )
                            } else if (cardType.equals("1")) {
                                binding.etCardNumber.setCompoundDrawablesWithIntrinsicBounds(
                                    R.drawable.ic_mastercard,
                                    0,
                                    0,
                                    0
                                )
                            }
                            Log.e(
                                "card_type_and_name",
                                AppUtils.cardTypeModelSet()!!.get(i).getType().toString() + "," +
                                        AppUtils.cardTypeModelSet()!!.get(i).getName()
                            )
                        }

                    }
                } else {
                    cardType = ""
                    if (cardType.equals("0")) {
                        binding.etCardNumber.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_visa,
                            0,
                            0,
                            0
                        )
                    } else if (cardType.equals("1")) {
                        binding.etCardNumber.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_mastercard,
                            0,
                            0,
                            0
                        )
                    } else {
                        binding.etCardNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                    }
                    Log.e("card_type_and_name", " ")
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val ccNum = s.toString()
                if (ccNum.length >= 2) {


                }
                if (!binding.etCardNumber.text.toString().equals("")) {
                } else {
                    cardType = ""
                    if (cardType == "0") {
                        binding.etCardNumber.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_visa,
                            0,
                            0,
                            0
                        )
                    } else if (cardType == "1") {
                        binding.etCardNumber.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_mastercard,
                            0,
                            0,
                            0
                        )
                    } else {
                        binding.etCardNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                    }
                    Log.e("card_type_and_name", " ")

                }
            }
        })
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
        val edtCardNo = binding.etCardNumber

        if (Validator.addCardValidation(name, edtCardNo, selectedMonth)) {

            val hashMap = HashMap<String, String>()
            hashMap["cardType"] = cardType
            hashMap["name"] = name
            hashMap["name"] = name
            hashMap["cardNumber"] = edtCardNo.text.toString()
            hashMap["month"] = selectedMonth
            hashMap["year"] = selectedYear

            appViewModel.addCardApi(this, true, hashMap)
            appViewModel.getResponse().observe(this, this)

        } else AppUtils.showMsgOnlyWithoutClick(this, Validator.errorMessage)

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
        binding.edtExpDate.setText("$selectedMonth/$selectedYear")
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is AddCardResponse) {
                    val response: AddCardResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {
                        AppUtils.showMsgOnlyWithClick(this,response.message,this)
                    }
                }
            }
            else -> {}
        }
    }

    override fun onPopupClickListener() {
        setResult(RESULT_OK)
        finish()
    }
}