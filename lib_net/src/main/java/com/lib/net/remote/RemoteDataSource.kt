package com.lib.net.remote

import com.lib.domain.entity.Demo
import com.lib.net.dto.Resource

/**
 * 服务端所有提供数据方法
 */
internal interface RemoteDataSource {
    suspend fun requestRecipes(): Resource<List<Demo>>
}
