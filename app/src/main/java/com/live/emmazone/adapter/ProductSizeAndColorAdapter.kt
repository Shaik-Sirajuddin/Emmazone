package com.live.emmazone.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.model.SizeAndColorItem

class ProductSizeAndColorAdapter(
    val context: Context,
    val list:ArrayList<SizeAndColorItem>,
    val onClick:(pos:Int)->Unit
): RecyclerView.Adapter<ProductSizeAndColorViewHolder>() {
    var selectedPos = 0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductSizeAndColorViewHolder {
        val layout = LayoutInflater.from(context).inflate(R.layout.layout_item_color_or_size,parent,false)
        val viewHolder = ProductSizeAndColorViewHolder(layout)
        layout.setOnClickListener {
            val pre = selectedPos
            selectedPos = viewHolder.adapterPosition
            notifyItemChanged(pre)
            notifyItemChanged(selectedPos)
            if(pre!=selectedPos)
            onClick(selectedPos)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ProductSizeAndColorViewHolder, position: Int) {
        holder.name.text = list[position].name
        if(selectedPos == position){
            holder.name.typeface = Typeface.DEFAULT_BOLD
        }
        else{
            holder.name.typeface = Typeface.DEFAULT
        }
    }

    override fun getItemCount(): Int = list.size
}
class ProductSizeAndColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val name: TextView = itemView.findViewById(R.id.name)
}