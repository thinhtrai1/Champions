package com.example.champions.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.champions.R
import com.squareup.picasso.Picasso

class SkinViewPagerAdapter(
    private val mContext: Context,
    private val mList: ArrayList<String>,
    private val mOnClick: (Int) -> Unit
) : PagerAdapter(), ViewPager.PageTransformer {

    companion object {
        private const val BIG_SCALE = 1.3f
        private const val SMALL_SCALE = 0.7f
        private const val DIFF_SCALE = BIG_SCALE - SMALL_SCALE
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return mList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager_custom, container, false)
        val mImageView: ImageView = view.findViewById(R.id.mImage)
        Picasso.get().load(mList[position]).placeholder(R.drawable.image_default).into(mImageView)
        view.setOnClickListener {
            mOnClick(position)
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    }

    override fun transformPage(page: View, position: Float) {
        var scale = BIG_SCALE
        if (position > 1f) scale -= (position - 1) * DIFF_SCALE
        else scale += (position - 1) * DIFF_SCALE
        if (scale < 0) scale = 0f

        page.scaleX = scale
        page.scaleY = scale
    }
}