package com.example.champions.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.champions.R
import com.squareup.picasso.Picasso

class SkinViewPagerAdapterImageView(
    private val mContext: Context,
    private val mList: ArrayList<String>,
    private val mOnClick: () -> Unit
) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return mList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return ImageView(mContext).apply {
            Picasso.get().load(mList[position]).placeholder(R.drawable.image_default).into(this)
            setOnClickListener {
                mOnClick()
            }
            container.addView(this)
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    }
}