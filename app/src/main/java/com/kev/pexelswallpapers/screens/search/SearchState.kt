package com.kev.pexelswallpapers.screens.search

import com.kev.pexelswallpapers.model.photo_search.Photo

data class SearchState(
    val isLoading: Boolean = false,
    val data: List<Photo> = emptyList(),
    val error:String = ""
)