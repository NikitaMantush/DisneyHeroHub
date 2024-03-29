package com.mantushnikita.disneyherohub3.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import com.mantushnikita.disneyherohub3.R
import com.mantushnikita.disneyherohub3.databinding.ViewBannerBinding


class BannerView(
    context: Context, attrs: AttributeSet?
) : FrameLayout(context, attrs) {


    private var binding: ViewBannerBinding? = null

    init {
        binding = ViewBannerBinding.inflate(LayoutInflater.from(context), this, true)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.BannerView,
            0, 0
        ).run {
            val iconSrcId = getResourceId(R.styleable.BannerView_iconBanner, 0)
            binding?.run {
                iconImageView.setImageResource(iconSrcId)
                bannerTitleTextView.text = getString(R.styleable.BannerView_titleTextBanner)
                bannerMessageTextView.text = getString(R.styleable.BannerView_messageTextBanner)
                cross.setColorFilter(R.styleable.BannerView_crossIconColor).run {
                    setOnClickListener {
                        clearAnimation()
                        visibility = View.GONE
                    }
                }

            }
        }
    }

    private fun setBannerTitle(title: String) {
        binding?.bannerTitleTextView?.text = title
    }

    private fun setBannerMessage(title: String) {
        binding?.bannerMessageTextView?.text = title
    }

    private fun setBannerImage(iconResId: Int) {
        binding?.iconImageView?.setImageResource(iconResId)
    }

    private fun setBannerBackground(backgroundResId: Int) {
        binding?.root?.setBackgroundResource(backgroundResId)
    }

    fun setTextColor(color: Int) {
        binding?.bannerTitleTextView?.setTextColor(color)
        binding?.bannerMessageTextView?.setTextColor(color)
    }

    fun showBanner(
        title: String, message: String, iconResId: Int, backgroundResId: Int, textColor: Int
    ) {
        setBannerTitle(title)
        setBannerMessage(message)
        setBannerImage(iconResId)
        setBannerBackground(backgroundResId)
        setTextColor(context.getColor(textColor))
        show()
        handler.postDelayed({ hide() }, 3000)
    }

    private fun show() {
        val fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        visibility = View.VISIBLE
        startAnimation(fadeInAnimation)
    }

    private fun hide() {
        if (visibility == View.VISIBLE) {
            val fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
            fadeOutAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}

                override fun onAnimationEnd(animation: Animation) {
                    visibility = View.GONE
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
            startAnimation(fadeOutAnimation)
        }
    }

}
