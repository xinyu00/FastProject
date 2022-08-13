pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven { setUrl("https://maven.aliyun.com/repository/public/") }
        maven { setUrl("https://jitpack.io") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://maven.aliyun.com/repository/public/") }
        maven { setUrl("https://jitpack.io") }
//        maven { url = uri("$rootDir/repo") }
    }
}
rootProject.name = "FastProject"
include(":app")
include(":lib_common")
