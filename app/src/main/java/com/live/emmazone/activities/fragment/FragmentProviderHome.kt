package com.live.emmazone.activities.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.activities.provider.EditShopDetailActivity
import com.live.emmazone.adapter.AdapterProviderShopDetailProducts
import com.live.emmazone.adapter.AdapterShopDetailCategory
import com.live.emmazone.model.ModelProShopDetailProducts
import com.live.emmazone.model.ModelShopDetailCategory

class FragmentProviderHome  : Fragment(){
    val list = ArrayList<ModelShopDetailCategory>()
    val listProSDProducts = ArrayList<ModelProShopDetailProducts>()
    lateinit var adapter : AdapterShopDetailCategory
    lateinit var adapterProviderSDProducts : AdapterProviderShopDetailProducts

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      val view : View = LayoutInflater.from(context).inflate(R.layout.fragment_provider_home, container, false)

        val rv : RecyclerView = view.findViewById(R.id.recyclerProviderShopDetailCategory)
        val rv1 : RecyclerView = view.findViewById(R.id.recyclerProviderSDProducts)
        val imgEdit : ImageView = view.findViewById(R.id.image_editShop)

        imgEdit.setOnClickListener {
            val intent = Intent(activity, EditShopDetailActivity::class.java)
            startActivity(intent)
        }

        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        rv1.layoutManager = GridLayoutManager(context, 2)

        list.add(ModelShopDetailCategory(R.drawable.ic_baseline_add_24, "Add"))
        list.add(ModelShopDetailCategory(R.drawable.shoe_round, "Shoes"))
        list.add(ModelShopDetailCategory(R.drawable.gogg, "Googles"))
        list.add(ModelShopDetailCategory(R.drawable.shoe_round, "Timepiece"))
        list.add(ModelShopDetailCategory(R.drawable.clock3, "T Shirts"))

       rv.adapter = AdapterShopDetailCategory(list)

        listProSDProducts.add(
            ModelProShopDetailProducts(R.drawable.shoe_bernd, "Bernd", "30.00$",
                "Lorem ipsum dolor", "4.8", "Delivery estimate 4-5 days",
                R.drawable.edit, R.drawable.bin1))

        listProSDProducts.add(
            ModelProShopDetailProducts(R.drawable.shoes2, "Bernd", "30.00$",
                "Lorem ipsum dolor", "4.8", "Delivery estimate 4-5 days",
                R.drawable.edit, R.drawable.bin1))

        listProSDProducts.add(
            ModelProShopDetailProducts(R.drawable.shoe_bernd, "Bernd", "30.00$",
                "Lorem ipsum dolor", "4.8", "Delivery estimate 4-5 days",
                R.drawable.edit, R.drawable.bin1))

        listProSDProducts.add(
            ModelProShopDetailProducts(R.drawable.shoes2, "Bernd", "30.00$",
                "Lorem ipsum dolor", "4.8", "Delivery estimate 4-5 days",
                R.drawable.edit, R.drawable.bin1))

        rv1.adapter = AdapterProviderShopDetailProducts(listProSDProducts)

        return view
    }

}