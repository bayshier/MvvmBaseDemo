package com.lib.base.view.databinding

import androidx.databinding.ViewDataBinding
import com.lib.base.view.viewbinding.IViewBindingHolder

internal class ViewDataBindingHolder<VB : ViewDataBinding> : IViewBindingHolder.Holder<VB>() {

    override fun clearBinding(clear: VB.() -> Unit) {
        super.clearBinding {
            clear()
            unbind()
        }
    }
}