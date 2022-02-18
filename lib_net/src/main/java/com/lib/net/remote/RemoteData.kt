package com.lib.net.remote

import android.text.TextUtils
import com.lib.domain.base.BaseResponse
import com.lib.domain.entity.Demo
import com.lib.log.KLog
import com.lib.net.BuildConfig
import com.lib.net.config.NetAppContext
import com.lib.net.dto.Resource
import com.lib.net.error.*
import com.lib.net.error.mapper.ErrorManager
import com.lib.net.error.mapper.ErrorMapper
import com.lib.net.remote.service.RecipesService
import com.lib.net.utils.NetworkConnectivity
import com.lib.net.utils.ThreadUtils
import com.lib.net.utils.ext.view.showToast
import retrofit2.Response
import java.io.IOException


/**
 * 服务端数据提供者实现
 */
class RemoteData
constructor(
    private val retrofitManager: RetrofitManager,
    private val networkConnectivity: NetworkConnectivity
) : RemoteDataSource {
    private val errorManager by lazy { ErrorManager(ErrorMapper()) }

    override suspend fun requestRecipes(): Resource<List<Demo>> {
        //创建接口服务
        val recipesService = retrofitManager.create<RecipesService>()

        return dealDataWhen(processCall(recipesService::fetchRecipes))
    }


    /**
     * 数据结构体的返回处理
     */
    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            //若当前客户端未打开数据连接开关
            return showToast(NOT_NETWORD)
        }
        return try {
            val response = responseCall.invoke()
            if (response.code() in SUCCESS until UNAUTHORIZED) {
                response.body()
            } else {
                when (response.code()) {
                    UNAUTHORIZED -> showToast(UNAUTHORIZED)
                    FORBIDDEN -> showToast(FORBIDDEN)
                    NOT_FOUND -> showToast(NOT_FOUND)
                    REQUEST_TIMEOUT -> showToast(REQUEST_TIMEOUT)
                    INTERNAL_SERVER_ERROR -> showToast(INTERNAL_SERVER_ERROR)
                    SERVICE_UNAVAILABLE -> showToast(SERVICE_UNAVAILABLE)
                    else -> showToast(UNKNOWN)
                }
            }
        } catch (e: IOException) {
            if (BuildConfig.DEBUG) {
                ThreadUtils.runOnUiThread {
                    e.message?.showToast()
                }
                KLog.e("RemoteData", e)
            }
            showToast(NETWORD_ERROR)
        }
    }

    /**
     * 处理相应结果
     */
    private inline fun <reified T> dealDataWhen(any: Any?): Resource<T> {
        return when (any) {
            is BaseResponse<*> -> {
                Resource.Success(data = toAs(if (any.data != null) any.data else any.msg))
            }
            else -> {
                Resource.DataError(errorCode = toAs(any))
            }
        }
    }

    /**
     * 类型转换
     */
    private inline fun <reified T> toAs(obj: Any?): T {
        return obj as T
    }

    /**
     * 错误吐司
     */
    private fun showToast(code: Int, msg: String? = ""): Int {
        ThreadUtils.runOnUiThread {
            if (!TextUtils.isEmpty(msg)) msg?.showToast(NetAppContext.getContext())
            else errorManager.getError(code).description.showToast(NetAppContext.getContext())
        }
        return code
    }
}
