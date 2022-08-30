package com.fast.common.integration

import android.content.Context
import androidx.startup.Initializer
import com.fast.common.BuildConfig
import com.fast.common.utils.Timber
import com.fast.common.utils.logTimeEnd
import com.fast.common.utils.logTimeStart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GlobalConfigInitializer : Initializer<Unit> {
    val tag = "FAST_PROJECT"
    override fun create(context: Context) {
        logTimeStart("GlobalConfigInitializer")
        CoroutineScope(Dispatchers.Default).launch {
//        MultiDex.install(context)
            if (BuildConfig.DEBUG) {
                Timber.plant(Timber.DebugTree())
                Timber.tag(tag)
            }
//            else {
//                cockroachInstall(context.applicationContext)
//            }

        }
        logTimeEnd("GlobalConfigInitializer")
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }

}