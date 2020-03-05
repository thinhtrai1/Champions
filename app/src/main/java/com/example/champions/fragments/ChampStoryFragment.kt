package com.example.champions.fragments

import android.os.Build
import android.os.Bundle
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.champions.R
import com.example.champions.activities.DetailActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_champ_story.*

class ChampStoryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_champ_story, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var mActivity = activity as DetailActivity

        var name = mActivity.mJsoup
            .getElementsByClass("style__Title-sc-14gxj1e-3 iLTyui")[0]
            .getElementsByTag("span")[0]
            .text()
        mActivity.setTitle(name)
        var nickname = mActivity.mJsoup
            .getElementsByClass("style__Intro-sc-14gxj1e-2 fmCNnE")[0]
            .getElementsByTag("span")[0]
            .text()
        mTvNickname.text = nickname
        var story = mActivity.mJsoup
            .getElementsByClass("style__Desc-sc-1o884zt-9 hZPZqS")[0]
            .getElementsByTag("p")[0]
            .text()
        mTvStory.text = story.split(" See More")[0]
        var image = mActivity.mJsoup
            .getElementsByClass("style__BackgroundImage-sc-1o884zt-2 cIdAXF")[0]
            .getElementsByTag("img")
            .attr("src")
        Picasso.get().load(image).placeholder(R.drawable.image_default).into(mImvBanner)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mTvStory.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }
        mTvStory.movementMethod = ScrollingMovementMethod()
    }
}