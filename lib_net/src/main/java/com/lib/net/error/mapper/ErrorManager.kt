package com.lib.net.error.mapper

import com.lib.net.error.Error

/**
 * Describe:
 * <p>获取错误信息实现</p>
 */
class ErrorManager constructor(private val errorMapper: ErrorMapper) : ErrorFactory {
    override fun getError(errorCode: Int): Error {
        return Error(code = errorCode, description = errorMapper.errorsMap.getValue(errorCode))
    }
}
