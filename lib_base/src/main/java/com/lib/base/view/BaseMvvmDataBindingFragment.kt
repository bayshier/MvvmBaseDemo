package com.lib.base.view

import android.view.ViewStub
import androidx.databinding.ViewDataBinding
import com.lib.base.mvvm.viewmodel.BaseViewModel
import com.lib.base.view.databinding.FragmentBinding
import com.lib.base.view.databinding.FragmentBindingHolder


abstract class BaseMvvmDataBindingFragment<V : ViewDataBinding, VM : BaseViewModel> :
    BaseMvvmFragment<VM>(), FragmentBindingHolder<V> by FragmentBinding() {

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
