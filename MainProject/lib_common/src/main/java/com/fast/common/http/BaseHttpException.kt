package com.fast.common.http

/**
 * 对请求失败的业务进行封装
 */
data class BaseHttpException(val code: Int, override val message: String? = null): RuntimeException(message)

fun Throwable.isSpecialCode(code: Int): Boolean = if (this is BaseHttpException) this.code == code else false