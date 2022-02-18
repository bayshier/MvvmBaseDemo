package com.lib.common.utils

import android.Manifest
import com.lib.base.utils.ToastUtil
import com.tbruyelle.rxpermissions2.RxPermissions

/**
 * 权限申请类
 */
object PermissionCheckUtil {
    fun check(activity: androidx.fragment.app.FragmentActivity) {
        RxPermissions(activity).request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe { aBoolean ->
            if (!aBoolean) {
                ToastUtil.showToast("缺少定位权限、存储权限，这会导致地图、导航、拍照等部分功能无法使用")
            }
        }
    }
}
