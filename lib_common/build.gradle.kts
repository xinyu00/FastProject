import com.fast.plugin.Deploy
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.fast.plugin")
}

android {
    compileSdk = Deploy.compileSdkVersion

    defaultConfig {
        minSdk = Deploy.minSdkVersion
        targetSdk = Deploy.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // 自动加载依赖
    Deploy.commonDep.forEach { value ->
        api(value)
    }

//    implementation(group = "com.tencent.matrix",name = "matrix-android-lib",version = Deploy.matrixVersion)
//    implementation( group= "com.tencent.matrix", name= "matrix-android-lib", version= Deploy.matrixVersion)
//    implementation (group= "com.tencent.matrix", name= "matrix-android-commons", version= Deploy.matrixVersion)
//    implementation (group= "com.tencent.matrix", name= "matrix-trace-canary", version= Deploy.matrixVersion)
//    implementation (group= "com.tencent.matrix", name= "matrix-resource-canary-android", version= Deploy.matrixVersion)
//    implementation (group= "com.tencent.matrix", name= "matrix-resource-canary-common", version= Deploy.matrixVersion)
//    implementation (group= "com.tencent.matrix", name= "matrix-io-canary", version= Deploy.matrixVersion)
//    implementation( group= "com.tencent.matrix", name= "matrix-sqlite-lint-android-sdk", version= Deploy.matrixVersion)
//    implementation (group= "com.tencent.matrix", name= "matrix-battery-canary", version= Deploy.matrixVersion)
//    implementation (group= "com.tencent.matrix", name= "matrix-hooks", version= Deploy.matrixVersion)
}