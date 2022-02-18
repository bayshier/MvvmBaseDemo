package com.lib.net.remote.service

import com.lib.domain.base.BaseResponse
import com.lib.domain.entity.Demo
import retrofit2.Response
import retrofit2.http.GET

/**
 * 服务端提供数据接口方法
 */
interface RecipesService {
    @GET("recipes.json")
    suspend fun fetchRecipes(): Response<BaseResponse<List<Demo>>>
}