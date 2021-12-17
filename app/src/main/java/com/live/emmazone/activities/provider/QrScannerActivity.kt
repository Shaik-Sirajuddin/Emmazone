package com.live.emmazone.activities.provider

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityQrScannerBinding
import com.live.emmazone.utils.ToastUtils
import com.permissionx.guolindev.PermissionX
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView

class QrScannerActivity : AppCompatActivity(), ZBarScannerView.ResultHandler {

    private lateinit var binding: ActivityQrScannerBinding
    private var scannerView: ZBarScannerView? = null

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, QrScannerActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { onBackPressed() }

        openCameraViewPermissionCheck()
    }

    private fun openCameraViewPermissionCheck() {
        PermissionX.init(this)
            .permissions(Manifest.permission.CAMERA)
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    "You need to allow camera permissions, to scan QR.",
                    "Allow",
                    "Deny"
                )
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    "You need to allow necessary permissions in Settings manually",
                    "Open Settings",
                    "Cancel"
                )
            }
            .request { allGranted, _, _ ->
                if (allGranted) {
                    initializeQRCamera()
                } else {
                    ToastUtils.showLongToast("Unable to perform action due to permissions. Please allow camera usage to scan QR")
                    finish()
                }
            }
    }

    private fun initializeQRCamera() {
        scannerView = ZBarScannerView(this)
        scannerView!!.setResultHandler(this)
        scannerView!!.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
        scannerView!!.setBorderColor(ContextCompat.getColor(this, R.color.green))
        scannerView!!.setLaserColor(ContextCompat.getColor(this, R.color.status_bar))
        scannerView!!.setBorderStrokeWidth(10)
        scannerView!!.setSquareViewFinder(true)
        scannerView!!.setupScanner()
        scannerView!!.setAutoFocus(true)
        startQRCamera()
        binding.containerScanner.addView(scannerView)
    }


    private fun startQRCamera() {
        scannerView?.startCamera()
    }

    private fun resetPreview() {
        scannerView?.stopCamera()
        scannerView?.startCamera()
        scannerView?.stopCameraPreview()
        scannerView?.resumeCameraPreview(this)
    }

    override fun onResume() {
        super.onResume()
        scannerView?.setResultHandler(this)
        scannerView?.startCamera()
    }

    override fun onPause() {
        super.onPause()
        scannerView?.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        scannerView?.stopCamera()
    }

    override fun handleResult(rawResult: Result?) {
        println(rawResult?.contents)
        ToastUtils.showToast("QR scanned successfully")
        finish()
    }
}