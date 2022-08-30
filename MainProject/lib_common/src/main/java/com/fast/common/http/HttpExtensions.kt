package com.fast.common.http

import com.fast.res.R
import com.fast.common.base.BaseApplication
import com.fast.common.integration.GlobalModel
import com.fast.common.utils.ToastUtils.showToast
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

fun <T> request(
    scope: CoroutineScope,
    callback: RequestCallback<T>? = null,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    callbackDispatcher: CoroutineDispatcher = Dispatchers.Main,
    block: suspend CoroutineScope.() -> T
): Job = scope.launch(callbackDispatcher) {
    try {
        when (val result = withContext(dispatcher, block)) {
            is BaseResponse<*> -> {
                when (result.code) {
                    HttpCode.SUCCESS -> callback?.onSuccess(ResponseSuccess(result))
                    else -> throw BaseHttpException(
                        result.code,
                    )
                }
            }
            is BasePageResponse<*> -> {
                when (result.code) {
                    HttpCode.SUCCESS -> callback?.onSuccess(ResponseSuccess(result))
                    else -> throw BaseHttpException(
                        result.code,
                    )
                }
            }
            else -> {
                // result 不是BaseResponse类型  直接将结果返回
                callback?.onSuccess(ResponseSuccess(result))
            }
        }
    } catch (e: Exception) {
        callback?.onError(ResponseError(e))
    }
}

interface RequestCallback<T> {
    fun onSuccess(result: ResponseSuccess<T>)
    fun onError(error: ResponseError) {
        // 针对Exception进行统一处理
        error.t?.apply {
            if (this is BaseHttpException) {
                when (this.code) {
                    HttpCode.REFRESH_TOKEN -> {
//                        AppTokenModel().refreshToken()
                        return
                    }
                    HttpCode.LOGIN_EXPIRED, HttpCode.ABNORMAL_TOKEN,
                    HttpCode.UNIFIED_PARAMETER_NOT_EXIST, HttpCode.UNIFIED_PARAMETER_ERROR,
                    HttpCode.SIGNATURE_ERROR, HttpCode.REQUESTED_VALIDITY_EXCEEDED -> {
                        showToast(BaseApplication.instance.getString(R.string.token_error))
                        GlobalModel.onLoginExpired()
                        return
                    }
                    HttpCode.EQUIPMENT_AUTHORIZATION_EXCEPTION -> {
                        showToast(BaseApplication.instance.getString(R.string.login_other_device))
                        GlobalModel.onLoginExpired()
                        return
                    }
                    HttpCode.SERVICE_INUPGRADE -> {
                        showToast(BaseApplication.instance.getString(R.string.service_upgrade))
                        return
                    }
                }
            }
            showToast(this.getShowMsg())
        }
    }
}

fun Throwable.getShowMsg() = when (this) {
    is HttpException -> "${
        when (code()) {
            in 301..307 -> "网络被劫持，请检查网络"
            401 -> "文件未授权或证书错误"
            403 -> "请求被服务器拒绝"
            404 -> "请求地址不存在"
            408 -> "请求超时，服务器未响应"
            in 500..505 -> "服务器异常"
            else -> message()
        }
    }，错误码：${code()}"
    is BaseHttpException -> message ?: "错误码：${code}"
//    is UnknownHostException -> "网络不可用"
//    is ConnectException -> "连接异常"
//    is NetworkErrorException -> "网络不可用"
    is SocketTimeoutException -> ""
//    is SocketException -> "网络异常"
//    is IOException -> "请求网络超时"
//    is ParseException -> "json格式错误"
//    is JSONException -> "json解析错误"
//    is JsonParseException -> "json参数错误"
//    is SSLHandshakeException -> "证书验证失败"
//    is JsonIOException -> "数据解析错误"
    else -> message ?: ""
}

const val MESSAGE_PLACEHOLDER = "{${'$'}}"
const val MESSAGE_PLACEHOLDER_SECOND = "{${'$'}${'$'}}"


suspend fun <T> Call<BaseResponse<T>>.await(): BaseResponse<T> {
    return suspendCoroutine { continuation ->
        enqueue(object : Callback<BaseResponse<T>> {
            override fun onResponse(
                call: Call<BaseResponse<T>>,
                response: Response<BaseResponse<T>>
            ) {
                val body = response.body()
                if (body != null) {
                    continuation.resume(body)
                } else {
                    continuation.resumeWithException(
                        RuntimeException("response body is null")
                    )
                }
            }

            override fun onFailure(call: Call<BaseResponse<T>>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })
    }
}
