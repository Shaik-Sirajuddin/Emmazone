package com.live.emmazone.activities.provider

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.live.emmazone.R
import com.live.emmazone.activities.fragment.FragmentProviderAddProduct
import com.live.emmazone.activities.fragment.FragmentProviderHome
import com.live.emmazone.activities.fragment.FragmentProviderSale
import com.live.emmazone.activities.fragment.ProviderAccountFragment
import com.live.emmazone.databinding.ActivityProviderMainBinding
import com.live.emmazone.response_model.NotificationListingResponse
import com.live.emmazone.utils.AppConstants

class ProviderMainActivity : AppCompatActivity() {
    lateinit var binding: ActivityProviderMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProviderMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getNotificationClick()

        binding.bottomNavigationView.itemIconTintList = null
        //   binding.bottomNavigationView.menu.findItem(R.id.home).isChecked = true

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.addProduct -> {
                    if (currentFragment() !is FragmentProviderAddProduct)
                        loadFragment(FragmentProviderAddProduct())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.sale -> {
                    if (currentFragment() !is FragmentProviderSale)
                        loadFragment(FragmentProviderSale(null))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.account_provider -> {
                    if (currentFragment() !is ProviderAccountFragment)
                        loadFragment(ProviderAccountFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.home_provider -> {
                    if (currentFragment() !is FragmentProviderHome)
                        loadFragment(FragmentProviderHome())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }


    }

    private fun getNotificationClick() {
        if (intent.getSerializableExtra(AppConstants.NOTIFICATION_RESPONSE) != null) {
            val notificationResponse =
                intent.getSerializableExtra(AppConstants.NOTIFICATION_RESPONSE)
                        as NotificationListingResponse.Body

            binding.bottomNavigationView.selectedItemId = R.id.sale
            loadFragment(FragmentProviderSale(notificationResponse))
        } else {
            loadFragment(FragmentProviderHome())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentProviderContainer, fragment)
        transaction.commit()
    }

    private fun currentFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.fragmentProviderContainer)
    }

    override fun onBackPressed() {

        if (currentFragment() !is FragmentProviderHome) {
            loadFragment(FragmentProviderHome())
            binding.bottomNavigationView.menu.findItem(R.id.home_provider).isChecked = true
        } else {
            super.onBackPressed()

        }
    }

}