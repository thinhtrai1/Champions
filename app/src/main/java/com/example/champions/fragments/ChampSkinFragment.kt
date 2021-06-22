package com.example.champions.fragments

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.champions.R
import com.example.champions.activities.DetailActivity
import com.example.champions.adapters.SkinViewPagerAdapter
import com.example.champions.adapters.SkinViewPagerAdapterImageView
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_champ_skin.*
import kotlinx.android.synthetic.main.item_viewpager_custom.view.*
import org.jsoup.nodes.Element
import kotlin.math.abs

class ChampSkinFragment: Fragment() {
    private val mJsoup by lazy { (context as DetailActivity).mJsoup }
    private val mList: ArrayList<String> = ArrayList()

    companion object {
        fun newInstance(): ChampSkinFragment {
            return ChampSkinFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_champ_skin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val metrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            activity!!.display?.getRealMetrics(metrics)
        } else {
            activity!!.windowManager.defaultDisplay.getMetrics(metrics)
        }

        val items = mJsoup
            .getElementsByClass("style__CarouselContainer-sc-1tlyqoa-11 cUeBFP")[0]
            .getElementsByTag("div")[1]
            .getElementsByTag("li")
        for (item: Element in items) {
            mList.add(item.getElementsByTag("img")[0].attr("src"))
        }

        with(mViewPagerSkin) {
            offscreenPageLimit = mList.size - 1
            clipToPadding = false
            adapter = SkinViewPagerAdapter(context!!, mList) {
                setCurrentItem(it, true)
            }
            setPadding(metrics.widthPixels / 3, 0, metrics.widthPixels / 3, 0)
            addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    mViewPagerSkinImageView.setCurrentItem(position, true)
                }
            })
            setPageTransformer(false) { page, position ->
                var scale = 1.3f
                if (position > 1f) scale -= (position - 1) * 0.6f
                else scale += (position - 1) * 0.6f
                if (scale < 0) scale = 0f
                page.cardView.apply {
                    scaleX = scale
                    scaleY = scale
                }
            }
        }

        with(mViewPagerSkinImageView) {
            adapter = SkinViewPagerAdapterImageView(context!!, mList) {
                zoom(mList[currentItem])
            }
            addOnPageChangeListener(object :
                ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    mViewPagerSkin.setCurrentItem(position, true)
                }
            })
            setPageTransformer(false) { page, position ->
                page.apply {
                    val pageWidth = width
                    when {
                        position < -1 -> alpha = 0f
                        position <= 0 -> {
                            alpha = 1f
                            translationX = 0f
                            scaleX = 1f
                            scaleY = 1f
                        }
                        position <= 1 -> {
                            alpha = 1 - position
                            translationX = pageWidth * -position
                            val scaleFactor = 0.75f + (1 - 0.75f) * (1 - abs(position))
                            scaleX = scaleFactor
                            scaleY = scaleFactor
                        }
                        else -> alpha = 0f
                    }
                }
            }
        }
    }

    private fun zoom(url: String) {
        Dialog(context!!, android.R.style.Theme_Black_NoTitleBar_Fullscreen).apply {
            val view = PhotoView(context)
            setContentView(view)
            show()
            view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate))
            Picasso.get().load(url).placeholder(R.drawable.image_default).rotate(90F).into(view)
        }
    }
}