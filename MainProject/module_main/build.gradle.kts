import com.fast.plugin.Deploy

plugins {
    val isBuildModule = false
    if (isBuildModule){  // 作为独立app运行
        id("com.android.application")
    }else{
        id("com.android.library")
    }
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = Deploy.compileSdkVersion

    defaultConfig {
        minSdk = Deploy.minSdkVersion
        targetSdk = Deploy.targetSdkVersion

        testInstrumentationRunner = Deploy.androidJUnitRunner
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    val isBuildModule:Boolean by project
}