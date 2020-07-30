package com.example.champions.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.champions.IOnItemClickListener
import com.example.champions.R
import com.squareup.picasso.Picasso
import kotlin.math.abs

class SkinViewPagerAdapterImageView(
    private val mContext: Context,
    private val mList: ArrayList<String>,
    private val mOnClick: IOnItemClickListener
) : PagerAdapter(), ViewPager.PageTransformer {

    companion object {
        private const val MIN_SCALE = 0.75f
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return mList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(mContext)
        Picasso.get().load(mList[position]).placeholder(R.drawable.image_default).into(imageView)

        imageView.setOnClickListener {
            mOnClick.onItemClick(-1)
        }

        container.addView(imageView)
        return imageView
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
}