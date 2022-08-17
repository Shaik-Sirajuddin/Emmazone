package com.live.emmazone.activities.provider

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.live.emmazone.adapter.AllTransactionAdapter
import com.live.emmazone.databinding.ActivityWithdrawal2Binding
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.interfaces.OnPopupClick
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.AllTransactionsResponse
import com.live.emmazone.response_model.GetBankResponse
import com.live.emmazone.response_model.WithdrawRequestResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel

class Withdrawal2Activity : AppCompatActivity(),
    Observer<RestObservable>, OnPopupClick {

    private lateinit var binding: ActivityWithdrawal2Binding

    private var list = ArrayList<AllTransactionsResponse.Body.Withdrawlist>()
    private lateinit var allTransactionAdapter: AllTransactionAdapter
    private var selectedBank: GetBankResponse.Body? = null

    private val appViewModel: AppViewModel by viewModels()

    private var balance = "0.0"

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                binding.tvSelectBankAcc.visibility = View.GONE
                binding.llBank.visibility = View.VISIBLE

                // There are no request codes
                val data: Intent? = result.data

                selectedBank =
                    data?.getSerializableExtra(AppConstants.SELECTED_BANK_ACCOUNT) as GetBankResponse.Body

                if (selectedBank != null) {
                    binding.tvBranch.text = selectedBank!!.bankBranch
                    binding.tvAccountNo.text =
                        "xxxx xxxx xxxx" + selectedBank!!.accountNumber.subSequence(
                            (selectedBank!!.accountNumber.length - 4),
                            selectedBank!!.accountNumber.length
                        )
                }


            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWithdrawal2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        clicksHandle()

        allTransactionAdapter = AllTransactionAdapter(list)
        binding.rvAllTransaction.adapter = allTransactionAdapter
        allTransactionApiHit()
    }

    private fun clicksHandle() {
        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.llBank.setOnClickListener {
            val intent = Intent(this, MyBankAccountActivity::class.java)
            resultLauncher.launch(intent)
        }

        binding.btnWithdrawNow.setOnClickListener {
            withdrawApiHit()
        }

        binding.tvSelectBankAcc.setOnClickListener {
            val intent = Intent(this, MyBankAccountActivity::class.java)
            resultLauncher.launch(intent)
        }


    }


    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 202) {
            binding.tvSelectBankAcc.visibility = View.GONE
            binding.llBank.visibility = View.VISIBLE
        }
    }*/

    private fun withdrawApiHit() {
        val amount = binding.edtAmount.text.toString().trim()

        if (Validator.validateWithdraw(
                balance.toDouble(),
                amount,
                selectedBank
            )
        ) {

            val hashMap = HashMap<String, String>()
            hashMap["amount"] = binding.edtAmount.text.toString().trim()
            hashMap["bankId"] = selectedBank!!.id.toString()

            appViewModel.withdrawRequestApi(this, hashMap, true)
            appViewModel.getResponse().observe(this, this)
        } else {
            AppUtils.showMsgOnlyWithoutClick(this, Validator.errorMessage)
        }
    }

    private fun allTransactionApiHit() {
        appViewModel.transactionApi(this, true)
        appViewModel.getResponse().observe(this, this)
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is WithdrawRequestResponse) {
                    val response: WithdrawRequestResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {
                        AppUtils.showMsgOnlyWithClick(this, "Withdraw request send", this)
                    }
                } else if (t.data is AllTransactionsResponse) {
                    val response: AllTransactionsResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {

                        if (response.body.balance.walletBalance.isNotEmpty()) {
                            balance = response.body.balance.walletBalance

                            binding.tvCtBalanceDollar.text =
                                "€" + String.format("%.02f", balance.toDouble())
                        } else {
                            binding.tvCtBalanceDollar.text = balance
                        }


                        list.clear()
                        list.addAll(response.body.withdrawlist)
                        allTransactionAdapter.notifyDataSetChanged()

                        if (list.size > 0) {
                            binding.llNoTransaction.visibility = View.GONE
                            binding.llAllTransaction.visibility = View.VISIBLE
                        } else {
                            binding.llNoTransaction.visibility = View.VISIBLE
                            binding.llAllTransaction.visibility = View.GONE
                        }
                    }
                }
            }
            else -> {}
        }
    }

    /*hide keyboard*/
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val v = currentFocus
        if (v != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) && v is EditText &&
            !v.javaClass.name.startsWith("android.webkit.")
        ) {
            val sourceCoordinates = IntArray(2)
            v.getLocationOnScreen(sourceCoordinates)
            val x = ev.rawX + v.getLeft() - sourceCoordinates[0]
            val y = ev.rawY + v.getTop() - sourceCoordinates[1]
            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom()) {
                AppUtils.hideSoftKeyboard(this)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onPopupClickListener() {
        finish()
    }
}