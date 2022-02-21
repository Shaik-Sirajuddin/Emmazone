package com.live.emmazone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.response_model.FaqListResponse
import com.live.emmazone.response_model.NotificatioListingResponse
import com.schunts.extensionfuncton.loadImage

class AdapterFaqs(private val list: ArrayList<FaqListResponse.Body>) :
    RecyclerView.Adapter<AdapterFaqs.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_faqs, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvQuestion.text = list[position].title
        holder.tvAnswer.text = list[position].answer
        holder.ivDropDown.setOnClickListener {
            if (list[position].isSelected == "false") {
                holder.ivDropDown.setImageResource(R.drawable.arrow_drop_down_black)
                holder.tvAnswer.visibility = View.VISIBLE
                list[position].isSelected="true"
            } else  if (list[position].isSelected == "true") {
                holder.ivDropDown.setImageResource(R.drawable.arrow_drop_down_black)
                holder.tvAnswer.visibility = View.GONE
                list[position].isSelected="false"

            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvQuestion = itemView.findViewById<TextView>(R.id.tvQuestion)
        val tvAnswer = itemView.findViewById<TextView>(R.id.tvAnswer)
        val ivDropDown = itemView.findViewById<ImageView>(R.id.ivDropDown)

    }
}