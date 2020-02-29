package com.example.champions.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.champions.adapters.GridViewAdapter
import com.example.champions.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class MainActivity : AppCompatActivity() {
    var mChampList: ArrayList<String> = ArrayList()
    var mDetailList: ArrayList<String> = ArrayList()
    var mUrl = "https://na.leagueoflegends.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mAdapter = GridViewAdapter(this, mChampList)
        mGridView.numColumns = 2
        mGridView.adapter = mAdapter

        var stringRequest = StringRequest(Request.Method.GET, "$mUrl/en-us/champions/", Response.Listener<String> { response ->
            var elements = Jsoup.parse(response).getElementsByClass("style__List-ntddd-2 fqjuPM")[0].getElementsByTag("a")
            for (element: Element in elements) {
                mDetailList.add(mUrl + element.attr("href"))
                mChampList.add(element.getElementsByTag("img")[0].attr("src"))
            }
            mAdapter.notifyDataSetChanged()
        }, Response.ErrorListener {})
        Volley.newRequestQueue(this).add(stringRequest)

        mGridView.setOnItemClickListener { _, _, position, _ ->
            var intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("url", mDetailList[position])
            startActivity(intent)
        }
    }
}
