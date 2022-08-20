package com.fast.common.utils

object UnCheckedUtils {

    @Suppress("UNCHECKED_CAST")
    fun <T> cast(obj: Any?): T {
        return obj as T
    }
}