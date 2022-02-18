package com.lib.base.view.viewbinding

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


interface FragmentViewBinding<T : ViewBinding> : IViewBindingHolder<T> {

    fun Fragment.inflate(
        inflate: () -> T,
        onClear: ((binding: T) -> Unit)? = null,
        init: ((binding: T) -> Unit)? = null
    ): View
}