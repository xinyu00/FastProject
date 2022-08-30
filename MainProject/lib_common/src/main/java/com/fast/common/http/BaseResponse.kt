package com.fast.common.http

import java.io.Serializable

data class BaseResponse<T>(
    val code: Int, //返回码
    val data: T?, //数据集
    val message: MutableList<String>?
) : Serializable

data class BasePageResponse<T>(
    val code: Int, //返回码
    val data: MutableList<T>?, //数据集
    val message: MutableList<String>?,
    val pageNo: Int, // 页码
    val totalRows: Int // 总数据量
) : Serializable