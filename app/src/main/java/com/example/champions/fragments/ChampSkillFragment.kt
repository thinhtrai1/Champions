package com.example.champions.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.champions.R
import com.example.champions.activities.DetailActivity
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_champ_skill.*

class ChampSkillFragment: Fragment() {
    private val mJsoup by lazy { (context as DetailActivity).mJsoup }
    private val mSkillList: ArrayList<String> = ArrayList()
    private val mDesList: ArrayList<String> = ArrayList()
    private lateinit var mMediaPlayer: SimpleExoPlayer

    companion object {
        fun newInstance(): ChampSkillFragment {
            return ChampSkillFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_champ_skill, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val skills = mJsoup
            .getElementsByClass("style__WrapperInner-sc-18a4qs7-1 gFNUaI")[0]
            .getElementsByTag("button")
        for (item in skills) {
            mSkillList.add(item.getElementsByTag("img").attr("src"))
        }
        Picasso.get().load(mSkillList[0]).placeholder(R.drawable.image_default).into(mImvSkill0)
        Picasso.get().load(mSkillList[1]).placeholder(R.drawable.image_default).into(mImvSkill1)
        Picasso.get().load(mSkillList[2]).resize(256, 256).placeholder(R.drawable.image_default).into(mImvSkill2)
        Picasso.get().load(mSkillList[3]).resize(256, 256).placeholder(R.drawable.image_default).into(mImvSkill3)
        Picasso.get().load(mSkillList[4]).resize(256, 256).placeholder(R.drawable.image_default).into(mImvSkill4)

        val descriptions = mJsoup
            .getElementsByClass("style__AbilityInfoList-ulelzu-7 kAlIxD")[0]
            .getElementsByTag("li")
        for (item in descriptions) {
            mDesList.add(item.getElementsByTag("h5")[0].text() + "###" + item.getElementsByTag("p")[0].text())
        }

        val mVideoList = ArrayList<MediaItem>()
        val videos = mJsoup
            .getElementsByClass("style__VideoContainer-tmew42-2 cvZjKa")[0]
            .getElementsByTag("video")
        for (i in 0..4) {
            mVideoList.add(MediaItem.fromUri(videos[i].getElementsByTag("source")[0].attr("src")))
        }
        mMediaPlayer = SimpleExoPlayer.Builder(requireContext()).build()
        mMediaPlayer.setMediaItems(mVideoList)
        mMediaPlayer.addListener(object : Player.Listener {
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                imageClick(mVideoList.indexOf(mediaItem))
            }
        })
        mMediaPlayer.prepare()
        mVdvSKill.player = mMediaPlayer

        imageClick(0)
        mTvSKillDes.movementMethod = ScrollingMovementMethod()

        mImvSkill0.setOnClickListener {
            if (tvKeyP.currentTextColor == Color.BLACK) {
                imageClick(0)
            }
        }

        mImvSkill1.setOnClickListener {
            if (tvKeyQ.currentTextColor == Color.BLACK) {
                imageClick(1)
            }
        }

        mImvSkill2.setOnClickListener {
            if (tvKeyW.currentTextColor == Color.BLACK) {
                imageClick(2)
            }
        }

        mImvSkill3.setOnClickListener {
            if (tvKeyE.currentTextColor == Color.BLACK) {
                imageClick(3)
            }
        }

        mImvSkill4.setOnClickListener {
            if (tvKeyR.currentTextColor == Color.BLACK) {
                imageClick(4)
            }
        }
    }

    private fun imageClick(index: Int) {
        mMediaPlayer.seekToDefaultPosition(index)
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
        mTvSKillName.text = mDesList[index].split("###")[0]
        mTvSKillDes.text = mDesList[index].split("###")[1]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mMediaPlayer.release()
    }
}