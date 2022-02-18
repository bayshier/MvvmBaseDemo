package com.lib.base.module

import com.lib.base.BaseApplication
import com.lib.common.URL_BASE
import com.lib.net.config.NetConfig


open class ModuleApplication : BaseApplication() {

    override fun initOnlyMainProcessInLowPriorityThread() {
        super.initOnlyMainProcessInLowPriorityThread()
        initNet()
    }

    private fun initNet() {
        val config = NetConfig.Builder()
            .setBaseUrl(URL_BASE)
            .build()
        config.initContext(this)
    }
}
