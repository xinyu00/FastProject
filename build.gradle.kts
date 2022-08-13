buildscript{
    dependencies{
        classpath(Deploy.matrix)
    }
}



plugins{
    id(Deploy.pluginAndroidId) version(Deploy.gradleVersion)  apply(false)
    id(Deploy.pluginAndroidLibrary) version(Deploy.gradleVersion)  apply(false)
    id(Deploy.pluginKotlin) version(Deploy.kotlinVersion) apply(false)
}



tasks{
    val clean by registering(Delete::class){
        delete(rootProject.buildDir)
    }
}