package com.lib.net

import com.lib.domain.entity.Demo
import com.lib.net.dto.Resource
import com.lib.net.local.entity.UserTestRoom
import kotlinx.coroutines.flow.Flow


/**
 *
 */
interface DataRepositorySource {
    suspend fun requestRecipes(): Flow<Resource<List<Demo>>>
    suspend fun doLogin(): Flow<Resource<String>>
    suspend fun removeUserTestRoom(userTestRoom: UserTestRoom): Flow<Resource<Int>>
    suspend fun insertUserTestRoom(userTestRoom: UserTestRoom):Flow<Resource<Long>>
    suspend fun getAllUserTestRoom(): Flow<Resource<List<UserTestRoom>>>
}
