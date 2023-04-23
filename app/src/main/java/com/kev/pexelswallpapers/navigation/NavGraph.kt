package com.kev.pexelswallpapers.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kev.pexelswallpapers.screens.details.PhotoDetailsScreen
import com.kev.pexelswallpapers.screens.home.HomeScreen
import com.kev.pexelswallpapers.screens.search.SearchPhotoScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(Screen.Search.route) {
            SearchPhotoScreen(navController = navController)
        }

        composable(
            route = Screen.Details.route + "/{photoId}",
            arguments = listOf(
                navArgument("photoId") {
                    type = NavType.IntType
                    nullable = false
                }
            )

        ) { entry ->
            PhotoDetailsScreen(
                photoId = entry.arguments?.getInt("photoId")!!
            )
        }
    }
}
