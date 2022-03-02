package com.live.emmazone.adapter

import android.app.Dialog
import android.content.Context
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.live.emmazone.R
import com.live.emmazone.activities.main.Cart
import com.live.emmazone.model.*

class AdapterCart(
    private var mContext: Context, private
    val list: ArrayList<CartResponsModel.Body.CartItem>
) :
    RecyclerView.Adapter<AdapterCart.ViewHolder>() {

    var onDeleteClick: ((pos: Int) -> Unit)? = null
    var onPlusMinusClick: ((pos: Int, clickOn: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_cart_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val modelCart = list[position]
        Glide.with(mContext).load(modelCart.product.mainImage).into(holder.imgCart)

        // holder.imageDelete.setImageResource(modelCart.imgDelete)
        holder.tvproductItemName.text = modelCart.product.name
        holder.tvproductPrice.text = modelCart.product.productPrice
        holder.productQty.text = modelCart.qty.toString()
        holder.imageDelete.setOnClickListener {
            val dialog = Dialog(mContext)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            dialog.setContentView(R.layout.dialog_delete_product)
            dialog.window?.setBackgroundDrawable(
                ContextCompat.getDrawable(mContext, android.R.color.transparent)
            )

            val yesBtn: Button = dialog.findViewById(R.id.btnCancelYes)
            val noBtn: Button = dialog.findViewById(R.id.btnCancelNo)

            yesBtn.setOnClickListener {
                dialog.dismiss()
                onDeleteClick?.invoke(position)
            }

            noBtn.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }

        holder.tvMins.setOnClickListener {
            if (modelCart.qty > 1) {
                modelCart.qty = modelCart.qty - 1
                notifyDataSetChanged()
            }
        }

        holder.tvAdd.setOnClickListener {
            modelCart.qty = modelCart.qty + 1
            notifyDataSetChanged()

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgCart: ImageView = itemView.findViewById(R.id.imageCart)
        val imageDelete: ImageView = itemView.findViewById(R.id.imgDelete)
        val tvproductItemName = itemView.findViewById<TextView>(R.id.productItemName)
        val tvproductPrice = itemView.findViewById<TextView>(R.id.productPrice)
        val productQty = itemView.findViewById<TextView>(R.id.productQty)
        val tvMins = itemView.findViewById<TextView>(R.id.imgMinus)
        val tvAdd = itemView.findViewById<TextView>(R.id.imgAdd)

    }
}