package com.live.emmazone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.live.emmazone.activities.FragmentMyOrders
import com.live.emmazone.activities.fragment.FragmentAccount
import com.live.emmazone.activities.fragment.FragmentHome
import com.live.emmazone.activities.fragment.FragmentWishList
import com.live.emmazone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(FragmentHome())

        binding.bottomNavigationView.menu.findItem(R.id.home).isChecked = true
        binding.bottomNavigationView.itemIconTintList = null

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.wishList -> {
                    loadFragment(FragmentWishList())
                }
                R.id.myOrders -> {
                    loadFragment(FragmentMyOrders())
                }
                R.id.account -> {
                    loadFragment(FragmentAccount())
                }
                R.id.home -> {
                    loadFragment(FragmentHome())
                 //   binding.bottomNavigationView.menu.findItem(R.id.home).setIcon(R.drawable.home_selected)
                }
            }

            true
        }
    }
    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
     //   transaction.addToBackStack(null)
        transaction.commit()
    }

}