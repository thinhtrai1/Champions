package com.example.champions.activities

import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Explode
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.core.view.setMargins
import com.example.champions.R
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_universe.*
import kotlinx.android.synthetic.main.dialog_story.*

class UniverseActivity : AppCompatActivity() {
    var mNames = ArrayList<String>()
    var mStories = ArrayList<Story>()
    var mWait = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_universe)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.enterTransition = (Explode().setDuration(2000))
        }

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val layout1 = LinearLayout.LayoutParams(metrics.widthPixels - 20.toPx(), (metrics.widthPixels - 20.toPx()) * 1013 / 1800)
        layout1.setMargins(5.toPx())
        layout_1.layoutParams = layout1
        val layout2 = LinearLayout.LayoutParams((metrics.widthPixels - 30.toPx()) / 2, ((metrics.widthPixels - 30.toPx()) / 2) * 1013 / 1800)
        layout2.setMargins(5.toPx())
        layout_2.layoutParams = layout2
        val layout3 = LinearLayout.LayoutParams((metrics.widthPixels - 30.toPx()) / 2, ((metrics.widthPixels - 30.toPx()) / 2) * 1026 / 1800)
        layout3.setMargins(5.toPx())
        layout_3.layoutParams = layout3

        val rotateAnimation = RotateAnimation(0f, 3600f
            , Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotateAnimation.duration = 2000
        mImage2.startAnimation(rotateAnimation)

        Picasso.get().load("https://am-a.akamaihd.net/image?f=https%3A%2F%2Funiverse-meeps.leagueoflegends.com%2Fv1%2Fassets%2Fimages%2Fname-of-the-blade-splash.jpg&resize=1800:")
            .placeholder(R.drawable.image_default).into(mImage1)
        Picasso.get().load("https://am-a.akamaihd.net/image?f=https%3A%2F%2Funiverse-meeps.leagueoflegends.com%2Fv1%2Fassets%2Fimages%2Fcomic-series%2Fzedcomic%2Fissue-4-link.jpg&resize=1800:")
            .placeholder(R.drawable.image_default).into(mImage2)
        Picasso.get().load("https://am-a.akamaihd.net/image?f=https%3A%2F%2Funiverse-meeps.leagueoflegends.com%2Fv1%2Fassets%2Fimages%2Fdream-thief-splash.jpg&resize=1800:")
            .placeholder(R.drawable.image_default).into(mImage3)
        Picasso.get().load("https://am-a.akamaihd.net/image?f=https%3A%2F%2Funiverse-meeps.leagueoflegends.com%2Fv1%2Fassets%2Fimages%2Fbow-and-kunai-splash.jpg&resize=1800:")
            .placeholder(R.drawable.image_default).into(mImage4)
        Picasso.get().load("https://am-a.akamaihd.net/image?f=https%3A%2F%2Funiverse-meeps.leagueoflegends.com%2Fv1%2Fassets%2Fimages%2Fsett-color-splash.jpg&resize=1800:")
            .placeholder(R.drawable.image_default).into(mImage5)
        Picasso.get().load("https://universe.leagueoflegends.com/images/latestBg_Wallpaper.png")
            .placeholder(R.drawable.image_default).into(mImage0)

        val namesAdapter = ArrayAdapter(this, R.layout.item_listview_story, mNames)
        mLvUniverse.adapter = namesAdapter

        var mRef = FirebaseDatabase.getInstance().reference
        mRef.child("Stories").addChildEventListener(object: ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                var mStory = p0.getValue(Story::class.java)!!
                mStories.add(mStory)
                val size = mStories.size
                if (size > 5) {
                    mNames.add("" + (size - 5) + ".  " + mStory.name)
                    namesAdapter.notifyDataSetChanged()
                }
                if (mStories.size - 1 == mWait)
                    mReadDialog(mStories.size - 1)
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }
        })

        mImage1.setOnClickListener { mReadDialog(0) }
        mImage2.setOnClickListener { mReadDialog(1) }
        mImage3.setOnClickListener { mReadDialog(2) }
        mImage4.setOnClickListener { mReadDialog(3) }
        mImage5.setOnClickListener { mReadDialog(4) }

        mLvUniverse.setOnItemClickListener { _, _, position, _ ->
            mReadDialog(position + 5)
        }
    }

    private fun mReadDialog(pos: Int) {
        if (mStories.size > pos) {
            mWait = -1
            if (mStories[pos].web == 0) {
                var dialog = Dialog(this)
                dialog.setContentView(R.layout.dialog_story)
                dialog.show()
                dialog.mTvDialogStory.text = mStories[pos].name + "\n\n" + mStories[pos].story
                if (mStories[pos].image != null)
                    Picasso.get().load(mStories[pos].image).placeholder(R.drawable.image_default).into(
                        dialog.mImvDialogStory
                    )
                else dialog.mImvDialogStory.visibility = View.GONE
            } else {
                var wvZed = WebView(this)
                wvZed.webViewClient = WebViewClient()
                wvZed.loadUrl(mStories[pos].story)
                var webSettings = wvZed.settings
                webSettings.javaScriptEnabled = true
                webSettings.loadWithOverviewMode = true
                webSettings.useWideViewPort = true
                AlertDialog.Builder(this).setView(wvZed).show()
            }
        } else {
            mWait = pos
        }
    }

    private fun Int.toPx(): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), resources.displayMetrics).toInt()
    }

    class Story {
        val name = ""
        var story = ""
        var image: String? = null
        var web = 0
        fun Story() {}
    }
}
