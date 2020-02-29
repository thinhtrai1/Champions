package com.example.champions.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.champions.R
import com.example.champions.activities.DetailActivity
import com.squareup.picasso.Picasso
import org.jsoup.Jsoup

class ChampSkillFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_champ_skill, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var mActivity = activity as DetailActivity

        var stringRequest = StringRequest(Request.Method.GET, mActivity.mUrl, Response.Listener<String> { response ->
//            var story = Jsoup.parse(response)
//                .getElementsByClass("style__Desc-sc-1o884zt-9 hZPZqS$0")[0]
//                .getElementsByTag("p")[0]
//                .text()
//            mTvStory.text = story
//            var image = Jsoup.parse(response)
//                .getElementsByClass("style__BackgroundImage-sc-1o884zt-2 cIdAXF")[0]
//                .getElementsByTag("img")
//                .attr("src")
//            Picasso.get().load(image).placeholder(R.drawable.image_default).into(mImvBanner)
        }, Response.ErrorListener {})
        Volley.newRequestQueue(mActivity).add(stringRequest)
    }
}