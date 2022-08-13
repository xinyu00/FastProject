package com.cpzero.lib_base.integration

import android.app.Application
import android.content.Context
import com.cpzero.lib_base.app.AppLifecycle

interface ILifecycle {
    /**
     * 執行一次
     */
    fun applySingleton(app: Context)

    /**
     * 執行多次
     */
    fun applyInit(
        app: Context,
        tcpConfig: TcpConfig?,
        imConfig: ArrayList<IMConfig>?,
        rtcConfig: ArrayList<RtcConfig>?
    )

    fun injectAppLifecycle(context: Context, lifecycle: MutableList<AppLifecycle>)

    fun injectActivityLifecycle(
        context: Context,
        lifecycle: MutableList<Application.ActivityLifecycleCallbacks>
    )
}