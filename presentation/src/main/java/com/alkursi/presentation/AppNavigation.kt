package com.alkursi.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alkursi.presentation.common.AppRoutes
import com.alkursi.presentation.feature.details.NewsDetailsScreen
import com.alkursi.presentation.feature.home.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.Home.routeName
    ) {
        composable(route = AppRoutes.Home.routeName) {
            HomeScreen(navController)
        }

        composable(
            route = AppRoutes.NewsDetails.routeName,
            arguments = listOf(navArgument("articleId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val articleId = backStackEntry.arguments?.getInt("articleId") ?: -1
            NewsDetailsScreen(
                navController = navController,
                articleId = articleId
            )
        }

        composable(route = AppRoutes.Settings.routeName) {}
    }
}