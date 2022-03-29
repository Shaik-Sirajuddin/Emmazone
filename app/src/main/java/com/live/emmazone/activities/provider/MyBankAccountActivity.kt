package com.live.emmazone.activities.provider

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.live.emmazone.R
import com.live.emmazone.adapter.MyBankAccountAdapter
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.DeleteBankResponse
import com.live.emmazone.response_model.GetBankResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel
import kotlinx.android.synthetic.main.activity_my_bank_account.*

class MyBankAccountActivity : AppCompatActivity(), Observer<RestObservable> {

    private lateinit var list: ArrayList<GetBankResponse.Body>
    private var myBankAccountAdapter: MyBankAccountAdapter? = null

    private val appViewModel: AppViewModel by viewModels()
    private var deleteBankPos: Int? = null
    private var selectedBankPos: Int? = null


    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                getBankApiHit()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_bank_account)

        clicksHandle()


        setBankAccountAdapter()
        setNoDataVisibility()
        getBankApiHit()

    }

    private fun clicksHandle() {
        back.setOnClickListener {
            onBackPressed()
        }

        ivAdd.setOnClickListener {
            val intent = Intent(this, AddBankAccountActivity::class.java)
            resultLauncher.launch(intent)
        }

        btnDone.setOnClickListener {
            if (selectedBankPos != null) {
                val intent = Intent()
                intent.putExtra(AppConstants.SELECTED_BANK_ACCOUNT, list[selectedBankPos!!])
                setResult(RESULT_OK, intent)
                finish()
            } else {
                AppUtils.showMsgOnlyWithoutClick(this, getString(R.string.pls_select_bank))
            }
        }
    }

    private fun setBankAccountAdapter() {
        list = ArrayList()
        myBankAccountAdapter = MyBankAccountAdapter(list)
        rvMyBankAccount.adapter = myBankAccountAdapter

        myBankAccountAdapter?.onItemClick = { pos, clickOn ->
            if (clickOn == "delete") {
                deleteBankPos = pos
                deleteBankApiHit(list[pos].id.toString())
            } else if (clickOn == "itemClick") {
                list.forEachIndexed { index, body ->
                    if (index == pos) {
                        body.isSelected = true
                        selectedBankPos = pos
                    } else {
                        body.isSelected = false
                    }
                }

                myBankAccountAdapter?.notifyDataSetChanged()
            }
        }
    }

    private fun deleteBankApiHit(bankId: String) {
        val hashMap = HashMap<String, String>()
        hashMap["id"] = bankId

        appViewModel.deleteBank(this, hashMap, true)
        appViewModel.getResponse().observe(this, this)
    }


    private fun setNoDataVisibility() {
        if (list.size > 0) {
            tvNoData.visibility = View.GONE
            llData.visibility = View.VISIBLE
        } else {
            tvNoData.visibility = View.VISIBLE
            llData.visibility = View.GONE
        }

    }

    private fun getBankApiHit() {
        appViewModel.getBankApi(this, true)
        appViewModel.getResponse().observe(this, this)
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is GetBankResponse) {

                    val response: GetBankResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {
                        list.clear()
                        list.addAll(response.body)
                        myBankAccountAdapter?.notifyDataSetChanged()
                        setNoDataVisibility()

                    }
                } else if (t.data is DeleteBankResponse) {
                    val response: DeleteBankResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {
                        list.removeAt(deleteBankPos!!)
                        myBankAccountAdapter?.notifyDataSetChanged()
                        AppUtils.showMsgOnlyWithoutClick(this, response.message)

                    }
                }
            }
        }
    }

}