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
    const val kotlinVersion = "1.7.0"
    const val gradleVersion = "7.1.1"
    const val appcompatVersion = "1.5.0-rc01"
    const val constraintlayoutVersion = "2.1.3"
    const val junitVersion = "4.13.2"
    const val testJunitVersion = "1.1.3"
    const val espressoCoreVersion = "3.4.0"
    const val materialVersion = "1.6.1"
    const val matrixVersion = "2.0.8"
    const val lifecycleVersion = "2.5.1"
    const val composeVersion = "1.2.0"
    const val composeMaterial3Version = "1.0.0-alpha01"
    const val composeActivityVersion = "1.5.1"
    const val composeNavigationVersion = "2.4.0-alpha06"
    const val coilVersion = "1.3.2"
    const val accompanistVersion = "0.16.1"
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
    const val minSdkVersion = 23
    const val targetSdkVersion = 32

    //版本
    const val versionCode = 1
    const val versionName = "1.0"

    //依赖
    const val androidJUnitRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val matrix = "com.tencent.matrix:matrix-gradle-plugin:$matrixVersion"
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
    const val material = "com.google.android.material:material:$materialVersion"
    const val kotlinCoreKtx = "androidx.core:core-ktx:$kotlinVersion"
    const val appcompat = "androidx.appcompat:appcompat:$appcompatVersion"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:$constraintlayoutVersion"
    const val junit = "junit:junit:$junitVersion"
    const val testJunit = "androidx.test.ext:junit:$testJunitVersion"
    const val espressoCore = "androidx.test.espresso:espresso-core:$espressoCoreVersion"

    const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    const val gson = "com.google.code.gson:gson:2.8.6"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:2.9.0"


    // compose
    val composeUI = "androidx.compose.ui:ui:$composeVersion"
    val composeMaterial = "androidx.compose.material:material:$composeVersion"
    val composeMaterial3 = "androidx.compose.material3:material3:$composeMaterial3Version"
    val composeUITool = "androidx.compose.ui:ui-tooling-preview:$composeVersion"
    val activityCompose = "androidx.activity:activity-compose:$composeActivityVersion"

    // compose其他依赖
    val composeCoil = "io.coil-kt:coil-compose:$coilVersion"
    val composeMaterialIconExtended = "androidx.compose.material:material-icons-extended:$composeVersion"
    val accompanistInsets = "com.google.accompanist:accompanist-insets:$accompanistVersion"
    val accompanistSystemuicontroller = "com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion"
    val composeNavigation = "androidx.navigation:navigation-compose:$composeNavigationVersion"
    val composeConstraintlayout= "androidx.constraintlayout:constraintlayout-compose:1.0.0-beta02"

    val composeDebugUI = "androidx.compose.ui:ui-tooling:$composeVersion"
    // compose测试
    val composeTestJunit = "androidx.compose.ui:ui-test-junit4:$composeVersion"
    val composeTestUITool = "androidx.compose.ui:ui-test-manifest:$composeVersion"

    // lifecycle
    val livedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
    val viewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
    val lifecycleProcess = "androidx.lifecycle:lifecycle-process:$lifecycleVersion"

    // composeLifecycle
    val livedataCompose = "androidx.compose.runtime:runtime-livedata:$composeVersion"
    val viewmodelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion"


    val commonDep = mutableListOf(
        kotlinStdlib,
        kotlinCoreKtx,
        material,
        constraintLayout,
        livedataCompose,
        viewmodelCompose,
        runtimeKtx,
        lifecycleProcess,
        composeUI,
        composeUITool,
        activityCompose,
        composeCoil,
        composeNavigation,
        retrofit,
        gson,
        gsonConverter,
        composeMaterialIconExtended,
        accompanistInsets,
        accompanistSystemuicontroller,
        composeConstraintlayout,
        composeMaterial
    )

    val debugDep = mutableListOf(
        composeDebugUI,
        composeTestUITool
    )

    val testDep = mutableListOf(
        junit,
    )
    val androidTestDep = mutableListOf(
        testJunit,
        espressoCore,
        composeTestJunit
    )


}