package com.lib.base.mvvm.view


interface ITransView {
    //显示背景透明小菊花View,例如删除操作
    fun showTransLoadingView()

    //隐藏背景透明小菊花View
    fun hideTransLoadingView()
}
