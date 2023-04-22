package com.kev.pexelswallpapers.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object Search : Screen("search_screen")
    object Details : Screen("details_screen")

    fun wihArgs(vararg args: Int): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
