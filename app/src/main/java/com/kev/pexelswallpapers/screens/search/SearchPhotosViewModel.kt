package com.kev.pexelswallpapers.screens.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.pexelswallpapers.repository.PhotosRepository
import com.kev.pexelswallpapers.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SearchPhotosViewModel @Inject constructor(
    private val repository: PhotosRepository
) : ViewModel() {

    private val _imageList: MutableState<SearchState> = mutableStateOf(SearchState())
    val imageList = _imageList

    fun searchImages(query: String) = viewModelScope.launch {
        imageList.value = SearchState(isLoading = true)
        when (val result = repository.searchPhoto(query)) {
            is Resource.Success -> {
                _imageList.value = SearchState(data = result.data.photos)
            }

            is Resource.Loading -> {
                _imageList.value = SearchState(isLoading = true)
            }

            is Resource.Error -> {
                imageList.value = SearchState(error = result.errorMessage)
            }
        }
    }
}
