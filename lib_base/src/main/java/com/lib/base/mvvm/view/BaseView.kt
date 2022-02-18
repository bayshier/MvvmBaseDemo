package com.lib.base.mvvm.view


interface BaseView : ILoadView, INoDataView, ITransView, INetErrView {
    fun initListener()
    fun initData()
    fun finishActivity()
}
