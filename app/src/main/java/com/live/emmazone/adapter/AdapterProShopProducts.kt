package com.live.emmazone.adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.activities.fragment.FragmentProviderHome
import com.live.emmazone.activities.provider.EditProductActivity
import com.live.emmazone.response_model.Product
import com.live.emmazone.response_model.ProductGroup
import com.live.emmazone.response_model.SellerShopDetailResponse
import com.schunts.extensionfuncton.loadImage

class AdapterProShopProducts(
    private val context: Context,
    val list: ArrayList<ProductGroup>,
    val fragmentProviderHome: FragmentProviderHome
) :
    RecyclerView.Adapter<AdapterProShopProducts.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_provider_shopdetail_products, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ModelProShopDetailProducts = list[position]
        ModelProShopDetailProducts.mainImage?.let { holder.imageProductSD.loadImage(it) }
        holder.productItemNameSD.text = ModelProShopDetailProducts.name
//        holder.productItemPriceSD.text = context.getString(
//            R.string.euro_symbol,
//            ModelProShopDetailProducts.price.toDouble().toString()
//        )
        holder.tvShopDetailProductBrandSD.text = ModelProShopDetailProducts.shortDescription   //short description

        holder.tvSDDeliveryEstimateSD.text = "Delivery Estimate 7 Days"

//        if (!ModelProShopDetailProducts.productReview.isNullOrEmpty()){
//            holder.tvShopDetailProductText.text = ModelProShopDetailProducts.productReview
//            holder.ratingBar.rating = ModelProShopDetailProducts.productReview.toFloat()
//        }

        holder.imageEditSDProduct.setOnClickListener {
            val intent = Intent(holder.itemView.context, EditProductActivity::class.java)
            intent.putExtra("group", ModelProShopDetailProducts)
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
                fragmentProviderHome.deleteProductGroup(
                    position,
                    ModelProShopDetailProducts.id.toString()
                )
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
        val tvShopDetailProductBrandSD =
            itemView.findViewById<TextView>(R.id.tvShopDetailProductBrand)
        val tvShopDetailProductText = itemView.findViewById<TextView>(R.id.tvShopDetailProductText)
        val tvSDDeliveryEstimateSD = itemView.findViewById<TextView>(R.id.tvSDDeliveryEstimate)
        val ratingBar = itemView.findViewById<RatingBar>(R.id.ratingBarShopDetailProduct)
        val layoutAddProduct = itemView.findViewById<LinearLayout>(R.id.layoutAddProduct)

    }

    fun deleteItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, list.size)
    }

}