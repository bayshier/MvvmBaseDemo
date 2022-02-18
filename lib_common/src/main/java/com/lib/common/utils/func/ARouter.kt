package com.lib.common.utils.func

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter

/**
 */



fun startActivity(path: String, bundle: Bundle?) {
    ARouter.getInstance().apply {
        build(path).with(bundle).navigation()
    }
}