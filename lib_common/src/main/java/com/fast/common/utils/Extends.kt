package com.fast.common.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

sealed class RouteScreen(val route: String) {
    object ScreenForSplash: RouteScreen(route = "splashScreen")
    object ScreenForHome: RouteScreen(route = "homeScreen")
    class ScreenForLine(dataSize: Int? = null): RouteScreen(route = generateRoute(dataSize = dataSize)){
        companion object{
            fun generateRoute(dataSize: Int?): String = "lineScreen/${dataSize ?: "{dataSize}"}"

            fun getArgument(entry: NavBackStackEntry): Int = entry.arguments?.getInt("dataSize") ?: 10
        }
    }
}

fun NavController.navigateWithBack(routeScreen: RouteScreen) {
    popBackStack()
    navigate(routeScreen.route)
}

fun NavController.navigateSingleTop(routeScreen: RouteScreen) {
    popBackStack()
    navigate(routeScreen.route) {
        launchSingleTop = true
    }
}

val LocalInputMethodManager = staticCompositionLocalOf<InputMethodManager> {
    error("CompositionLocal InputMethodManager not present")
}

@Composable
fun ProvideInputMethodManager(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val inputMethodManager = remember {
        context.getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
    }
    CompositionLocalProvider(LocalInputMethodManager provides inputMethodManager) {
        content()
    }
}