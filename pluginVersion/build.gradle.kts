plugins {
    id("java-gradle-plugin")
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
}
buildscript{            // 构建
    repositories {      // 仓库
        gradlePluginPortal()
        google()
        mavenCentral()
        maven { setUrl("https://maven.aliyun.com/repository/public/") }
        maven { setUrl("https://jitpack.io") }
    }
    dependencies{       //依赖
        classpath(kotlin("gradle-plugin","1.6.10"))
    }
}
dependencies {
    implementation(gradleKotlinDsl())
    implementation(kotlin("stdlib","1.6.10"))
    implementation(kotlin("gradle-plugin","1.6.10"))
}

repositories{
    gradlePluginPortal()
}

gradlePlugin {
    plugins {
        create("version") {
            // 在 app 模块需要通过 id 引用这个插件
            id = "com.fast.plugin"
            // 实现这个插件的类的路径
            implementationClass = "com.fast.plugin.VersionPlugin"
        }
    }
}
