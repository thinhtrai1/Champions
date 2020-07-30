package com.example.champions.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.viewpager.widget.ViewPager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.champions.R
import com.example.champions.adapters.DetailViewPagerAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.math.abs

class DetailActivity : AppCompatActivity(), ViewPager.PageTransformer {
    private lateinit var mUrl: String
    private lateinit var mJsoup: Document

    companion object {
        private const val MIN_SCALE = 0.85f
        private const val MIN_ALPHA = 0.5f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }

        mUrl = intent.getStringExtra("url")!!
        Volley.newRequestQueue(this)
            .add(StringRequest(Request.Method.GET, mUrl, Response.Listener<String> { response ->
                mJsoup = Jsoup.parse(response)
                mViewPager.adapter = DetailViewPagerAdapter(supportFragmentManager, mJsoup)
                mImvTemp.visibility = View.GONE
                mViewPager.visibility = View.VISIBLE
            }, Response.ErrorListener {}))

        mViewPager.setPageTransformer(false, this)
        mViewPager.offscreenPageLimit = 2
        mTabLayout.setupWithViewPager(mViewPager)
    }

    fun setTitle(title: String) {
        mTvTitle.text = title
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
                    val scaleFactor = MIN_SCALE.coerceAtLeast(1 - abs(position))
                    val verMargin = pageHeight * (1 - scaleFactor) / 2
                    val horMargin = pageWidth * (1 - scaleFactor) / 2
                    translationX = if (position < 0) {
                        horMargin - verMargin / 2
                    } else {
                        horMargin + verMargin / 2
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

//    fun Bitmap.rotate(degrees: Float): Bitmap {
//        val matrix = Matrix().apply { postRotate(degrees) }
//        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
//    }
}
