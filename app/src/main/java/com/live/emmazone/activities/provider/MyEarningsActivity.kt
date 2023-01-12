package com.live.emmazone.activities.provider

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.text.style.DynamicDrawableSpan.ALIGN_CENTER
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterMyEarnings
import com.live.emmazone.databinding.ActivityMyEarningsBinding
import com.live.emmazone.model.ModelMyEarnings
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.MyEarningResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.AppUtils.Companion.showToast
import com.live.emmazone.view_models.AppViewModel
import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Workbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class MyEarningsActivity : AppCompatActivity(), Observer<RestObservable> {
    lateinit var binding: ActivityMyEarningsBinding
    lateinit var adapter: AdapterMyEarnings
    val list = ArrayList<ModelMyEarnings>()
    val orders = ArrayList<MyEarningResponse.Body.FindOrder>()
    private val appViewModel: AppViewModel by viewModels()

    private lateinit var workbook: HSSFWorkbook
    private lateinit var sheet: HSSFSheet
    private lateinit var cell: Cell
    private lateinit var headerCellStyle: HSSFCellStyle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyEarningsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = AdapterMyEarnings(orders)
        binding.rvMyEarnings.adapter = adapter

        clicksHandle()
        earningApiHit("1")
    }

    private fun clicksHandle() {
        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.tvToday.setOnClickListener {
            todayClk()
        }

        binding.tvWeekly.setOnClickListener {
            weeklyClick()
        }
        binding.exportButton.setOnClickListener {
            if (exportDataIntoWorkbook()) {
                showToast("File saved in downloads directory")
            } else {
                showToast("Unknown error occured while saving file")
            }
        }
    }

    private fun openWithdrawalActivity() {
        val intent = Intent(this, Withdrawal2Activity::class.java)
        startActivity(intent)
    }

    private fun todayClk() {
        binding.tvToday.background =
            ContextCompat.getDrawable(this, R.drawable.bg_fill_earning)
        binding.tvWeekly.setBackgroundColor(Color.TRANSPARENT)

        earningApiHit("1") //1 for ToDay Earnings
    }


    private fun weeklyClick() {
        binding.tvWeekly.background =
            ContextCompat.getDrawable(this, R.drawable.bg_fill_earning)
        binding.tvToday.setBackgroundColor(Color.TRANSPARENT)

        earningApiHit("2") //2 for Weekly Earning
    }

    private fun earningApiHit(status: String) {
        val hashMap = HashMap<String, String>()
        hashMap["status"] = status

        appViewModel.myEarning(this, hashMap, true)
        appViewModel.getResponse().observe(this, this)
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is MyEarningResponse) {
                    val response: MyEarningResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {
                        binding.totalEarning.text =
                            getString(R.string.euro_symbol, response.body.totalEarning.toString())

                        binding.totalProducts.text = response.body.totalProduct.toString()
                        orders.clear()
                        orders.addAll(response.body.findOrder)
                        adapter.notifyDataSetChanged()
                    }

                }
            }
            else -> {}
        }
    }

    private fun getOrderStatus(status: Int): String {
        when (status) {
            0 -> { //  order status  0-> Pending  1-> on the way 2-> Delivered 3-> cancelled
                //7-> Return in transit // 8-> Returned
                return getString(R.string.pending)
            }
            1 -> {
                return getString(R.string.on_the_way)
            }
            2 -> {
                return getString(R.string.completed)
            }
            3 -> {
                return getString(R.string.cancel)
            }
            7 -> {
                return getString(R.string.return_in_transit)
            }
            8 -> {
                return getString(R.string.returned)
            }
        }
        return ""
    }


    private fun fillDataIntoExcel() {
        for (i in orders.indices) {
            val model = orders[i]
            // Create a New Row for every new entry in list
            val rowData: Row = sheet.createRow(i + 1)
            val orderNo = model.orderNo
            val amount = model.netAmount

            val orderDate = AppUtils.secondsToTime(model.created.toLong(), AppConstants.DATE_FORMAT)
            // Create Cells for each row
            cell = rowData.createCell(0)
            cell.setCellValue(orderNo)
            cell = rowData.createCell(1)
            cell.setCellValue(orderDate)
            cell = rowData.createCell(2)
            cell.setCellValue(amount)
            cell = rowData.createCell(3)
            cell.setCellValue(getOrderStatus(model.orderStatus))
        }
    }


    private fun exportDataIntoWorkbook(

    ): Boolean {

        // Check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            showToast("Storage not available or read only")
            return false
        }

        // Creating a New HSSF Workbook (.xls format)
        workbook = HSSFWorkbook()
        setHeaderCellStyle()

        // Creating a New Sheet and Setting width for each column
        sheet = workbook.createSheet("Earnings_Emmazone.xls")
        sheet.setColumnWidth(0, 15 * 400)
        sheet.setColumnWidth(1, 15 * 400)
        sheet.setColumnWidth(2, 15 * 400)
        sheet.setColumnWidth(3, 15 * 400)
        setHeaderRow()
        fillDataIntoExcel()
        return storeExcelInStorage(this, "Earnings_Emmazone.xls")
    }

    /**
     * Setup header cell style
     */
    private fun setHeaderCellStyle() {
        headerCellStyle = workbook.createCellStyle();
//        headerCellStyle.fillForegroundColor = HSSFColor.AQUA.index;

    }

    /**
     * Setup Header Row
     */
    private fun setHeaderRow() {
        val data: Row = sheet.createRow(0)
        cell = data.createCell(0)
        cell.setCellValue("Order No")
        cell = data.createCell(1)
        cell.setCellValue("Order Date")
        cell = data.createCell(2)
        cell.setCellValue("Net Amount")
        cell = data.createCell(3)
        cell.setCellValue("Status")
    }

    private fun isExternalStorageReadOnly(): Boolean {
        val externalStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED_READ_ONLY == externalStorageState
    }

    private fun isExternalStorageAvailable(): Boolean {
        val externalStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == externalStorageState
    }

    /**
     * Store Excel Workbook in external storage
     *
     * @param context  - application context
     * @param fileName - name of workbook which will be stored in device
     * @return boolean - returns state whether workbook is written into storage or not
     */
    private fun storeExcelInStorage(
        context: Context,
        fileName: String
    ): Boolean {
        var isSuccess: Boolean
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            fileName
        )
        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream = FileOutputStream(file)
            workbook.write(fileOutputStream)
            Log.e("Excel", "Writing file$file")
            isSuccess = true
        } catch (e: IOException) {
            Log.e("Excel", "Error writing Exception: ", e)
            isSuccess = false
        } catch (e: Exception) {
            Log.e("Excel", "Failed to save file due to Exception: ", e)
            isSuccess = false
        } finally {
            try {
                fileOutputStream?.close()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        return isSuccess
    }

}