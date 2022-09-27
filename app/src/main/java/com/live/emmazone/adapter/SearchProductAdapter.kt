package com.live.emmazone.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.response_model.SearchProductResponse
import com.schunts.extensionfuncton.loadImage
import kotlinx.android.synthetic.main.item_layout_shopdetail_products.view.*

class SearchProductAdapter(val list: ArrayList<SearchProductResponse.Body>) :
    RecyclerView.Adapter<SearchProductAdapter.SearchProductViewHolder>() {

    private lateinit var mContext: Context

    var onItemClick: ((pos: Int) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchProductViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_shopdetail_products, parent, false)
        return SearchProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchProductViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class SearchProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(pos: Int) {
            val model = list[pos]
            if (model.group!=null && !model.group!!.productImages.isNullOrEmpty()) {
                itemView.imageProductShopDetail.loadImage(model.group!!.productImages[0].image)
            }
            else if(!model.images.isNullOrEmpty()){
                itemView.imageProductShopDetail.loadImage(model.images[0].image)
            }
            else {
                itemView.imageProductShopDetail.setImageResource(R.drawable.placeholder)
            }

            itemView.productItemName.text = model.name
            itemView.rating.text = model.productReview
            try{
                itemView.ratingBar.rating = model.productReview.toFloat()
            }catch (e : Exception){
                Log.e("searchProductAdapter:55" , e.message.toString())
            }
            itemView.productItemPrice.text = mContext.getString(R.string.euro_symbol, model.minPrice)
            itemView.tvShopDetailProductBrand.text = model.shortDescription

            itemView.setOnClickListener {
                onItemClick?.invoke(pos)

            }

        }
    }

}