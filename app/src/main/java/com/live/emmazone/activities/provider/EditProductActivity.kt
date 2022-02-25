package com.live.emmazone.activities.provider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.live.emmazone.R
import com.live.emmazone.adapter.ImageAdapter
import com.live.emmazone.databinding.ActivityEditProductBinding
import com.live.emmazone.model.ImageModel
import com.live.emmazone.response_model.ShopDetailResponse
import com.live.emmazone.utils.ImagePickerUtility
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.loadImage

class EditProductActivity : ImagePickerUtility() {

    private lateinit var images: ArrayList<ImageModel>

    private val appViewModel: AppViewModel by viewModels()

    var productData: ShopDetailResponse.Body.Product?=null


    lateinit var binding: ActivityEditProductBinding

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

        productData= intent.extras?.get("productData") as ShopDetailResponse.Body.Product

        setData(productData!!)

        images = arrayListOf()



        initListener()
        initAdapter()
    }

    private fun setData(productData: ShopDetailResponse.Body.Product) {
        for(i in 0 until productData.product_images.size){
            imageList.add(productData.product_images[i].image)
        }
        binding.ivShop.loadImage(productData.mainImage)
        binding.edtShopName.setText(productData.name)
        binding.edtDesc.setText(productData.description)
        binding.edtProductPrice.setText(productData.product_price)
        binding.edtProductQ.setText(productData.product_quantity.toString())

        setImageAdapter()
    }
    private fun setImageAdapter() {
        imageAdapter = ImageAdapter(imageList)
        binding.recyclerImages.adapter = imageAdapter

        imageAdapter.onItemClickListener = { pos ->
            getImage(1, false)
        }
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