package com.live.emmazone.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.databinding.ItemAddColorSizeBinding
import com.live.emmazone.model.ColorSizeModel

class ColorSizeAdapter(val list: ArrayList<ColorSizeModel>) :
    RecyclerView.Adapter<ColorSizeAdapter.ColorSizeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorSizeViewHolder {

        val binding = ItemAddColorSizeBinding.inflate(LayoutInflater.from(parent.context))

//        val view =
//            LayoutInflater.from(parent.context).inflate(R.layout.item_add_color_size, parent, false)
        return ColorSizeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ColorSizeViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ColorSizeViewHolder(val binding: ItemAddColorSizeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pos: Int) {
            val colorSizeModel = list[pos]

            binding.edtColorSize.setText(colorSizeModel.text)

            binding.edtColorSize.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    colorSizeModel.text = s.toString()
                }

            })

        }
    }

}