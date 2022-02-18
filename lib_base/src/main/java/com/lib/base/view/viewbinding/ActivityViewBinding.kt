package com.lib.base.view.viewbinding

import android.view.ViewStub
import androidx.activity.ComponentActivity
import androidx.viewbinding.ViewBinding


interface ActivityViewBinding<T : ViewBinding> : IViewBindingHolder<T> {

    fun ComponentActivity.inflate(
        inflate: () -> T,
        isRoot: Boolean ?= true,
        onClear: ((T) -> Unit)? = null,
        init: ((T) -> Unit)? = null
    ): T

    fun ComponentActivity.inflate(
        bindingClass: Class<T>,
        onClear: ((T) -> Unit)? = null,
        init: (T) -> Unit
    )

    fun ComponentActivity.inflate(
        viewStub: ViewStub,
        bindingClass: Class<T>,
        onClear: ((T) -> Unit)? = null,
        init: (T) -> Unit
    )
}