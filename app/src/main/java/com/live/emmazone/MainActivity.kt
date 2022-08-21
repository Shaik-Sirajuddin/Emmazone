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
import com.live.emmazone.activities.fragment.FragmentMyOrders
import com.live.emmazone.activities.fragment.HomeFragment
import com.live.emmazone.activities.fragment.WishListFragment
import com.live.emmazone.activities.provider.MessageActivity
import com.live.emmazone.databinding.ActivityMainBinding
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.response_model.NotificationListingResponse
import com.live.emmazone.utils.AppConstants


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getNotificationClick()
        openMessagesOnFirebaseNotification()
//        binding.bottomNavigationView.menu.findItem(R.id.home).isChecked = true
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
                            loadFragment(FragmentMyOrders(null))
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

    private fun getNotificationClick() {
        if (intent.getSerializableExtra(AppConstants.NOTIFICATION_RESPONSE) != null) {
            val notificationResponse =
                intent.getSerializableExtra(AppConstants.NOTIFICATION_RESPONSE)
                        as NotificationListingResponse.Body
            binding.bottomNavigationView.menu.findItem(R.id.myOrders).isChecked = true
            loadFragment(FragmentMyOrders(notificationResponse))

        } else if (intent.getBooleanExtra(AppConstants.OPEN_BY_CART, false)) {
            binding.bottomNavigationView.menu.findItem(R.id.myOrders).isChecked = true
            loadFragment(FragmentMyOrders(null))
        } else {
            loadFragment(HomeFragment())

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

    override fun onBackPressed() {
        if (currentFragment() !is HomeFragment) {
            loadFragment(HomeFragment())
            binding.bottomNavigationView.menu.findItem(R.id.home).isChecked = true
        } else {
            super.onBackPressed()
        }

    }
    //Open MessagesActivity on pop up firebase notification click
    private fun openMessagesOnFirebaseNotification(){
        val isFirebaseNotificationClick = intent.getBooleanExtra(AppConstants.IS_FIREBASE_NOTIFICATION,false)
        if(isFirebaseNotificationClick){
            val intent = Intent(this, MessageActivity::class.java)
            startActivity(intent)
        }
    }
}


