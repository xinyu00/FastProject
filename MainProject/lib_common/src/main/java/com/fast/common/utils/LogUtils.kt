package com.fast.common.utils

import android.util.Log
import java.util.*


inline fun Any.logv(tag: String? = null) {
    Timber.v(this.toString().toString(), tag)
}

inline fun Any.logd(tag: String? = null) {
    Timber.d(this.toString(), tag)
}

inline fun Any.logi(tag: String? = null) {
    Timber.i(this.toString(), tag)
}

inline fun Any.logw(tag: String? = null) {
    Timber.w(this.toString(), tag)
}

inline fun Any.loge(tag: String? = null) {
    Timber.e(this.toString(), tag)
}

inline fun Any.logwtf(tag: String? = null) {
    Timber.wtf(this.toString(), tag)
}

inline fun Any.logl(priority: Int, tag: String? = null) {
    Timber.l(priority, this.toString(), tag)
}

private val timeMap = Collections.synchronizedMap(mutableMapOf<String, Long>())
fun logTimeStart(tag: String) {
    timeMap[tag] = System.currentTimeMillis()
}

fun logTimeEnd(tag: String) {
    timeMap.remove(tag)?.let { start ->
        Log.d("logTime", "$tag duration:${System.currentTimeMillis() - start}")
    }
}




