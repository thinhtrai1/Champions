package com.example.champions.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.champions.R
import com.squareup.picasso.Picasso

class MainRecyclerViewAdapter(var mList: ArrayList<String>, var mOnClick: onItemClickListener) : RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_grv, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(mList[position]).placeholder(R.drawable.image_default).into(holder.imageView)
        holder.initialize(mList[position], mOnClick)
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        var imageView = row.findViewById(R.id.mImage) as ImageView

        fun initialize(item: String, action: onItemClickListener) {
            imageView.setOnClickListener {
                action.onItemClick(item, adapterPosition)
            }
        }
    }

    interface onItemClickListener {
        fun onItemClick(item: String, position: Int)
    }
}