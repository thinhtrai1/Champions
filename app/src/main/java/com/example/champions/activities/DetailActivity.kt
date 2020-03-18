package com.example.champions.activities

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.champions.R
import com.example.champions.adapters.DetailViewPagerAdapter
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_detail.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.lang.Exception

class DetailActivity : AppCompatActivity() {
    var mUrl = ""
    var mCurrent = 0
    lateinit var mJsoup: Document

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        val mAdapter = DetailViewPagerAdapter(supportFragmentManager)
        mUrl = intent.getStringExtra("url")
        Volley.newRequestQueue(this)
            .add(StringRequest(Request.Method.GET, mUrl, Response.Listener<String> { response ->
                mJsoup = Jsoup.parse(response)
                mViewPager.adapter = mAdapter
                mImvTemp.visibility = View.GONE
                mViewPager.visibility = View.VISIBLE
            }, Response.ErrorListener {}))

        mViewPager.setPageTransformer(false, mAdapter)
        mViewPager.offscreenPageLimit = 2
        mTabLayout.setupWithViewPager(mViewPager)
    }

    fun setTitle(title: String) {
        mTvTitle.text = title
    }

    fun zoom(url: String) {
        var dialog = Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.setContentView(R.layout.dialog_image)
        dialog.show()
        var imageView = dialog.findViewById<ImageView>(R.id.mImageZoom)
        imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate))
        Picasso.get().load(url).placeholder(R.drawable.image_default).into(object: Target{
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            }

            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
                imageView.setImageBitmap(bitmap.rotate(90F))
            }
        })
    }

    fun Bitmap.rotate(degrees: Float): Bitmap {
        val matrix = Matrix().apply { postRotate(degrees) }
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }
}
