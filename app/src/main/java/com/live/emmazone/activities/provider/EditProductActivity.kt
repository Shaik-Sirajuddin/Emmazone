package com.live.emmazone.activities.provider

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.live.emmazone.R
import com.live.emmazone.activities.listeners.OnActionListener
import com.live.emmazone.adapter.ImageAdapter
import com.live.emmazone.databinding.ActivityEditProductBinding
import com.live.emmazone.model.ImageModel
import com.live.emmazone.utils.ImagePickerUtility
import com.live.emmazone.utils.ToastUtils
import com.permissionx.guolindev.PermissionX
import com.schunts.extensionfuncton.loadImage

class EditProductActivity : ImagePickerUtility() {

    private lateinit var images: ArrayList<ImageModel>
    var isNotifyOn = true

    lateinit var binding: ActivityEditProductBinding

    private var isMainPhoto = true

    private lateinit var imageAdapter: ImageAdapter
    private val imageList = ArrayList<String>()

    override fun selectedImage(imagePath: String?, code: Int) {
        if (imagePath != null) {
            if (code == 0) {
                //0 for Main Pic
                binding.ivShop.loadImage(imagePath)
            } else {
                imageList.add(imagePath)
                imageAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        images = arrayListOf()

        initListener()
        initAdapter()
    }


    private fun initAdapter() {
        imageAdapter = ImageAdapter(imageList)
        binding.recyclerImages.adapter = imageAdapter

        imageAdapter.onItemClickListener = { pos ->
            getImage(1, false)
        }
    }

    private fun initListener() {
        binding.apply {

            back.setOnClickListener { onBackPressed() }

            btnUpdate.setOnClickListener { showProductUpdateDialog() }

            ivShop.setOnClickListener {
                getImage(0, false)
            }
        }
    }

    private fun showProductUpdateDialog() {
        val alertBuilder = AlertDialog.Builder(this)
        val factory = LayoutInflater.from(this)
        val view: View = factory.inflate(R.layout.dialog_product_detail_updated, null)

        val buttonOk = view.findViewById<Button>(R.id.ok)

        buttonOk.setOnClickListener {
            onBackPressed()
        }

        alertBuilder.setView(view)
        alertBuilder.show()
    }

}