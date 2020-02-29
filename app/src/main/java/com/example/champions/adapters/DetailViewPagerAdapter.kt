package com.example.champions.adapters

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.champions.fragments.ChampSkillFragment
import com.example.champions.fragments.ChampSkinFragment
import com.example.champions.fragments.ChampStoryFragment
import kotlin.math.abs

class DetailViewPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT), ViewPager.PageTransformer{
    var mFragments = arrayListOf(ChampStoryFragment(), ChampSkillFragment(), ChampSkinFragment())
    var mTitles = arrayListOf("STORY", "SKILL", "SKIN")
    private val MIN_SCALE = 0.85f
    private val MIN_ALPHA = 0.5f

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTitles[position]
    }

    override fun transformPage(view: View, position: Float) {
        view.apply {
            val pageWidth = width
            val pageHeight = height
            when {
                position < -1 -> {
                    alpha = 0f
                }
                position <= 1 -> {
                    val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                    val vertMargin = pageHeight * (1 - scaleFactor) / 2
                    val horzMargin = pageWidth * (1 - scaleFactor) / 2
                    translationX = if (position < 0) {
                        horzMargin - vertMargin / 2
                    } else {
                        horzMargin + vertMargin / 2
                    }

                    scaleX = scaleFactor
                    scaleY = scaleFactor

                    alpha = (MIN_ALPHA + (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
                }
                else -> {
                    alpha = 0f
                }
            }
        }
    }
}