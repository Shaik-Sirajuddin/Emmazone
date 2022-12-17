package com.live.emmazone.activities.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.live.emmazone.activities.ImageGetter
import com.live.emmazone.activities.provider.AddShopStory
import com.live.emmazone.databinding.FragmentShopStoriesProviderBinding
import com.live.emmazone.utils.ImagePickerUtility

class ShopStoriesProviderFragment : Fragment() {

    private lateinit var binding: FragmentShopStoriesProviderBinding
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imagePath =  result.data?.getStringExtra("imagePath")
                if(!imagePath.isNullOrEmpty()){
                    val intent = Intent(requireContext(),AddShopStory::class.java)
                    startActivity(intent)
                }
            }
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopStoriesProviderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.addStory.setOnClickListener {
            launcher.launch(Intent(requireContext(), ImageGetter::class.java))
        }
    }
}