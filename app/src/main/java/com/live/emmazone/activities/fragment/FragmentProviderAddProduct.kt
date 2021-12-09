package com.live.emmazone.activities.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.live.emmazone.R

class FragmentProviderAddProduct : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val view : View = LayoutInflater.from(context).inflate(R.layout.fragment_add_product_provider, container, false)

        return view
    }

}