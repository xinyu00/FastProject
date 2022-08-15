package com.fast.common.utils

import android.widget.Toast
import com.fast.common.base.BaseApplication

object Utils {
    fun showToast(msg: String?="Empty Msg") {
        Toast.makeText(BaseApplication.appContext, msg.toString(), Toast.LENGTH_SHORT).show()
    }
}