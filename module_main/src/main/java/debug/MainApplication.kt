package debug

import com.lib.base.module.ModuleApplication
import com.lib.common.URL_BASE
import com.lib.net.config.NetConfig

class MainApplication : ModuleApplication() {
    override fun onCreate() {
        super.onCreate()
        initNet()
    }

    private fun initNet() {
        val config = NetConfig.Builder()
            .setBaseUrl(URL_BASE)
            .build()
        config.initContext(this)
    }
}
