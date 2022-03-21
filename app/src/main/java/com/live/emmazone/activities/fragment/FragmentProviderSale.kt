package com.live.emmazone.activities.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.live.emmazone.R
import com.live.emmazone.activities.main.Notifications
import com.live.emmazone.databinding.FragmentSaleProviderBinding

class FragmentProviderSale : Fragment() {

    companion object{
        lateinit var imageRedDot:ImageView
    }

    private lateinit var binding: FragmentSaleProviderBinding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSaleProviderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clicksHandle()
        openSalesFragment(NewSalesProviderFragment())
    }

    private fun clicksHandle() {

        imageRedDot = binding.notifyRedBG

        binding.imageNotifications.setOnClickListener {
            val intent = Intent(activity, Notifications::class.java)
            startActivity(intent)
        }

        binding.newSale.setOnClickListener {
            openSalesFragment(NewSalesProviderFragment())
            binding.newSale.setBackgroundResource(R.drawable.bg_fill_earning)
            binding.newSale.setTextColor(Color.WHITE)
            binding.onGoingSale.setTextColor(Color.BLACK)
            binding.onGoingSale.setBackgroundColor(Color.TRANSPARENT)
            binding.pastSale.setTextColor(Color.BLACK)
            binding.pastSale.setBackgroundColor(Color.TRANSPARENT)
            binding.salesLayout.setBackgroundResource(R.drawable.bg_earning)
        }

        binding.onGoingSale.setOnClickListener {
            openSalesFragment(OnGoingSalesProviderFragment())
            binding.onGoingSale.setTextColor(Color.WHITE)
            binding.onGoingSale.setBackgroundResource(R.drawable.bg_fill_earning)
            binding.newSale.setTextColor(Color.BLACK)
            binding.newSale.setBackgroundColor(Color.TRANSPARENT)
            binding.salesLayout.setBackgroundResource(R.drawable.bg_earning)
            binding.pastSale.setTextColor(Color.BLACK)
            binding.pastSale.setBackgroundColor(Color.TRANSPARENT)
        }

        binding.pastSale.setOnClickListener {
            openSalesFragment(PastSalesProviderFragment())
            binding.onGoingSale.setTextColor(Color.BLACK)
            binding.onGoingSale.setBackgroundColor(Color.TRANSPARENT)
            binding.newSale.setTextColor(Color.BLACK)
            binding.salesLayout.setBackgroundResource(R.drawable.bg_earning)
            binding.newSale.setBackgroundColor(Color.TRANSPARENT)
            binding.pastSale.setTextColor(Color.WHITE)
            binding.pastSale.setBackgroundResource(R.drawable.bg_fill_earning)
        }

    }


    private fun openSalesFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragmentProviderSalesContainer, fragment)
        transaction?.commit()
    }

}