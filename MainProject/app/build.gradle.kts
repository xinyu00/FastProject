import com.fast.plugin.Deploy

plugins {

//    id(Deploy.pluginMatrix)
}

android {
    compileSdk = Deploy.compileSdkVersion

    defaultConfig {
        applicationId = Deploy.applicationId
        minSdk = Deploy.minSdkVersion
        targetSdk = Deploy.targetSdkVersion
        versionCode = Deploy.versionCode
        versionName = Deploy.versionName

        testInstrumentationRunner = Deploy.androidJUnitRunner
    }

    signingConfigs {
        register("release") {
            keyAlias = "key0"
            keyPassword = "123456"
            storeFile = file("../test.jks")
            storePassword = "123456"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    //输出文件
    android.applicationVariants.all {
        //编译类型
        val buildType = this.buildType.name
        outputs.all {
            //输出APK
            if (this is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
                if (buildType == "debug") {
                    this.outputFileName =
                        "KOTLIN_DSL_V${defaultConfig.versionName}_${buildType}_${Deploy.getSystemTime()}.apk"
                } else if (buildType == "release") {
                    this.outputFileName =
                        "KOTLIN_DSL_V${defaultConfig.versionName}_${buildType}_${Deploy.getSystemTime()}.apk"
                }
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    //部署资源文件
    fun listSubFile(): ArrayList<String> {
        //新资源目录
        val resFolder = "src/main/res/layouts"
        //新资源目录下的文件夹
        val files = file(resFolder).listFiles()
        val folders = ArrayList<String>()
        //遍历路径
        files?.let {
            it.forEach { file ->
                folders.add(file.absolutePath)
            }
        }
        //资源整合
        folders.add(file(resFolder).parentFile.absolutePath)
        return folders
    }

    //资源重定向
    sourceSets {
        getByName("main") {
            res.srcDir(listSubFile())
        }
    }

//    matrix{
//        trace{
//            isEnable  =true
//            baseMethodMapFile = "${project.buildDir}/matrix_output/Debug.methodmap"
//            blackListFile = "${project.projectDir}/matrixTrace/blackMethodList.txt"
//        }
//    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
    }



}

dependencies {
    implementation(project(mapOf("path" to ":module_main")))
}