package com.cpzero.lib_base.integration

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.fast.common.utils.logTimeEnd
import com.fast.common.utils.logTimeStart
import java.lang.reflect.Field

object GlobalModel {
    lateinit var appViewModelStore: ViewModelStore
        private set
    lateinit var appViewModelFactory: ViewModelProvider.Factory
        private set
    lateinit var modules: MutableList<ILifecycle>
        private set

    fun initConfig(app: Application) {
        appViewModelStore = ViewModelStore()
        appViewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(app)
    }


    fun initModules(context: Context): MutableList<ILifecycle> {
        logTimeStart("module___")
        modules = ManifestParser(context).parseLifecycles()
        logTimeEnd("module___")
        return modules
    }

    fun <T : ViewModel> getApplicationViewModel(
        cls: Class<T>,
        factory: ViewModelProvider.Factory = appViewModelFactory
    ): T {
        return ViewModelProvider(appViewModelStore, factory).get(cls)
    }

    fun removeViewModel(viewModel: ViewModel) {
        //先销毁
        val onCleared = ViewModel::class.java.getDeclaredMethod("clear")
        onCleared.isAccessible = true
        onCleared.invoke(viewModel)
        //再删除集合里的
        val key: String =
            "androidx.lifecycle.ViewModelProvider.DefaultKey:" + viewModel::class.java.canonicalName
        val field: Field = appViewModelStore.javaClass.getDeclaredField("mMap")
        field.isAccessible = true
        val mMap: HashMap<String, ViewModel> =
            field.get(appViewModelStore) as HashMap<String, ViewModel>
        mMap.remove(key)
        //put一个空的进去（但是不销毁可能造成内存泄漏，暂不使用）
//        val put = instance.appViewModelStore.javaClass
//            .getDeclaredMethod("put", String::class.java, ViewModel::class.java)
//        put.isAccessible = true
//        put.invoke(instance.appViewModelStore, key, null)
    }

    open fun onLoginExpired() {

    }
}