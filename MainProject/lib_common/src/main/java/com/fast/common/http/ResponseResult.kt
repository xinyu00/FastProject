package com.fast.common.http

// 密封 禁止继承
sealed class ResponseResult

data class ResponseSuccess<T>(val data: T?) : ResponseResult()
data class ResponseError(val t: Throwable?) : ResponseResult()