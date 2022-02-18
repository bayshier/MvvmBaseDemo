package com.lib.net.utils.ext.view

import android.content.Context
import android.widget.Toast
import com.lib.net.config.NetAppContext

/**
 * Describe:
 * <p></p>
 */


/**
 * 使用方式
 * "This is Toast".showToast(context,Toast.LENGTH_SHORT)
 */
fun String.showToast(context: Context = NetAppContext.getContext(), duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, duration).show()
}

/**
 * 使用方式
 * "This is Toast".showToast(context,Toast.LENGTH_SHORT)
 */
fun Int.showToast(context: Context = NetAppContext.getContext(), duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, duration).show()
}
