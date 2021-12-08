package com.live.emmazone.activities.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.live.emmazone.MainActivity
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityMainBinding
import com.live.emmazone.databinding.ActivityMyEarningsBinding

class ActivityMainBindingFragmentProviderAccount : Fragment() {

    private var _binding: ActivityMainBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = ActivityMainBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}