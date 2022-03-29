package com.live.emmazone.activities.provider

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.live.emmazone.databinding.ActivityAddBankAccountBinding
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.AddBankResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel

class AddBankAccountActivity : AppCompatActivity(), Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()

    lateinit var binding: ActivityAddBankAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBankAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.btnSaveAct.setOnClickListener {
            validateData()
        }

    }

    private fun validateData() {
        val ifsc = binding.edtIFSC.text.toString()
        val bankBranch = binding.edtBankBranch.text.toString()
        val accountNo = binding.edtActNumber.text.toString()
        val confirmAccountNo = binding.edtConfirmActNumber.text.toString()
        val name = binding.edtActHolderName.text.toString()

        if (Validator.addAccountValidation(ifsc, bankBranch, accountNo, confirmAccountNo, name)) {
            val hashMap = HashMap<String, String>()
            hashMap["ifscSwiftCode"] = ifsc
            hashMap["bankBranch"] = bankBranch
            hashMap["accountHolderName"] = name
            hashMap["accountNumber"] = accountNo

            appViewModel.addAccountApi(this, true, hashMap)
            appViewModel.getResponse().observe(this, this)

        } else {
            AppUtils.showMsgOnlyWithoutClick(this, Validator.errorMessage)
        }

    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is AddBankResponse) {
                    val response: AddBankResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {
                        setResult(RESULT_OK)
                        finish()
                    }
                }
            }
        }
    }
}