buildscript{
    dependencies{
//        classpath(Deploy.matrix)
    }
}



plugins{
    id("com.android.application") version("7.1.1")  apply(false)
    id("com.android.library") version("7.1.1")  apply(false)
    id("org.jetbrains.kotlin.android") version("1.7.0") apply(false)
}



tasks{
    val clean by registering(Delete::class){
        delete(rootProject.buildDir)
    }
}