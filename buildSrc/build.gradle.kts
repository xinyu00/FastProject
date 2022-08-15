apply{
    plugin("kotlin")
}

buildscript{            // 构建
    repositories {      // 仓库
        gradlePluginPortal()
    }
    dependencies{       //依赖
        classpath(kotlin("gradle-plugin","1.7.0"))
    }
}
dependencies {
    implementation(gradleKotlinDsl())
    implementation(kotlin("stdlib","1.7.0"))
}

repositories{
    gradlePluginPortal()
}
