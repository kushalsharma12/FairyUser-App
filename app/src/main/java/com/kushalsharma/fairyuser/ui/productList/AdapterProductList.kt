package com.kushalsharma.fairyuserapp.ui.productList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.kushalsharma.fairyuserapp.Modals.Post
import com.kushalsharma.fairyuserapp.R


class AdapterProductList(options: FirestoreRecyclerOptions<Post>) :
    FirestoreRecyclerAdapter<Post, AdapterProductList.productViewHolder>(
        options
    ) {

    class productViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var pTitle: TextView = itemView.findViewById(R.id.item_name_product_list)
        var pDescription: TextView = itemView.findViewById(R.id.description_item_product_list)
        var pQty: TextView = itemView.findViewById(R.id.quantity_item_product_list)
        var pImg: ImageView = itemView.findViewById(R.id.image_item_product)
        val pPrice: TextView = itemView.findViewById(R.id.price_item_product_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): productViewHolder {

        val viewHolder = productViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product_list, parent, false)
        )
        return viewHolder


    }

    override fun onBindViewHolder(holder: productViewHolder, position: Int, model: Post) {

        holder.pTitle.text = model.title
        holder.pDescription.text = model.description
        holder.pQty.text = "Qty: ${model.quantity}"
        holder.pPrice.text = "₹ ${model.price}"
        Glide.with(holder.pImg.context).load(model.productImageUrl)
            .transform(CenterCrop(), RoundedCorners(40))
            .placeholder(R.drawable.ic_loading_placeholder)
            .into(holder.pImg)
    }
}