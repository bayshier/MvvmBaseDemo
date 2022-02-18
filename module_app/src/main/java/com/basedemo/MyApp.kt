package com.basedemo

import com.lib.base.manager.ActivityManager
import com.lib.base.module.ModuleApplication

/**
 * Describe:
 * App
 */
class MyApp : ModuleApplication(){
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(ActivityManager.instance)
    }
}
