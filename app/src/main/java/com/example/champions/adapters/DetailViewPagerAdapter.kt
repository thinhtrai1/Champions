package com.example.champions.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.champions.fragments.ChampSkillFragment
import com.example.champions.fragments.ChampSkinFragment
import com.example.champions.fragments.ChampStoryFragment
import org.jsoup.nodes.Document

class DetailViewPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val mFragments: ArrayList<Fragment> = arrayListOf(
        ChampStoryFragment.newInstance(),
        ChampSkillFragment.newInstance(),
        ChampSkinFragment.newInstance()
    )
    private val mTitles = arrayListOf("STORY", "SKILL", "SKIN")

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTitles[position]
    }
}