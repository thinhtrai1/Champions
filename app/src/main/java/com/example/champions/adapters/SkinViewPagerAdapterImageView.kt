package com.example.champions.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.champions.R
import com.example.champions.activities.DetailActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_champ_skin.*
import kotlin.math.abs

class SkinViewPagerAdapterImageView(var mContext: Context, var mList: ArrayList<String>) : PagerAdapter(), ViewPager.PageTransformer, ViewPager.OnPageChangeListener{

    private val MIN_SCALE = 0.75f
    var a = mContext as DetailActivity

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return mList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var view = LayoutInflater.from(mContext).inflate(R.layout.item_view_pager_skin_image_view, container, false)
        val mImageView = view.findViewById<ImageView>(R.id.mImageViewSkin)
        Picasso.get().load(mList[position]).placeholder(R.drawable.image_default).into(mImageView)

        mImageView.setOnClickListener {
        }

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    }

    override fun transformPage(view: View, position: Float) {
        view.apply {
            val pageWidth = width
            when {
                position < -1 -> alpha = 0f
                position <= 0 -> {
                    alpha = 1f
                    translationX = 0f
                    scaleX = 1f
                    scaleY = 1f
                }
                position <= 1 -> {
                    alpha = 1 - position
                    translationX = pageWidth * -position
                    val scaleFactor = (MIN_SCALE + (1 - MIN_SCALE) * (1 - abs(position)))
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                }
                else -> alpha = 0f
            }
        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        if (a.mViewPagerSkin.currentItem != position)
            a.mViewPagerSkin.setCurrentItem(position, true)
    }
}