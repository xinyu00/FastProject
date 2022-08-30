

plugins{
    id("com.fast.plugin")
    id("com.android.application") version("7.1.1")  apply(false)
    id("com.android.library") version("7.1.1")  apply(false)
    id("org.jetbrains.kotlin.android") version("1.6.10") apply(false)
}
// 全局项目管理
subprojects {
    // 默认应用所有子项目中
    apply(plugin="com.fast.plugin")
    apply(plugin="org.jetbrains.kotlin.android")
    // 区分子项目添加应用
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
