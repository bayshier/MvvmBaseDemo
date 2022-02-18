package com.lib.base.view

import androidx.databinding.ViewDataBinding
import com.lib.base.mvvm.view.BaseRefreshView
import com.lib.base.mvvm.viewmodel.BaseRefreshViewModel


abstract class BaseMvvmRefreshDataBindingActivity<T, V : ViewDataBinding, VM : BaseRefreshViewModel<T>> :
    BaseMvvmDataBindingActivity<V, VM>(),
    BaseRefreshView {

    protected var isRefresh = true

    override fun initCommonView() {
        super.initCommonView()
        initRefreshView()
        initBaseViewRefreshObservable()
    }

    protected abstract fun onBindRefreshLayout(): Int

    protected abstract fun enableRefresh(): Boolean

    protected abstract fun enableLoadMore(): Boolean

    /**
     * 初始化刷新组件
     */
    private fun initRefreshView() {

    }

    /**
     * 初始化观察者 ViewModel 层加载完数据的回调通知当前页面事件已完成
     */
    private fun initBaseViewRefreshObservable() {
        mViewModel.mUIChangeRefreshLiveData.autoRefreshLiveEvent.observe(this) {
            autoLoadData()
        }
        mViewModel.mUIChangeRefreshLiveData.stopRefreshLiveEvent.observe(this) {
            stopRefresh(it)
        }
        mViewModel.mUIChangeRefreshLiveData.stopLoadMoreLiveEvent.observe(this) {
            stopLoadMore(it)
        }
    }

    override fun enableRefresh(b: Boolean) {
    }

    override fun enableLoadMore(b: Boolean) {

    }

    override fun enableAutoLoadMore(b: Boolean) {

    }

    override fun onAutoLoadEvent() {

    }

    override fun autoLoadData() {

    }

    override fun stopRefresh(boolean: Boolean) {

    }

    override fun stopLoadMore(boolean: Boolean) {

    }

}
