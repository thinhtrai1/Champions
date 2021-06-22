package com.example.champions.activities

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.champions.adapters.MainRecyclerViewAdapter
import com.example.champions.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread {
            val mChampList = ArrayList<String>()
            val mDetailList = ArrayList<String>()
            val elements: Elements
            try {
                elements = Jsoup.connect(URL.plus("/en-us/champions/")).get()
                    .getElementsByClass("style__List-ntddd-2 fqjuPM")[0].getElementsByTag("a")
            } catch (e: Exception) {
                return@Thread
            }
            for (element in elements) {
                mDetailList.add(URL + element.attr("href"))
                mChampList.add(element.getElementsByTag("img")[0].attr("src"))
            }
            runOnUiThread {
                mGridView.layoutManager = StaggeredGridLayoutManager(2, 1)
                mGridView.adapter = MainRecyclerViewAdapter(mChampList) {
                    startActivity(Intent(this, DetailActivity::class.java).putExtra("url", mDetailList[it]))
                }
            }
        }.start()

        fab()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun fab() {
        var dX = 0f
        var dY = 0f
        var d1 = fab.x
        var d2 = fab.y
        fab.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    dX = view.x - event.rawX
                    dY = view.y - event.rawY
                    d1 = fab.x
                    d2 = fab.y
                }
                MotionEvent.ACTION_MOVE -> {
                    view.animate()
                        .x(event.rawX + dX)
                        .y(event.rawY + dY)
                        .setDuration(0)
                        .start()
                }
                MotionEvent.ACTION_UP -> {
                    if (fab.x in d1 - 5.. d1 + 5 || fab.y in d2 - 5.. d2 + 5) {
                        val intent = Intent(this, UniverseActivity::class.java)
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
                            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                        } else startActivity(intent)
                    }
                }
                else -> {
                    return@setOnTouchListener false
                }
            }
            true
        }
    }

    companion object {
        const val URL = "https://na.leagueoflegends.com"
    }
}
