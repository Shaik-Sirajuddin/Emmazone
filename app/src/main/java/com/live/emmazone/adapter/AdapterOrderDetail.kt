package com.live.emmazone.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.live.emmazone.R
import com.live.emmazone.activities.AddProductReview
import com.live.emmazone.activities.main.ShopReviewsActivity
import com.live.emmazone.response_model.UserOrderListing
import com.live.emmazone.utils.AppConstants
import kotlinx.android.synthetic.main.fragment_provider_home.view.*

class AdapterOrderDetail(
    val context: Context,
    private val list: ArrayList<UserOrderListing.Body.Response.OrderJson.OrderItem>,
    private val isOnGoing : Boolean
) :
    RecyclerView.Adapter<AdapterOrderDetail.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_order_detail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        if(data.mainImage!=null){
            var image = data.mainImage
            if(image.contains("http")){
                image = "https://emmazones3.s3.eu-west-2.amazonaws.com/product/" +  image.substring(image.lastIndexOf('/') + 1)
                Log.e("this",image)
            }
            Glide.with(context).load(image).into(holder.imageOD)
        }
        holder.productItemNameOD.text = data.name
        holder.tvOrderQuantityNumberOD.text = data.orderedQty.toString()
        holder.productPriceOD.text = context.getString(R.string.euro_symbol, data.productPrice)

        holder.review.setOnClickListener {
            Log.e("registerCode",data.registerCode.toString())
            Log.e("productId",data.productId.toString())
            val intent = Intent(context,AddProductReview::class.java)
            intent.putExtra("id",data.productId.toString())
            context.startActivity(intent)
        }

        if(isOnGoing){
            holder.review.visibility = View.GONE
        }
        else{
            holder.review.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageOD: ImageView = itemView.findViewById(R.id.imageOrder)
        val productItemNameOD = itemView.findViewById<TextView>(R.id.productItemName)
        val tvOrderQuantityNumberOD = itemView.findViewById<TextView>(R.id.tvOrderQuantityNumber)
        val productPriceOD = itemView.findViewById<TextView>(R.id.productPrice)
        val review = itemView.findViewById<Button>(R.id.button)
        val seperator = itemView.findViewById<View>(R.id.seperator)
    }
}