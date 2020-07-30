package com.example.champions.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.champions.IOnItemClickListener
import com.example.champions.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_grv.view.*

class MainRecyclerViewAdapter(private val mList: ArrayList<String>, private val mOnClick: IOnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_grv, parent, false)) {}
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Picasso.get().load(mList[position]).placeholder(R.drawable.image_default).into(holder.itemView.imageView)
        holder.itemView.setOnClickListener {
            mOnClick.onItemClick(position)
        }
    }
}