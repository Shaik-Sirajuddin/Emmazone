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
import androidx.lifecycle.Lifecycle
import com.live.emmazone.activities.auth.UserLoginChoice
import com.live.emmazone.activities.fragment.*
import com.live.emmazone.activities.provider.MessageActivity
import com.live.emmazone.databinding.ActivityMainBinding
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.response_model.NotificationListingResponse
import com.live.emmazone.response_model.WishListResponse
import com.live.emmazone.utils.AppConstants


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var homeFragment: HomeFragment
    lateinit var ordersFragment: FragmentMyOrders
    lateinit var accountFragment: AccountFragment
    lateinit var wishListFragment: WishListFragment
    lateinit var shopStoryFragment: ShopStoriesFragment
    lateinit var currentFragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeFragments()
        getNotificationClick()
        openMessagesOnFirebaseNotification()
//        binding.bottomNavigationView.menu.findItem(R.id.home).isChecked = true
        binding.bottomNavigationView.itemIconTintList = null
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.wishList -> {
                    if (getPreference(AppConstants.PROFILE_TYPE, "") == "guest") {
                        showLoginDialog()
                    } else {
                        if (currentFragment() !is WishListFragment)
                            loadFragment(wishListFragment)
                    }
                }
                R.id.myOrders -> {
                    if (getPreference(AppConstants.PROFILE_TYPE, "") == "guest") {
                        showLoginDialog()
                    } else {
                        if (currentFragment() !is FragmentMyOrders)
                            loadFragment(ordersFragment)
                    }

                }

                R.id.account -> {
                    if (getPreference(AppConstants.PROFILE_TYPE, "") == "guest") {
                        showLoginDialog()
                    } else {
                        if (currentFragment() !is AccountFragment)
                            loadFragment(accountFragment)
                    }

                }
                R.id.home -> {
                    if (currentFragment() !is HomeFragment)
                        loadFragment(homeFragment)
                    //   binding.bottomNavigationView.menu.findItem(R.id.home).setIcon(R.drawable.home_selected)
                }
                R.id.shopStory -> {
                    if (getPreference(AppConstants.PROFILE_TYPE, "") == "guest") {
                        showLoginDialog()
                    } else {
                        if (currentFragment() !is ShopStoriesFragment)
                            loadFragment(shopStoryFragment)
                    }
                }
            }
            true
        }
    }

    private fun initializeFragments() {
        wishListFragment = WishListFragment()
        homeFragment = HomeFragment()
        ordersFragment = FragmentMyOrders(null)
        accountFragment = AccountFragment()
        shopStoryFragment = ShopStoriesFragment()
        //ordersFragment
        addAndHideFragment(ordersFragment)
        //wishListFragment
        addAndHideFragment(wishListFragment)
        //accountFragment
        addAndHideFragment(accountFragment)
        //HomeFragment
        addAndHideFragment(shopStoryFragment)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, homeFragment)
            .commit()

        currentFragment = homeFragment
    }

    private fun addAndHideFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, fragment)
            .setMaxLifecycle(fragment, Lifecycle.State.CREATED)
            .hide(fragment).commit()
        currentFragment = fragment
    }

    private fun getNotificationClick() {
        if (intent.getSerializableExtra(AppConstants.NOTIFICATION_RESPONSE) != null) {
            val notificationResponse =
                intent.getSerializableExtra(AppConstants.NOTIFICATION_RESPONSE)
                        as NotificationListingResponse.Body
            binding.bottomNavigationView.menu.findItem(R.id.myOrders).isChecked = true
            ordersFragment = FragmentMyOrders(notificationResponse)
            addAndHideFragment(ordersFragment)
            loadFragment(ordersFragment)

        } else if (intent.getBooleanExtra(AppConstants.OPEN_BY_CART, false)) {
            binding.bottomNavigationView.menu.findItem(R.id.myOrders).isChecked = true
            ordersFragment = FragmentMyOrders(null)
            addAndHideFragment(ordersFragment)
            loadFragment(ordersFragment)
        } else {
            loadFragment(homeFragment)
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val currentFrag = currentFragment()
        val manager = supportFragmentManager
        manager.beginTransaction().hide(currentFrag).show(fragment).commit()
        manager.beginTransaction().setMaxLifecycle(currentFrag, Lifecycle.State.STARTED)
            .commit()
        manager.beginTransaction().setMaxLifecycle(fragment, Lifecycle.State.RESUMED).commit()
        currentFragment = fragment
    }

    private fun currentFragment(): Fragment {
        return currentFragment
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
            loadFragment(homeFragment)
            binding.bottomNavigationView.menu.findItem(R.id.home).isChecked = true
        } else {
            super.onBackPressed()
        }
    }

    //Open MessagesActivity on pop up firebase notification click
    private fun openMessagesOnFirebaseNotification() {
        val isFirebaseNotificationClick =
            intent.getBooleanExtra(AppConstants.IS_FIREBASE_NOTIFICATION, false)
        if (isFirebaseNotificationClick) {
            val intent = Intent(this, MessageActivity::class.java)
            startActivity(intent)
        }
    }
}


