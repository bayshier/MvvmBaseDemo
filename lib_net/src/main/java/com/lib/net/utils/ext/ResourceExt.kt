package com.lib.net.utils.ext

import com.lib.log.KLog
import com.lib.net.dto.Resource

/**
 * Describe:
 * 结果包装类拓展
 */


/**
 * 快捷拓展获取到相应的数据结果
 * @loading 加载中回调
 * @fail 失败回调
 * @success 成功回调
 */
fun <T> Resource<T>.launch(
    loading: (() -> Unit)? = null,
    fail: ((code: Int?) -> Unit)? = null,
    success: (data: T?) -> Unit
) {
    when (this) {
        is Resource.Success -> success(data)
        is Resource.Loading -> loading?.invoke()
        else -> {
            this.errorCode.let { KLog.e("Resource", "--------->$it") }
            fail?.let { it(errorCode) }
        }
    }
}