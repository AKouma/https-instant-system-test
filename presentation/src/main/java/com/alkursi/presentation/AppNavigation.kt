package com.alkursi.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alkursi.presentation.common.AppRoutes
import com.alkursi.presentation.feature.details.NewsDetailsScreen
import com.alkursi.presentation.feature.home.HomeScreen
import com.alkursi.presentation.feature.network.NetworkSnackBar
import com.alkursi.presentation.feature.network.NetworkViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val networkViewModel = koinViewModel<NetworkViewModel>()
    val networkStatus = networkViewModel.networkState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        networkViewModel.initNetworkListener()
    }

    Scaffold(
        snackbarHost = { NetworkSnackBar(networkStatus.value) }
    ) { _ ->
        NavHost(
            navController = navController,
            startDestination = AppRoutes.Home.routeName,
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
}