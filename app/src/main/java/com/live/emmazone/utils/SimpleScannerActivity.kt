package com.live.emmazone.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView


class SimpleScannerActivity : Activity(), ZXingScannerView.ResultHandler {

    private var mScannerView: ZXingScannerView? = null

    val TAG = "scanner"

    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        mScannerView = ZXingScannerView(this) // Programmatically initialize the scanner view
        setContentView(mScannerView) // Set the scanner view as the content view
    }

    public override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView!!.startCamera() // Start camera on resume
    }

    public override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera() // Stop camera on pause
    }

    override fun handleResult(rawResult: Result?) {
        // Do something with the result here
        Log.e(TAG, rawResult!!.text) // Prints scan results
        Log.v(
            TAG,
            rawResult.barcodeFormat.toString()
        ) // Prints the scan format (qrcode, pdf417 etc.)

        // If you would like to resume scanning, call this method below:
        mScannerView!!.resumeCameraPreview(this)

        if (!rawResult.text.isNullOrEmpty()) {

            Log.e(TAG, "ORDER ID: $rawResult.text")

            val intent = Intent()
            intent.putExtra(AppConstants.ORDER_ID, rawResult.text.toString())
            setResult(RESULT_OK, intent)
        }
        finish()
    }

}

