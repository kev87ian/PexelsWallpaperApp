package com.kev.pexelswallpapers.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.pexelswallpapers.model.photo_search.PhotoSearchResponse
import com.kev.pexelswallpapers.repository.PhotosRepository
import com.kev.pexelswallpapers.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SearchPhotoViewModel @Inject constructor(
    private val repository: PhotosRepository
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery
    private val _searchPhotosStateflow = MutableStateFlow<Resource<PhotoSearchResponse>>(
        Resource.Loading()
    )
    val searchPhotosStateFlow: StateFlow<Resource<PhotoSearchResponse>> = _searchPhotosStateflow


    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchPhoto(photoQuery: String) = viewModelScope.launch {
        val response = repository.searchPhoto(photoQuery = photoQuery)

        _searchPhotosStateflow.value = response
    }
}
