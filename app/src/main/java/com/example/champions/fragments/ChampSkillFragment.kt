package com.example.champions.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.champions.R
import com.example.champions.activities.DetailActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_champ_skill.*
import org.jsoup.nodes.Element

class ChampSkillFragment: Fragment() {
    private var mSkillList: ArrayList<String> = ArrayList()
    private var mDesList: ArrayList<String> = ArrayList()
    private var mVideoList: ArrayList<String> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_champ_skill, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var mActivity = activity as DetailActivity
        mImvPlay.visibility = View.GONE
        mProgressBar.visibility = View.VISIBLE

        val metrics = DisplayMetrics()
        mActivity.windowManager.defaultDisplay.getMetrics(metrics)
        val layoutParams = LinearLayout.LayoutParams(metrics.widthPixels, metrics.widthPixels * 15 / 22)
        mVdvSKill.layoutParams = layoutParams

        var skills = mActivity.mJsoup
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

        var dess = mActivity.mJsoup
            .getElementsByClass("style__AbilityInfoList-ulelzu-7 kAlIxD")[0]
            .getElementsByTag("li")
        for (item: Element in dess) {
            mDesList.add(item.getElementsByTag("h5")[0].text() + "###" + item.getElementsByTag("p")[0].text())
        }

        var videos = mActivity.mJsoup
            .getElementsByClass("style__VideoContainer-tmew42-2 cvZjKa")[0]
            .getElementsByTag("video")
        for (i in 0..4) {
            mVideoList.add(videos[i].getElementsByTag("source")[0].attr("src"))
        }

        imageClick(0)
        mTvSKillDes.movementMethod = ScrollingMovementMethod()

        mImvPlay.setOnClickListener {
            if (mVdvSKill.isPlaying) {
                mVdvSKill.pause()
                mImvPlay.setImageResource(R.drawable.ic_video_play)
            } else {
                mImvPlay.setImageResource(R.drawable.ic_video_pause)
                mVdvSKill.start()
            }
        }

        mVdvSKill.setOnPreparedListener { mediaPlayer ->
            mImvPlay.visibility = View.VISIBLE
            mProgressBar.visibility = View.GONE
            mediaPlayer.isLooping = true
        }

        mImvSkill0.setOnClickListener {
            if (tvKeyP.currentTextColor == Color.BLACK) {
                imageClick(0)
                mImvPlay.visibility = View.GONE
                mProgressBar.visibility = View.VISIBLE
            }
        }

        mImvSkill1.setOnClickListener {
            if (tvKeyQ.currentTextColor == Color.BLACK) {
                imageClick(1)
                mImvPlay.visibility = View.GONE
                mProgressBar.visibility = View.VISIBLE
            }
        }

        mImvSkill2.setOnClickListener {
            if (tvKeyW.currentTextColor == Color.BLACK) {
                imageClick(2)
                mImvPlay.visibility = View.GONE
                mProgressBar.visibility = View.VISIBLE
            }
        }

        mImvSkill3.setOnClickListener {
            if (tvKeyE.currentTextColor == Color.BLACK) {
                imageClick(3)
                mImvPlay.visibility = View.GONE
                mProgressBar.visibility = View.VISIBLE
            }
        }

        mImvSkill4.setOnClickListener {
            if (tvKeyR.currentTextColor == Color.BLACK) {
                imageClick(4)
                mImvPlay.visibility = View.GONE
                mProgressBar.visibility = View.VISIBLE
            }
        }
    }

    private fun imageClick(index: Int) {
        tvKeyP.setTextColor(Color.BLACK)
        tvKeyQ.setTextColor(Color.BLACK)
        tvKeyW.setTextColor(Color.BLACK)
        tvKeyE.setTextColor(Color.BLACK)
        tvKeyR.setTextColor(Color.BLACK)
        when (index) {
            0 -> tvKeyP.setTextColor(Color.RED)
            1 -> tvKeyQ.setTextColor(Color.RED)
            2 -> tvKeyW.setTextColor(Color.RED)
            3 -> tvKeyE.setTextColor(Color.RED)
            4 -> tvKeyR.setTextColor(Color.RED)
        }

        mVdvSKill.stopPlayback()
        mImvPlay.setImageResource(R.drawable.ic_video_play)
        mTvSKillName.text = mDesList[index].split("###")[0]
        mTvSKillDes.text = mDesList[index].split("###")[1]
        mVdvSKill.setVideoPath(mVideoList[index])
    }
}