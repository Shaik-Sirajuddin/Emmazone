package com.live.emmazone

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.live.emmazone.activities.TermsCondition
import com.live.emmazone.activities.auth.LoginActivity
import com.live.emmazone.activities.auth.SignUpActivity
import com.live.emmazone.activities.fragment.FragmentMyOrders
import com.live.emmazone.activities.fragment.FragmentAccount
import com.live.emmazone.activities.fragment.FragmentHome
import com.live.emmazone.activities.fragment.FragmentWishList
import com.live.emmazone.activities.main.DeliveryAddress
import com.live.emmazone.activities.main.PaymentMethod
import com.live.emmazone.databinding.ActivityMainBinding
import com.live.emmazone.utils.helper.getProfileType

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(FragmentHome())

        binding.bottomNavigationView.menu.findItem(R.id.home).isChecked = true
        binding.bottomNavigationView.itemIconTintList = null

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.wishList -> {
                    if (getProfileType() == "guest") {
                        showLoginOption()
                    } else
                        loadFragment(FragmentWishList())
                }
                R.id.myOrders -> {
                    if (getProfileType() == "guest") {
                        showLoginOption()
                    } else
                        loadFragment(FragmentMyOrders())
                }

                R.id.account -> {
                    if (getProfileType() == "guest") {
                        showLoginOption()
                    } else
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

    override fun onBackPressed() {
        finishAffinity()
    }

    fun showLoginOption() {

        val dialog = Dialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_login, null)

        val imgCross = view.findViewById<ImageView>(R.id.cross)
        val btnSignUp = view.findViewById<Button>(R.id.btnSignUp)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)

        imgCross.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()

    }
}


