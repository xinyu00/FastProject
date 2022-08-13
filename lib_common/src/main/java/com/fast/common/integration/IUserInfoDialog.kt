package com.cpzero.lib_base.integration

interface IUserInfoListener {
    fun showInfo(userId: String?)
    fun getUserPermissions(): Int {
        return 0
    }
}