package com.lib.domain.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 */
@JsonClass(generateAdapter = true)
class BaseResponse<out T> constructor(
    @Json(name = "code")
    val code: Int = 0,
    @Json(name = "msg")
    val msg: String? = "",
    @Json(name = "data")
    val data: T? = null
)