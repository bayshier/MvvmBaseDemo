package com.lib.base.view

import android.view.ViewStub
import androidx.databinding.ViewDataBinding
import com.lib.base.mvvm.viewmodel.BaseViewModel
import com.lib.base.view.databinding.ActivityBinding
import com.lib.base.view.databinding.ActivityBindingHolder


abstract class BaseMvvmDataBindingActivity<V : ViewDataBinding, VM : BaseViewModel> :
    BaseMvvmActivity<VM>(), ActivityBindingHolder<V> by ActivityBinding() {

    override fun initContentView(mViewStubContent: ViewStub) {
        with(mViewStubContent) {
            layoutResource = onBindLayout()
            inflate(this) { binding ->
                onBindVariableId().forEach { pair ->
                    binding.setVariable(pair.first, pair.second)
                }
            }
        }
    }

    abstract fun onBindVariableId(): MutableList<Pair<Int, Any>>
}
