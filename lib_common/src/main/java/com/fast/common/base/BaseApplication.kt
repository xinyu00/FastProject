package com.fast.common.base

import android.app.Application
import android.content.Context
import com.fast.common.app.AppLifecycle
import com.fast.common.app.ApplicationDelegate
import com.fast.common.ui.theme.AppTheme

open class BaseApplication: Application() {

    companion object{
        lateinit var appContext: Application

        var currentTheme = AppTheme.Light
    }
    private lateinit var mAppDelegate: AppLifecycle

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        mAppDelegate = ApplicationDelegate(this)
        mAppDelegate.attachBaseContext(baseContext)
        mAppDelegate.onCreate(this)
    }


    override fun onTerminate() {
        super.onTerminate()
        mAppDelegate.onTerminate(this)
    }
}