package com.live.emmazone.adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.activities.provider.AddNewProductActivity
import com.live.emmazone.activities.provider.EditProductActivity
import com.live.emmazone.model.ModelProShopDetailProducts
import com.makeramen.roundedimageview.RoundedImageView

class AdapterProviderShopDetailProducts(
    private val context: Context,
    private val list: ArrayList<ModelProShopDetailProducts>
) :
    RecyclerView.Adapter<AdapterProviderShopDetailProducts.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_provider_shopdetail_products, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = list[position]

        if (position == 0) {
            holder.cardView.visibility = View.GONE
            holder.layoutAddProduct.visibility = View.VISIBLE
        } else {
            holder.cardView.visibility = View.VISIBLE
            holder.layoutAddProduct.visibility = View.GONE
        }

        holder.imageProductSD.setImageResource(model.imageProductShopDetail)
        holder.imageEditSDProduct.setImageResource(model.imgEdit)
        holder.imageDelete.setImageResource(model.imgDelete)
        holder.productItemNameSD.setText(model.productItemName)
        holder.productItemPriceSD.setText(model.productItemPrice)
        holder.tvShopDetailProductBrandSD.setText(model.tvShopDetailProductBrand)
        holder.tvShopDetailProductText.setText(model.tvShopDetailProductText)
        holder.tvSDDeliveryEstimateSD.setText(model.tvSDDeliveryEstimate)

        holder.itemView.setOnClickListener {
            if (position == 0) {
                val intent = Intent(holder.itemView.context, AddNewProductActivity::class.java)
                holder.itemView.context.startActivity(intent)
            }
        }

        holder.imageEditSDProduct.setOnClickListener {
            val intent = Intent(holder.itemView.context, EditProductActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }

        holder.imageDelete.setOnClickListener {

            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            dialog.setContentView(R.layout.dialog_delete_product)
            dialog.window?.setBackgroundDrawable(
                ContextCompat.getDrawable(context, android.R.color.transparent)
            )

            val yesBtn: Button = dialog.findViewById(R.id.btnCancelYes)
            val noBtn: Button = dialog.findViewById(R.id.btnCancelNo)

            yesBtn.setOnClickListener {
                dialog.dismiss()
            }

            noBtn.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageProductSD: ImageView = itemView.findViewById(R.id.imageProductShopDetail)
        val imageEditSDProduct: ImageView = itemView.findViewById(R.id.imgEdit)
        val imageDelete: ImageView = itemView.findViewById(R.id.imgDelete)
        val productItemNameSD = itemView.findViewById<TextView>(R.id.productItemName)
        val productItemPriceSD = itemView.findViewById<TextView>(R.id.productItemPrice)
        val tvShopDetailProductBrandSD = itemView.findViewById<TextView>(R.id.tvShopDetailProductBrand)
        val tvShopDetailProductText = itemView.findViewById<TextView>(R.id.tvShopDetailProductText)
        val tvSDDeliveryEstimateSD = itemView.findViewById<TextView>(R.id.tvSDDeliveryEstimate)
        val ratingBar = itemView.findViewById<ImageView>(R.id.ratingBarShopDetailProduct)
        val layoutAddProduct = itemView.findViewById<LinearLayout>(R.id.layoutAddProduct)
        val layoutConst = itemView.findViewById<ConstraintLayout>(R.id.layoutConst)
        val cardView = itemView.findViewById<CardView>(R.id.cardView)

    }

}