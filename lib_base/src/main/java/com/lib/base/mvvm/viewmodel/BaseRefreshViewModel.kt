package com.lib.base.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.lib.base.event.SingleLiveEvent


abstract class BaseRefreshViewModel<T> : BaseViewModel() {
    protected var mList = MutableLiveData<MutableList<T>>()

    var mUIChangeRefreshLiveData = UIChangeRefreshLiveData()

    inner class UIChangeRefreshLiveData : SingleLiveEvent<Any>() {
        private var mAutoRefreshLiveEvent: SingleLiveEvent<Void>? = null
        private var mStopRefreshLiveEvent: SingleLiveEvent<Boolean>? = null
        private var mStopLoadMoreLiveEvent: SingleLiveEvent<Boolean>? = null
        private var mStopLoadMoreWithNoMoreDataLiveEvent: SingleLiveEvent<Void>? = null

        val autoRefreshLiveEvent: SingleLiveEvent<Void> =
            createLiveData(mAutoRefreshLiveEvent).also { mAutoRefreshLiveEvent = it }
        val stopRefreshLiveEvent: SingleLiveEvent<Boolean> =
            createLiveData(mStopRefreshLiveEvent).also { mStopRefreshLiveEvent = it }
        val stopLoadMoreLiveEvent: SingleLiveEvent<Boolean> =
            createLiveData(mStopLoadMoreLiveEvent).also { mStopLoadMoreLiveEvent = it }
        val stopLoadMoreWithNoMoreDataEvent =
            createLiveData(mStopLoadMoreWithNoMoreDataLiveEvent).also {
                mStopLoadMoreWithNoMoreDataLiveEvent = it
            }
    }


    /**
     * ViewModel 层发布自动刷新事件
     */
    open fun postAutoRefreshEvent() {
        mUIChangeRefreshLiveData.autoRefreshLiveEvent.call()
    }

    /**
     * ViewModel 层发布停止刷新事件
     * @param boolean false 刷新失败
     */
    open fun postStopRefreshEvent(boolean: Boolean = true) {
        mUIChangeRefreshLiveData.stopRefreshLiveEvent.value = boolean
    }

    /**
     * ViewModel 层发布停止加载更多
     * @param boolean false 加载失败
     */
    open fun postStopLoadMoreEvent(boolean: Boolean = true) {
        mUIChangeRefreshLiveData.stopLoadMoreLiveEvent.value = boolean
    }

    /**
     * ViewModel 层发布完成加载并标记没有更多数据
     */
    open fun postStopLoadMoreWithNoMoreDataEvent() {
        mUIChangeRefreshLiveData.stopLoadMoreWithNoMoreDataEvent.call()
    }

    open fun getList(): MutableList<T>? {
        return mList.value
    }

    abstract fun refreshData()

    abstract fun loadMore()
}
