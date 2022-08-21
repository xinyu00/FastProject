package com.fast.common.integration

import android.app.Application
import android.content.Context
import com.fast.common.app.AppLifecycle

interface ILifecycle {
    /**
     * 執行一次
     */
    fun applySingleton(app: Context)


    fun injectAppLifecycle(context: Context, lifecycle: MutableList<AppLifecycle>)

    fun injectActivityLifecycle(
        context: Context,
        lifecycle: MutableList<Application.ActivityLifecycleCallbacks>
    )
}