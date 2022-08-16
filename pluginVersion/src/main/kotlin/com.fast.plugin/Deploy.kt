package com.fast.plugin
import org.gradle.kotlin.dsl.provideDelegate
import java.text.SimpleDateFormat
import java.util.*

object Deploy {
    // 获取当前系统时间
    fun getSystemTime(): String {
        val simpleDateFormat = SimpleDateFormat("yyyyMMdd_HH_mm_ss", Locale.CHINA)
        return simpleDateFormat.format(System.currentTimeMillis())
    }

    // 版本控制
    const val kotlinVersion = "1.6.10"
    const val gradleVersion = "7.1.1"
    const val kotlinCoreKtxVersion = "1.7.0"
    const val appcompatVersion = "1.4.1"
    const val constraintlayoutVersion = "2.1.3"
    const val junitVersion = "4.13.2"
    const val testJunitVersion = "1.1.3"
    const val espressoCoreVersion = "3.4.0"
    const val materialVersion = "1.6.1"
    const val matrixVersion = "2.0.8"
    const val lifecycleVersion = "2.5.1"

    //插件
    const val pluginAndroidId = "com.android.application"
    const val pluginAndroid = "android"
    const val pluginAndroidLibrary = "com.android.library"
    const val pluginKotlin = "org.jetbrains.kotlin.android"
    const val pluginMatrix = "com.tencent.matrix-plugin"

    const val pluginAndroidExtensions = "android.extensions"

    //SDK版本
    const val compileSdkVersion = 32
    const val applicationId = "com.fast.app"
    const val minSdkVersion = 21
    const val targetSdkVersion = 32

    //版本
    const val versionCode = 1
    const val versionName = "1.0"

    //依赖
    const val androidJUnitRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val matrix = "com.tencent.matrix:matrix-gradle-plugin:$matrixVersion"
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
    const val material = "com.google.android.material:material:$materialVersion"
    const val kotlinCoreKtx = "androidx.core:core-ktx:$kotlinCoreKtxVersion"
    const val appcompat = "androidx.appcompat:appcompat:$appcompatVersion"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:$constraintlayoutVersion"
    const val junit = "junit:junit:$junitVersion"
    const val testJunit = "androidx.test.ext:junit:$testJunitVersion"
    const val espressoCore = "androidx.test.espresso:espresso-core:$espressoCoreVersion"


    // lifecycle
    val livedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
    val viewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
    val lifecycleProcess = "androidx.lifecycle:lifecycle-process:$lifecycleVersion"

    val commonDep = mutableListOf(
        kotlinStdlib,
        material,
        kotlinCoreKtx,
        appcompat,
        constraintLayout,
        junit,
        testJunit,
        espressoCore,
        livedataKtx,
        viewmodelKtx,
        runtimeKtx,
        lifecycleProcess,

    )


}