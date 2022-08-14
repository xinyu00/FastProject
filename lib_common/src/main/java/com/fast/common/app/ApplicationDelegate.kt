package com.fast.common.app

import android.app.Application
import android.content.Context
import com.fast.common.integration.GlobalModel
import com.fast.common.utils.CoilImageLoader


class ApplicationDelegate(application: Application) : AppLifecycle {
    private var mAppLifeCycles = mutableListOf<AppLifecycle>()
    private var mActivityLifeCycles = mutableListOf<Application.ActivityLifecycleCallbacks>()

    private var mApplication: Application = application

    init {
        GlobalModel.initModules(application)
        for (module in GlobalModel.modules) {
            //注入外部由开发者实现的生命周期回调
            module.injectAppLifecycle(application, mAppLifeCycles)
            module.injectActivityLifecycle(application, mActivityLifeCycles)
            module.applySingleton(application)
        }
    }

    override fun attachBaseContext(ctx: Context) {
        for (lifecycle in mAppLifeCycles) {
            lifecycle.attachBaseContext(ctx)
        }
    }

    override fun onCreate(app: Application) {
//        if (BuildConfig.DEBUG) {
//            ARouter.openDebug()
//            ARouter.openLog()
//        }
//        ARouter.init(app)

        CoilImageLoader.initImageLoader(app)

        //执行开发者外部注册的生命周期
        for (lifecycle in mAppLifeCycles) {
            lifecycle.onCreate(app)
        }
        //注册开发者外部扩展的Activity生命周期,可能是由宿主或者插件去扩展的
        for (lifecycle in mActivityLifeCycles) {
            mApplication.registerActivityLifecycleCallbacks(lifecycle)
        }
    }

    override fun onTerminate(app: Application) {
        //反注册Activity生命周期回调
        for (lifecycle in mActivityLifeCycles) {
            mApplication.unregisterActivityLifecycleCallbacks(lifecycle)
        }

        for (lifecycle in mAppLifeCycles) {
            lifecycle.onTerminate(app)
        }

//        ARouter.getInstance().destroy()
    }
}