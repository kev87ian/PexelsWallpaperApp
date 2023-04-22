package com.kev.pexelswallpapers.screens.home

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.kev.pexelswallpapers.navigation.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController

) {
    val viewModel = hiltViewModel<PhotosViewModel>()
    val photos = viewModel.getCuratedImages().collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            HomeTopBar(onSearchClick = {
                navController.navigate(Screen.Search.route)
            })
        },
        content = {
            HomeScreenListContent(items = photos, navController)
        }
    )
}
