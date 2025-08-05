package com.alkursi.presentation.common

sealed class AppRoutes(val routeName: String) {

    data object Home : AppRoutes(routeName = "home_screen")

    data object Settings : AppRoutes(routeName = "settings_screen")

    data object NewsDetails : AppRoutes("news_details/{articleId}") {
        fun createRoute(articleId: Int) = "news_details/$articleId"
    }
}