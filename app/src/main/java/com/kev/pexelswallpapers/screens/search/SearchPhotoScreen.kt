package com.kev.pexelswallpapers.screens.search

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPhotoScreen(
    navController: NavController,
    searchPhotoViewModel: SearchPhotoViewModel = hiltViewModel()
) {
    val searchQuery by searchPhotoViewModel.searchQuery
    val searchImages = searchPhotoViewModel.searchPhotosStateFlow.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            SearchPhotoWidget(
                query = searchQuery,
                onTextChanged = { searchPhotoViewModel.updateSearchQuery(it)},
                onSearchClicked = {
                                  searchPhotoViewModel.searchPhoto(it)
                },
                onCloseClicked = {navController.popBackStack()})
        }
    ) {

    }
}
