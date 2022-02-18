package com.live.emmazone

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.live.emmazone.activities.auth.UserLoginChoice
import com.live.emmazone.activities.fragment.AccountFragment
import com.live.emmazone.activities.fragment.HomeFragment
import com.live.emmazone.activities.fragment.FragmentMyOrders
import com.live.emmazone.activities.fragment.WishListFragment
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.databinding.ActivityMainBinding
import com.live.emmazone.extensionfuncton.getPreference


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(HomeFragment())

        binding.bottomNavigationView.menu.findItem(R.id.home).isChecked = true
        binding.bottomNavigationView.itemIconTintList = null

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.wishList -> {
                    if (getPreference(AppConstants.PROFILE_TYPE, "") == "guest") {
                        showLoginDialog()
                    } else {
                        if (currentFragment() !is WishListFragment)
                            loadFragment(WishListFragment())
                    }
                }
                R.id.myOrders -> {
                    if (getPreference(AppConstants.PROFILE_TYPE, "") == "guest") {
                        showLoginDialog()
                    } else {
                        if (currentFragment() !is FragmentMyOrders)
                            loadFragment(FragmentMyOrders())
                    }

                }

                R.id.account -> {
                    if (getPreference(AppConstants.PROFILE_TYPE, "") == "guest") {
                        showLoginDialog()
                    } else {
                        if (currentFragment() !is AccountFragment)
                            loadFragment(AccountFragment())
                    }

                }
                R.id.home -> {
                    if (currentFragment() !is HomeFragment)
                        loadFragment(HomeFragment())
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

    private fun currentFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.fragmentContainer)
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    fun showLoginDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,
        )
        dialog.setContentView(R.layout.dialog_login)
        //dialog.window?.setBackgroundDrawable(ContextCompat.getDrawable(this, android.R.color.transparent))

        val imgCross = dialog.findViewById<ImageView>(R.id.cross)
        val btnLogin = dialog.findViewById<Button>(R.id.btnLogin)

        imgCross.setOnClickListener {
            dialog.dismiss()
        }

        btnLogin.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, UserLoginChoice::class.java))
            finishAffinity()
        }

        dialog.show()

    }
}


