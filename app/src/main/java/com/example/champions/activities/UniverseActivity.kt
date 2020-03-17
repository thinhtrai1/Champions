package com.example.champions.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Explode
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.webkit.WebViewClient
import com.example.champions.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_universe.*

class UniverseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_universe)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.enterTransition = (Explode().setDuration(1000))
        }

//        var rotate = RotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
//        rotate.duration = 300
//        rotate.repeatCount = 5
//        mImage3.startAnimation(rotate)

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
            .placeholder(R.drawable.image_default).into(mImage6)
    }
}
