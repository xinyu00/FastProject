package com.fast.common.app

import android.app.Application
import android.content.Context

interface AppLifecycle {
    fun attachBaseContext(ctx:Context)

    fun  onCreate(app:Application)

    fun  onTerminate(app: Application)
}