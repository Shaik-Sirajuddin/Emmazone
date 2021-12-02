package com.live.emmazone.activities

import android.app.Notification
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.activities.main.Cart
import com.live.emmazone.activities.main.Notifications
import com.live.emmazone.adapter.AdapterWishList
import com.live.emmazone.model.ModelWishList

class FragmentWishList : Fragment() {
    var list = ArrayList<ModelWishList>()
    lateinit var adapter: AdapterWishList
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         val view = LayoutInflater.from(context).inflate( R.layout.fragment_wishlist, container, false)

        recyclerView = view.findViewById(R.id.recyclerWishList)
        val notifications = view.findViewById<ImageView>(R.id.image_notifications)
        val cart = view.findViewById<ImageView>(R.id.cart)

        cart.setOnClickListener {
            val intent = Intent(activity, Cart::class.java)
            startActivity(intent)
        }

        notifications.setOnClickListener {
            val intent = Intent(activity, Notifications::class.java)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,
            false)

        list.add(ModelWishList(R.drawable.img1, R.drawable.heart, "Berserka Store",
        R.drawable.rating, "4.5/5", R.drawable.location, "03 miles away"))

        list.add(ModelWishList(R.drawable.img2, R.drawable.heart, "Curve Store",
            R.drawable.rating, "4.5/5", R.drawable.location, "03 miles away"))

        list.add(ModelWishList(R.drawable.img1, R.drawable.heart, "Berserka Store",
            R.drawable.rating, "4.5/5", R.drawable.location, "03 miles away"))

        recyclerView.adapter = AdapterWishList(list)

        return view
    }

}