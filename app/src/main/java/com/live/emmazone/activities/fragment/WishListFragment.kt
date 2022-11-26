package com.live.emmazone.activities.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.MainActivity
import com.live.emmazone.R
import com.live.emmazone.activities.main.Cart
import com.live.emmazone.activities.main.Notifications
import com.live.emmazone.activities.main.ShopDetailActivity
import com.live.emmazone.activities.main.ShopReviewsActivity
import com.live.emmazone.adapter.AdapterWishList
import com.live.emmazone.databinding.FragmentWishlistBinding
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.AddFavouriteResponse
import com.live.emmazone.response_model.WishListResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.LocationUpdateUtilityFragment
import com.live.emmazone.view_models.AppViewModel

class WishListFragment : Fragment() {

    private lateinit var binding: FragmentWishlistBinding

    companion object {
        lateinit var notifyRedBG: ImageView
        lateinit var ivRedCartDot: ImageView
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWishlistBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickHandle()

    }


    private fun clickHandle() {
        notifyRedBG = binding.notifyRedBG
        ivRedCartDot = binding.ivRedCart

        binding.imageNotifications.setOnClickListener {
            val intent = Intent(activity, Notifications::class.java)
            startActivity(intent)
        }

        binding.cart.setOnClickListener {
            val intent = Intent(activity, Cart::class.java)
            startActivity(intent)
        }

        binding.tvProducts.setOnClickListener {
            productsClick()
        }

        binding.tvShops.setOnClickListener {
            shopsClick()
        }
        productsClick()
    }


    private fun productsClick() {
        openFragment(ProductWishListFragment())
        binding.tvShops.setBackgroundColor(Color.TRANSPARENT)
        binding.tvProducts.setBackgroundResource(R.drawable.bg_fill_earning)
    }

    private fun shopsClick() {
        openFragment(ShopWishListFragment())
        binding.tvShops.setBackgroundResource(R.drawable.bg_fill_earning)
        binding.tvProducts.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.containerWishList, fragment)
        transaction?.commit()
    }
}