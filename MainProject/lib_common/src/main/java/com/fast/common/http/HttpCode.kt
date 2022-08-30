package com.fast.common.http

object HttpCode {
    const val SUCCESS: Int = 0
    const val ERROR: Int = -1
    const val REFRESH_TOKEN = 201  //刷新Token
    const val LOGIN_EXPIRED = 10990001 //token过期
    const val ABNORMAL_TOKEN = 10990002//异常令牌
    const val UNIFIED_PARAMETER_NOT_EXIST = 10990003//统一参数不存在
    const val UNIFIED_PARAMETER_ERROR = 10990004//统一参数异常
    const val SERVICE_INUPGRADE = 10990005//服务器正在升级
    const val SIGNATURE_ERROR = 10990006//签名错误
    const val REQUESTED_VALIDITY_EXCEEDED = 10990007//请求有效性超过
    const val EQUIPMENT_AUTHORIZATION_EXCEPTION = 10990008//账号已在其他设备登录
    const val GIFT_VERSION_UPDATE = 20181000//礼物版本升级
}

object HttpConstants {
    const val TOKEN = "token"
    const val BODY = "body"
    const val UNIFIED = "unified"
    const val URL = "url"
    const val SALT = "salt"
    const val TIMESTAMP = "timestamp"
    const val NONCE = "nonce"
    const val JOIN_CHAR = '='
    const val SIGNATURE = "signature"

    var token: String? = null
}