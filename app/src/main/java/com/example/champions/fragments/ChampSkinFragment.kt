package com.example.champions.fragments

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.champions.R
import com.example.champions.activities.DetailActivity
import com.example.champions.adapters.SkinViewPagerAdapter
import com.example.champions.adapters.SkinViewPagerAdapterImageView
import kotlinx.android.synthetic.main.fragment_champ_skin.*
import org.jsoup.nodes.Element

class ChampSkinFragment: Fragment() {
    var mList: ArrayList<String> = ArrayList()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_champ_skin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var mActivity = activity as DetailActivity
        val metrics = DisplayMetrics()
        mActivity.windowManager.defaultDisplay.getMetrics(metrics)
        val layoutParams = LinearLayout.LayoutParams(metrics.widthPixels, metrics.widthPixels / 3)

        var pagerAdapter = SkinViewPagerAdapter(mActivity, mList)
        mViewPagerSkin.adapter = pagerAdapter
        mViewPagerSkin.setPageTransformer(false, pagerAdapter)
        mViewPagerSkin.addOnPageChangeListener(pagerAdapter)
        mViewPagerSkin.clipToPadding = false
        mViewPagerSkin.setPadding(metrics.widthPixels / 3, 0, metrics.widthPixels / 3, 0)
        mViewPagerSkin.layoutParams = layoutParams

        var pagerImageAdapter = SkinViewPagerAdapterImageView(mActivity, mList)
        mViewPagerSkinImageView.adapter = pagerImageAdapter
        mViewPagerSkinImageView.setPageTransformer(false, pagerImageAdapter)
        mViewPagerSkinImageView.addOnPageChangeListener(pagerImageAdapter)

        var items = mActivity.mJsoup
            .getElementsByClass("style__CarouselContainer-sc-1tlyqoa-11 cUeBFP")[0]
            .getElementsByTag("div")[1]
            .getElementsByTag("li")
        for (item: Element in items) {
            mList.add(item.getElementsByTag("img")[0].attr("src"))
        }
        pagerAdapter.notifyDataSetChanged()
        pagerImageAdapter.notifyDataSetChanged()
        mViewPagerSkin.currentItem = 1
    }
}