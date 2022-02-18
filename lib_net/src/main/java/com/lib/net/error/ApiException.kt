package com.lib.net.error

/**
 * Describe:
 * <p>接口异常</p>
 */
class ApiException constructor(
    var code: Int,
    override var message: String = "",
    var e: Throwable? = null
) : Exception(message, e)