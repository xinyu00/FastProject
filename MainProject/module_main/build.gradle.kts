import com.fast.plugin.Deploy

plugins {

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

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    api(project(mapOf("path" to ":lib_common")))
}


tasks{
    val copyJar by registering(Copy::class){
        from("build/libs/")
        into("library/libs/")
        include("sdk.jar")
        println("====> sdk.jar copy complete")
    }
}
