package com.kev.pexelswallpapers.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.kev.pexelswallpapers.model.Photo
import com.kev.pexelswallpapers.repository.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    repository: PhotosRepository
): ViewModel(){

    val getAllImages = repository.getAllImages()
}
