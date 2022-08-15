package com.fast.app.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.fast.app.ui.screen.HomeScreen
import com.fast.app.ui.screen.LineScreen
import com.fast.app.ui.screen.SplashScreen
import com.fast.common.ui.theme.MyAppTheme
import com.fast.common.utils.RouteScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                NavigationRouterConfig(this)
            }
        }
    }

}
@Composable
fun NavigationRouterConfig(activity: ComponentActivity) {//导航路由配置
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = RouteScreen.ScreenForSplash.route) {
        composable(RouteScreen.ScreenForSplash.route) {
            SplashScreen(navController = navController)
        }
        composable(RouteScreen.ScreenForHome.route) {
            HomeScreen(activity = activity, navController = navController)
        }
        composable(RouteScreen.ScreenForLine().route, arguments = listOf(navArgument("dataSize") {
            type = NavType.IntType
            defaultValue = 5
        })) {
            LineScreen(navController = navController, dataSize = RouteScreen.ScreenForLine.getArgument(it))
        }
    }
}