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
import kotlinx.android.synthetic.main.fragment_champ_skill.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class ChampSkillFragment: Fragment() {
    var mSkillList: ArrayList<String> = ArrayList()
    var mDesList: ArrayList<String> = ArrayList()
    var mVideoList: ArrayList<String> = ArrayList()
    var mCurrent = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_champ_skill, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var mActivity = activity as DetailActivity

        var vidRequest = StringRequest(Request.Method.GET, mActivity.mUrl, Response.Listener<String> { response ->
            var videos = Jsoup.parse(response)
                .getElementsByClass("style__VideoContainer-tmew42-2 cvZjKa")[0]
                .getElementsByTag("video")
            for (i in 0..4) {
                mVideoList.add(videos[i].getElementsByTag("source")[0].attr("src"))
            }
            imageClick(0)
        }, Response.ErrorListener {})

        var desRequest = StringRequest(Request.Method.GET, mActivity.mUrl, Response.Listener<String> { response ->
            var dess = Jsoup.parse(response)
                .getElementsByClass("style__AbilityInfoList-ulelzu-7 kAlIxD")[0]
                .getElementsByTag("li")
            for (item: Element in dess) {
                mDesList.add(item.getElementsByTag("h5")[0].text() + "###" + item.getElementsByTag("p")[0].text())
            }
            Volley.newRequestQueue(mActivity).add(vidRequest)
        }, Response.ErrorListener {})

        var imageRequest = StringRequest(Request.Method.GET, mActivity.mUrl, Response.Listener<String> { response ->
            var skills = Jsoup.parse(response)
                .getElementsByClass("style__WrapperInner-sc-18a4qs7-1 gFNUaI")[0]
                .getElementsByTag("button")
            for (item: Element in skills) {
                mSkillList.add(item.getElementsByTag("img").attr("src"))
            }
            Picasso.get().load(mSkillList[0]).placeholder(R.drawable.image_default).into(mImvSkill0)
            Picasso.get().load(mSkillList[1]).placeholder(R.drawable.image_default).into(mImvSkill1)
            Picasso.get().load(mSkillList[2]).placeholder(R.drawable.image_default).into(mImvSkill2)
            Picasso.get().load(mSkillList[3]).placeholder(R.drawable.image_default).into(mImvSkill3)
            Picasso.get().load(mSkillList[4]).placeholder(R.drawable.image_default).into(mImvSkill4)
        }, Response.ErrorListener {})

        Volley.newRequestQueue(mActivity).add(desRequest)
        Volley.newRequestQueue(mActivity).add(imageRequest)

        mImvPlay.setOnClickListener {
            if (mVdvSKill.isPlaying) {
                mVdvSKill.stopPlayback()
                mImvPlay.setImageResource(R.drawable.ic_video_play)
            } else {
                mImvPlay.setImageResource(R.drawable.ic_video_pause)
                mVdvSKill.start()
            }
        }
        mVdvSKill.setOnPreparedListener { mediaPlayer -> mediaPlayer.isLooping = true }

        mImvSkill0.setOnClickListener {
            if (mCurrent != 0)
                imageClick(0)
        }

        mImvSkill1.setOnClickListener {
            if (mCurrent != 1)
                imageClick(1)
        }

        mImvSkill2.setOnClickListener {
            if (mCurrent != 2)
                imageClick(2)
        }

        mImvSkill3.setOnClickListener {
            if (mCurrent != 3)
                imageClick(3)
        }

        mImvSkill4.setOnClickListener {
            if (mCurrent != 4)
                imageClick(4)
        }
    }

    private fun imageClick(index: Int) {
        mVdvSKill.stopPlayback()
        mImvPlay.setImageResource(R.drawable.ic_video_play)
        mTvSKillName.text = mDesList[index].split("###")[0]
        mTvSKillDes.text = mDesList[index].split("###")[1]
        mTvSKillKey.text = when (index) {
            1 -> "Q"
            2 -> "W"
            3 -> "E"
            4 -> "R"
            else -> "PASSIVE"
        }
        mCurrent = index
        mVdvSKill.setVideoPath(mVideoList[mCurrent])
    }
}