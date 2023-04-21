package com.kev.pexelswallpapers.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.kev.pexelswallpapers.model.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    pager: Pager<Int, Photo>
) : ViewModel() {

    val imagesPagingFlow = pager
        .flow
        .cachedIn(viewModelScope)
}
