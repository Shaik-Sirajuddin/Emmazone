package com.live.emmazone.activities.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.live.emmazone.R
import com.live.emmazone.activities.main.Notifications
import com.live.emmazone.activities.provider.ProviderMainActivity
import com.live.emmazone.adapter.AdapterProviderShopDetailProducts
import com.live.emmazone.databinding.FragmentAddProductProviderBinding
import com.live.emmazone.model.ModelProShopDetailProducts

class FragmentProviderAddProduct : Fragment() {

    private lateinit var binding: FragmentAddProductProviderBinding
    private val list = ArrayList<ModelProShopDetailProducts>()
    private var isChecked = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddProductProviderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.checkBox.setOnClickListener {
            isChecked = !isChecked
            binding.checkBox.setImageResource(
                if (isChecked)
                    R.drawable.checkbox_g
                else
                    R.drawable.checkbox_tick
            )
        }

        binding.checkBoxLife.setOnClickListener {
            isChecked = !isChecked
            binding.checkBoxLife.setImageResource(
                if (isChecked)
                    R.drawable.checkbox_g
                else
                    R.drawable.checkbox_tick
            )
        }

        binding.checkOwnDelvry.setOnClickListener {
            isChecked = !isChecked
            binding.checkOwnDelvry.setImageResource(
                if (isChecked)
                    R.drawable.checkbox_g
                else
                    R.drawable.checkbox_tick
            )
        }


        binding.back.setOnClickListener {
            val intent = Intent(activity, ProviderMainActivity::class.java)
            startActivity(intent)
        }

        binding.imgNotify.setOnClickListener {
            val intent = Intent(activity, Notifications::class.java)
            startActivity(intent)
        }

        binding.rvAdProductProvider.layoutManager = GridLayoutManager(context, 2)

        list.add(
            ModelProShopDetailProducts(
                R.drawable.add_new_product, "", "",
                "", "", "",
                R.drawable.edit, R.drawable.bin1
            )
        )

        list.add(
            ModelProShopDetailProducts(
                R.drawable.sho1, "Bernd", "30.00€",
                "Lorem ipsum dolor", "Delivery estimate 4-5 days",
                "4.8",
                R.drawable.edit, R.drawable.bin1
            )
        )

        list.add(
            ModelProShopDetailProducts(
                R.drawable.sho2, "Bernd", "30.00€",
                "Lorem ipsum dolor", "Delivery estimate 4-5 days", "4.8",
                R.drawable.edit, R.drawable.bin1
            )
        )

        list.add(
            ModelProShopDetailProducts(
                R.drawable.sho3, "Bernd", "30.00€",
                "Lorem ipsum dolor", "Delivery estimate 4-5 days", "4.8",
                R.drawable.edit, R.drawable.bin1
            )
        )

        binding.rvAdProductProvider.adapter =
            AdapterProviderShopDetailProducts(requireContext(), list)

    }
}