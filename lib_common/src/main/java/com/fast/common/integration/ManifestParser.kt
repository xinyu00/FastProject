package com.cpzero.lib_base.integration

import android.content.Context
import android.content.pm.PackageManager

class ManifestParser(private val context: Context) {

    fun parseConfigs(): MutableList<IGlobalConfig> {
        val modules = mutableListOf<IGlobalConfig>()
        try {
            val appInfo = context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA
            )

            val keySet = appInfo.metaData?.keySet()
            if (keySet != null) {
                //for 循环不允许使用可空的值
                for (key: String in keySet) {
                    if (CONFIG_MODULE_VALUE == appInfo.metaData.get(key)) {
                        val parseModule = parseConfigModule(key)
                        if (parseModule != null) {
                            modules.add(parseModule)
                        }
                    }
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            throw  RuntimeException("Unable to find metadata to parse ConfigModule", e)
        }

        return modules
    }

    fun parseLifecycles(): MutableList<ILifecycle> {
        val modules = mutableListOf<ILifecycle>()
        try {
            val appInfo = context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA
            )

            val keySet = appInfo.metaData?.keySet()
            if (keySet != null) {
                //for 循环不允许使用可空的值
                for (key: String in keySet) {
                    if (LIFECYCLE_MODULE_VALUE == appInfo.metaData.get(key)) {
                        val parseModule = parseLifecycleModule(key)
                        if (parseModule != null) {
                            modules.add(parseModule)
                        }
                    }
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            throw  RuntimeException("Unable to find metadata to parse LifecycleModule", e)
        }

        return modules
    }

    companion object {
        const val LIFECYCLE_MODULE_VALUE = "LifecycleModule"
        private fun parseLifecycleModule(className: String): ILifecycle? {
            var obj: ILifecycle? = null
            try {

                //获取class 对象(使用的是Java中的反射)
                val cls = Class.forName(className)
                //判断cls是不是IGlobalConfig的子类
                if (ILifecycle::class.java.isAssignableFrom(cls)) {
                    //创建实例
                    obj = cls.newInstance() as ILifecycle
                } else {
                    throw  IllegalArgumentException("Expected instance of ILifecycle, but found: $className")
                }
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }

            return obj
        }

        const val CONFIG_MODULE_VALUE = "ConfigModule"
        private fun parseConfigModule(className: String): IGlobalConfig? {
            var obj: IGlobalConfig? = null
            try {

                //获取class 对象(使用的是Java中的反射)
                val cls = Class.forName(className)
                //判断cls是不是IGlobalConfig的子类
                if (IGlobalConfig::class.java.isAssignableFrom(cls)) {
                    //创建实例
                    obj = cls.newInstance() as IGlobalConfig
                } else {
                    throw  IllegalArgumentException("Expected instance of IConfigModule, but found: $className")
                }
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }

            return obj
        }
    }
}