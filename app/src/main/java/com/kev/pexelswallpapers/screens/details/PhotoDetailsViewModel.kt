package com.kev.pexelswallpapers.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.pexelswallpapers.model.photo_details.PhotoDetailsResponse
import com.kev.pexelswallpapers.repository.PhotosRepository
import com.kev.pexelswallpapers.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PhotoDetailsViewModel @Inject constructor(
    private val repository: PhotosRepository
) : ViewModel() {

    private val _photoDetailsStateFlow = MutableStateFlow<Resource<PhotoDetailsResponse>>(
        Resource.Loading()
    )
    val photoDetailsStateFlow: StateFlow<Resource<PhotoDetailsResponse>> = _photoDetailsStateFlow
    fun getPhotoDetails(photoId: Int) = viewModelScope.launch {
        val response = repository.getPhotoDetails(photoId)
        _photoDetailsStateFlow.value = response
    }
}
