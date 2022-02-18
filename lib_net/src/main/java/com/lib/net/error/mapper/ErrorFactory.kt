package com.lib.net.error.mapper

import com.lib.net.error.Error

interface ErrorFactory {
    fun getError(errorCode: Int): Error
}
