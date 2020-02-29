package com.example.champions.adapters

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.champions.R
import com.example.champions.activities.DetailActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_champ_skin.*

class SkinViewPagerAdapter(var mContext: Context, var mList: ArrayList<String>) : PagerAdapter(), ViewPager.PageTransformer, ViewPager.OnPageChangeListener{

    companion object {
        val BIG_SCALE = 1.3f
        var SMALL_SCALE = 0.7f
        var DIFF_SCALE = BIG_SCALE - SMALL_SCALE
    }
    var a = mContext as DetailActivity

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return mList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var view = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager_custom, container, false)
        val mImageView = view.findViewById<ImageView>(R.id.mImage)
        Picasso.get().load(mList[position]).placeholder(R.drawable.image_default).into(mImageView)
        var layout = view.findViewById<CardView>(R.id.mLayout)
        var mViewPager: ViewPager = a.findViewById(R.id.mViewPagerSkin) as ViewPager
        layout.setOnClickListener {
            mViewPager.currentItem = position
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

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        if (a.mViewPagerSkinImageView.currentItem != position)
            a.mViewPagerSkinImageView.setCurrentItem(position, true)
    }
}