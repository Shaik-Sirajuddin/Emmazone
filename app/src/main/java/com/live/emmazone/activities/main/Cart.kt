package com.live.emmazone.activities.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterCart
import com.live.emmazone.adapter.AdapterShopDetailProducts
import com.live.emmazone.databinding.ActivityCartBinding
import com.live.emmazone.model.ModelCart
import com.live.emmazone.model.ModelShopDetailProducts

class Cart : AppCompatActivity(), OnItemClick {
    lateinit var binding : ActivityCartBinding
    lateinit var adapter : AdapterCart
    val list = ArrayList<ModelCart>()
    val listMayLike = ArrayList<ModelShopDetailProducts>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.recyclerCart.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerCartMayLike.layoutManager = GridLayoutManager(this, 2)

        list.add(ModelCart(R.drawable.green, R.drawable.bin, "Brend Shoes", "30.00$"))
        list.add(ModelCart(R.drawable.green, R.drawable.bin, "Winter Sweeters", "30.00$"))

        binding.recyclerCart.adapter = AdapterCart(list)

        listMayLike.add(ModelShopDetailProducts(R.drawable.shoe_bernd, "Bernd", "30.00$", "Lorem ipsum dolor",
            "4.8", "Delivery estimate 4-5 days"))

        listMayLike.add(ModelShopDetailProducts(R.drawable.shoes2, "Matrix", "30.00$", "Lorem ipsum dolor",
            "4.8", "Delivery estimate 4-5 days"))

        binding.recyclerCartMayLike.adapter = AdapterShopDetailProducts(listMayLike, this)

//        private void showBottomSheetDialog() {
//
//            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
//            bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);
//
//            LinearLayout copy = bottomSheetDialog.findViewById(R.id.copyLinearLayout);
//            LinearLayout share = bottomSheetDialog.findViewById(R.id.shareLinearLayout);
//            LinearLayout upload = bottomSheetDialog.findViewById(R.id.uploadLinearLayout);
//            LinearLayout download = bottomSheetDialog.findViewById(R.id.download);
//            LinearLayout delete = bottomSheetDialog.findViewById(R.id.delete);
//
//            bottomSheetDialog.show();
//        }

    }

    override fun onCellClickListener() {
        val intent = Intent(this, ProductDetailActivity::class.java)
        startActivity(intent)
    }
}