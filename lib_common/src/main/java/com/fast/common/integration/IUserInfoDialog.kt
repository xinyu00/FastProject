package com.fast.common.integration

interface IUserInfoListener {
    fun showInfo(userId: String?)
    fun getUserPermissions(): Int {
        return 0
    }
}