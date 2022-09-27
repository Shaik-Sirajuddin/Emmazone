package com.live.emmazone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.model.ProductVariant
import com.live.emmazone.response_model.Product
import kotlinx.android.synthetic.main.item_product_variant.view.*
import java.text.DecimalFormat
import kotlin.math.roundToInt

class ProductVariantAdapter(
    val list: ArrayList<Product>,
    val context: Context,
    val editItem : (pos:Int) -> Unit,
    val deleteItem : (pos:Int) -> Unit,
    val addItem : () -> Unit
) :
    RecyclerView.Adapter<ProductVariantAdapter.ProductVariantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVariantViewHolder {
        val layout =
            LayoutInflater.from(context).inflate(R.layout.item_product_variant, parent, false)
        return ProductVariantViewHolder(layout)

    }


    override fun onBindViewHolder(holder: ProductVariantViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ProductVariantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(pos: Int) {
            if(pos == 0 ){
                itemView.addVariant.visibility = View.VISIBLE
                itemView.card.visibility = View.GONE
                itemView.addVariant.setOnClickListener {
                    addItem()
                }
            }
            else{
                itemView.addVariant.visibility = View.GONE
                itemView.card.visibility = View.VISIBLE

                val model = list[pos]
                itemView.size.text = model.productSize.size
                itemView.color.text = model.productColor.color
                itemView.no.text = pos.toString()
                itemView.quantity.text = model.productQuantity.toString()
                val formatter = DecimalFormat("#,###,###,###")
                val price = formatter.format(model.productPrice.toDouble().roundToInt())
                itemView.price.text = "$price €"
                itemView.imgEdit.setOnClickListener {
                    editItem(pos)
                }
                itemView.imgDelete.setOnClickListener {
                    deleteItem(pos)
                }
            }
        }
    }
}
