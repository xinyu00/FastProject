package com.fast.common.base

import android.app.Application
import android.content.Context
import com.fast.common.app.AppLifecycle
import com.fast.common.app.ApplicationDelegate

class BaseApplication: Application() {


    private lateinit var mAppDelegate: AppLifecycle

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        mAppDelegate = ApplicationDelegate(this)
        mAppDelegate.attachBaseContext(baseContext)
        mAppDelegate.onCreate(this)
    }
    companion object {
        @JvmStatic
        lateinit var instance: BaseApplication
            private set
    }

    override fun onTerminate() {
        super.onTerminate()
        mAppDelegate.onTerminate(this)
    }
}