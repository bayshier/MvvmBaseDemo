package com.lib.base.widget

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.lib.base.R

class LoadingInitView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {
    private val mAnimationDrawable: AnimationDrawable

    init {
        View.inflate(context, R.layout.view_init_loading, this)
        val imgLoading = findViewById<ImageView>(R.id.img_init_loading)
        mAnimationDrawable = imgLoading.drawable as AnimationDrawable
    }

    fun startLoading() {
        mAnimationDrawable.start()
    }

    fun stopLoading() {
        mAnimationDrawable.stop()
    }

    fun loading(b: Boolean) {
        if (b) {
            startLoading()
        } else {
            stopLoading()
        }
    }
}
