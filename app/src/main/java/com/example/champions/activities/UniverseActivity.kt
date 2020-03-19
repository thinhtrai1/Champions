package com.example.champions.activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
    var mNamesHold = ArrayList<String>()
    lateinit var mAdapter: ArrayAdapter<String>
    var mStories = ArrayList<Story>()
    var mWait = -1
    lateinit var mPref: SharedPreferences

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
        layout_3.layoutParams = layout2

        val rotateAnimation = RotateAnimation(0f, 3600f
            , Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotateAnimation.duration = 2000
        mImage2.startAnimation(rotateAnimation)

        mPref = getSharedPreferences("uni", Context.MODE_PRIVATE)
        mAdapter = ArrayAdapter(this, R.layout.item_listview_story, mNames)
        mLvUniverse.adapter = mAdapter
        mLvUniverse.isFocusable = false
        load()

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
                if (size == 5 && mStory.update != mPref.getString("54", "")) {
                    reSave()
                }
                if (size > 5) {
                    var name =
                    if (mStory.champ != null) "" + (size - 5) + ".  " + mStory.name + " [" + mStory.champ + "]"
                    else "" + (size - 5) + ".  " + mStory.name
                    mNames.add(name)
                    mNamesHold.add(name)
                    mAdapter.notifyDataSetChanged()
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

        mEdtSearch.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mNames.clear()
                mNames.addAll(mNamesHold)
                var temp = ArrayList<String>()
                for (name: String in mNames)
                    if (name.toLowerCase().contains(mEdtSearch.text.toString().toLowerCase()))
                        temp.add(name)
                mNames.clear()
                mNames.addAll(temp)
                mAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun reSave() {
        mPref.edit().putString("11", mStories[0].image).apply()
        mPref.edit().putString("12", mStories[0].name).apply()
        mPref.edit().putString("13", mStories[0].sub).apply()
        mPref.edit().putString("21", mStories[1].image).apply()
        mPref.edit().putString("22", mStories[1].name).apply()
        mPref.edit().putString("31", mStories[2].image).apply()
        mPref.edit().putString("32", mStories[2].name).apply()
        mPref.edit().putString("41", mStories[3].image).apply()
        mPref.edit().putString("42", mStories[3].name).apply()
        mPref.edit().putString("43", mStories[3].sub).apply()
        mPref.edit().putString("51", mStories[4].image).apply()
        mPref.edit().putString("52", mStories[4].name).apply()
        mPref.edit().putString("53", mStories[4].sub).apply()
        mPref.edit().putString("54", mStories[4].update).apply()

        load()
    }

    private fun load() {
        mTvName1.text = mPref.getString("12", "")
        mTvSub1.text = mPref.getString("13", "")
        mTvName2.text = mPref.getString("22", "")
        mTvName3.text = mPref.getString("32", "")
        mTvName4.text = mPref.getString("42", "")
        mTvSub4.text = mPref.getString("43", "")
        mTvName5.text = mPref.getString("52", "")
        mTvSub5.text = mPref.getString("53", "")
        Picasso.get().load(mPref.getString("11", "https://am-a.akamaihd.net/image?f=https%3A%2F%2Funiverse-meeps.leagueoflegends.com%2Fv1%2Fassets%2Fimages%2Fname-of-the-blade-splash.jpg&resize=1800:"))
            .placeholder(R.drawable.image_default).into(mImage1)
        Picasso.get().load(mPref.getString("21", "https://am-a.akamaihd.net/image?f=https%3A%2F%2Funiverse-meeps.leagueoflegends.com%2Fv1%2Fassets%2Fimages%2Fcomic-series%2Fzedcomic%2Fissue-4-link.jpg&resize=1800:"))
            .placeholder(R.drawable.image_default).into(mImage2)
        Picasso.get().load(mPref.getString("31", "https://am-a.akamaihd.net/image?f=https%3A%2F%2Funiverse-meeps.leagueoflegends.com%2Fv1%2Fassets%2Fimages%2Fdream-thief-splash.jpg&resize=1800:"))
            .placeholder(R.drawable.image_default).into(mImage3)
        Picasso.get().load(mPref.getString("41", "https://am-a.akamaihd.net/image?f=https%3A%2F%2Funiverse-meeps.leagueoflegends.com%2Fv1%2Fassets%2Fimages%2Fbow-and-kunai-splash.jpg&resize=1800:"))
            .placeholder(R.drawable.image_default).into(mImage4)
        Picasso.get().load(mPref.getString("51", "https://am-a.akamaihd.net/image?f=https%3A%2F%2Funiverse-meeps.leagueoflegends.com%2Fv1%2Fassets%2Fimages%2Fsett-color-splash.jpg&resize=1800:"))
            .placeholder(R.drawable.image_default).into(mImage5)
        Picasso.get().load("https://universe.leagueoflegends.com/images/latestBg_Wallpaper.png")
            .placeholder(R.drawable.image_default).into(mImage0)

        mAdapter.notifyDataSetChanged()
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
        val story = ""
        val image: String? = null
        val web = 0
        val sub = ""
        val champ: String? = null
        val update = ""
        fun Story() {}
    }
}
