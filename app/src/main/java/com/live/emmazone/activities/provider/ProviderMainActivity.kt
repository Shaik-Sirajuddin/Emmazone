package com.live.emmazone.activities.provider

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.live.emmazone.R
import com.live.emmazone.activities.fragment.*
import com.live.emmazone.databinding.ActivityProviderMainBinding

class ProviderMainActivity : AppCompatActivity() {
    lateinit var binding: ActivityProviderMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProviderMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        loadFragment(FragmentProviderHome())
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
                        loadFragment(FragmentProviderSale())
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

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentProviderContainer, fragment)
        transaction.commit()
    }

    private fun currentFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.fragmentProviderContainer)
    }

}