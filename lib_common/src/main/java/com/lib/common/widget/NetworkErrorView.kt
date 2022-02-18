package com.lib.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.lib.common.R

class NetworkErrorView : RelativeLayout {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        LayoutInflater.from(context)
            .inflate(R.layout.button_network_err, this, true)
        this.isClickable = true
        this.isFocusable = true
    }
}
