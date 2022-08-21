import com.fast.plugin.Deploy
import groovy.lang.Closure


apply(from="config.gradle.kts")

plugins{
    id("com.fast.plugin")
    id("com.android.application") version("7.1.1")  apply(false)
    id("com.android.library") version("7.1.1")  apply(false)
    id("org.jetbrains.kotlin.android") version("1.6.10") apply(false)
}

subprojects {
    apply(plugin="com.fast.plugin")
    apply(plugin="org.jetbrains.kotlin.android")
    if(name == "app"){
        apply(plugin="com.android.application")
    }else{
        apply(plugin="com.android.library")
    }
}
//
//buildscript{
//    dependencies{
//        classpath(Deploy.matrix)
//    }
//}

tasks{
    val clean by registering(Delete::class){
        delete(rootProject.buildDir)
    }
}


