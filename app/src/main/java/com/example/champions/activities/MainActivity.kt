package com.example.champions.activities

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.champions.adapters.MainRecyclerViewAdapter
import com.example.champions.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class MainActivity : AppCompatActivity(), MainRecyclerViewAdapter.onItemClickListener {
    var mChampList: ArrayList<String> = ArrayList()
    var mDetailList: ArrayList<String> = ArrayList()
    var mUrl = "https://na.leagueoflegends.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var manager = GridLayoutManager(this, 2)
        val mAdapter = MainRecyclerViewAdapter(mChampList, this)
        mGridView.adapter = mAdapter
        mGridView.layoutManager = manager

        var stringRequest = StringRequest(Request.Method.GET, "$mUrl/en-us/champions/", Response.Listener<String> { response ->
            var elements = Jsoup.parse(response).getElementsByClass("style__List-ntddd-2 fqjuPM")[0].getElementsByTag("a")
            for (element: Element in elements) {
                mDetailList.add(mUrl + element.attr("href"))
                mChampList.add(element.getElementsByTag("img")[0].attr("src"))
            }
            mAdapter.notifyDataSetChanged()
        }, Response.ErrorListener {})
        Volley.newRequestQueue(this).add(stringRequest)

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
                        var intent = Intent(this, UniverseActivity::class.java)
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

    override fun onItemClick(item: String, position: Int) {
        var intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("url", mDetailList[position])
            startActivity(intent)
    }
}
