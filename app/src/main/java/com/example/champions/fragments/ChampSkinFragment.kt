package com.example.champions.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.champions.IOnItemClickListener
import com.example.champions.R
import com.example.champions.adapters.SkinViewPagerAdapter
import com.example.champions.adapters.SkinViewPagerAdapterImageView
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_champ_skin.*
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class ChampSkinFragment: Fragment() {
    private lateinit var mJsoup: Document
    private val mList: ArrayList<String> = ArrayList()
    private var mCurrent = 0

    companion object {
        fun newInstance(jsoup: Document): ChampSkinFragment {
            return ChampSkinFragment().apply {
                mJsoup = jsoup
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_champ_skin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val metrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(metrics)
        val layoutParams = LinearLayout.LayoutParams(metrics.widthPixels, metrics.widthPixels / 3)

        val items = mJsoup
            .getElementsByClass("style__CarouselContainer-sc-1tlyqoa-11 cUeBFP")[0]
            .getElementsByTag("div")[1]
            .getElementsByTag("li")
        for (item: Element in items) {
            mList.add(item.getElementsByTag("img")[0].attr("src"))
        }

        val pagerAdapter = SkinViewPagerAdapter(context!!, mList, object : IOnItemClickListener {
            override fun onItemClick(position: Int) {
                mViewPagerSkin.setCurrentItem(position, true)
            }
        })
        mViewPagerSkin.adapter = pagerAdapter
        mViewPagerSkin.setPageTransformer(false, pagerAdapter)
        mViewPagerSkin.clipToPadding = false
        mViewPagerSkin.setPadding(metrics.widthPixels / 3, 0, metrics.widthPixels / 3, 0)
        mViewPagerSkin.layoutParams = layoutParams
        mViewPagerSkin.offscreenPageLimit = mList.size - 1
        mViewPagerSkin.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                mViewPagerSkinImageView.setCurrentItem(position, true)
            }
        })

        val pagerImageAdapter = SkinViewPagerAdapterImageView(context!!, mList, object : IOnItemClickListener {
            override fun onItemClick(position: Int) {
                zoom(mList[mCurrent])
            }
        })
        mViewPagerSkinImageView.adapter = pagerImageAdapter
        mViewPagerSkinImageView.setPageTransformer(false, pagerImageAdapter)
        mViewPagerSkinImageView.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                mViewPagerSkin.setCurrentItem(position, true)
                mCurrent = position
            }
        })
    }

    private fun zoom(url: String) {
        Dialog(context!!, android.R.style.Theme_Black_NoTitleBar_Fullscreen).apply {
            val view = PhotoView(context)
            setContentView(view)
            show()
            view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate))
            Picasso.get().load(url).placeholder(R.drawable.image_default).rotate(90F).into(view)
        }
    }
}