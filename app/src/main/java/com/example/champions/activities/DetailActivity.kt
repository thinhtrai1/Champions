package com.example.champions.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.champions.R
import com.example.champions.adapters.DetailViewPagerAdapter
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    var mUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mUrl = intent.getStringExtra("url")

        val mAdapter = DetailViewPagerAdapter(supportFragmentManager)
        mViewPager.adapter = mAdapter
        mViewPager.setPageTransformer(false, mAdapter)
        mViewPager.offscreenPageLimit = 2
        mTabLayout.setupWithViewPager(mViewPager)
    }

    fun setTitle(title: String) {
        mTvTitle.text = title
    }
}
