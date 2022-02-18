package com.lib.common.utils.ext.view

import android.widget.TextView

/**
 */

/**
 * if [TextView.getText] is not empty, invoke f()
 * otherwise invoke t()
 */
fun TextView.notEmpty(f: TextView.() -> Unit, t: TextView.() -> Unit) {
    if (text.toString().isNotEmpty()) f() else t()
}