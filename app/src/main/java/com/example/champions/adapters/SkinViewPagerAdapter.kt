package com.example.champions.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.champions.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_viewpager_custom.view.*

class SkinViewPagerAdapter(
    private val mContext: Context,
    private val mList: ArrayList<String>,
    private val mOnClick: (Int) -> Unit
) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return mList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return LayoutInflater.from(mContext).inflate(R.layout.item_viewpager_custom, container, false).apply {
            Picasso.get()
                .load(mList[position])
                .resizeDimen(R.dimen.skin_thumbnail_width, R.dimen.skin_thumbnail_height)
                .centerCrop()
                .placeholder(R.drawable.image_default)
                .into(imageView)
            setOnClickListener {
                mOnClick(position)
            }
            container.addView(this)
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    }
}