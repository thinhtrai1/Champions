package com.example.champions.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.champions.R
import com.squareup.picasso.Picasso

class GridViewAdapter(val mContext: Context, val mList: ArrayList<String>): BaseAdapter(){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = View.inflate(mContext, R.layout.item_grv, null)
        val imageView = view.findViewById<ImageView>(R.id.mImage)
        Picasso.get().load(mList[position]).placeholder(R.drawable.image_default).into(imageView)

        return view
    }

    override fun getItem(position: Int): Any {
        return mList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mList.size
    }
}