package com.live.emmazone.activities.fragment

import android.app.Notification
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.activities.main.Notifications
import com.live.emmazone.activities.provider.ProviderMainActivity
import com.live.emmazone.adapter.AdapterProviderShopDetailProducts
import com.live.emmazone.model.ModelProShopDetailProducts

class FragmentProviderAddProduct : Fragment() {
  lateinit var adapter : AdapterProviderShopDetailProducts
  val list = ArrayList<ModelProShopDetailProducts>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val view : View = LayoutInflater.from(context).inflate(R.layout.fragment_add_product_provider, container, false)

        val rv : RecyclerView = view.findViewById(R.id.rvAdProductProvider)
        val imgNotifictaion : ImageView = view.findViewById(R.id.imgNotify)
        val back : ImageView = view.findViewById(R.id.back)

        back.setOnClickListener {
            val intent = Intent(activity, ProviderMainActivity::class.java)
            startActivity(intent)
        }

        imgNotifictaion.setOnClickListener {
            val intent = Intent(activity, Notifications::class.java)
            startActivity(intent)
        }

        rv.layoutManager = GridLayoutManager(context, 2)

        list.add(
            ModelProShopDetailProducts(R.drawable.add_new_product, "", "",
                "", "", "",
                R.drawable.edit, R.drawable.bin1))

        list.add(
            ModelProShopDetailProducts(R.drawable.shoes2, "Bernd", "30.00€",
                "Lorem ipsum dolor", "Delivery estimate 4-5 days",
                "4.8",
                R.drawable.edit, R.drawable.bin1))

        list.add(
            ModelProShopDetailProducts(R.drawable.shoe_bernd, "Bernd", "30.00€",
                "Lorem ipsum dolor", "Delivery estimate 4-5 days", "4.8",
                R.drawable.edit, R.drawable.bin1))

        list.add(
            ModelProShopDetailProducts(R.drawable.shoes2, "Bernd", "30.00€",
                "Lorem ipsum dolor", "Delivery estimate 4-5 days", "4.8",
                R.drawable.edit, R.drawable.bin1))

        rv.adapter = AdapterProviderShopDetailProducts(requireContext(), list)

        return view
    }

}