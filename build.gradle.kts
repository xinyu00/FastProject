import com.fast.plugin.Deploy




plugins{
    id("com.fast.plugin")
    id("com.android.application") version("7.1.1")  apply(false)
    id("com.android.library") version("7.1.1")  apply(false)
    id("org.jetbrains.kotlin.android") version("1.6.10") apply(false)
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