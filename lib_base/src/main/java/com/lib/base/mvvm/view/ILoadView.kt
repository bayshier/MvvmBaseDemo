package com.lib.base.mvvm.view


interface ILoadView {
    //显示初始加载的View，初始进来加载数据需要显示的View
    fun showInitLoadView()

    //隐藏初始加载的View
    fun hideInitLoadView()
}
