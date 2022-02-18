package com.lib.base.view.databinding

import android.view.View
import android.view.ViewStub
import androidx.activity.ComponentActivity
import androidx.databinding.ViewDataBinding
import com.lib.base.view.viewbinding.IViewBindingHolder

interface ActivityBindingHolder<T : ViewDataBinding> : IViewBindingHolder<T> {

    fun inflateBinding(inflated: View, init: (binding: T) -> Unit)

    fun inflateBinding(activity: ComponentActivity, init: (binding: T) -> Unit)

    fun ComponentActivity.inflate(
        viewStub: ViewStub? = null,
        onClear: ((binding: T) -> Unit)? = null,
        init: (binding: T) -> Unit
    )
}