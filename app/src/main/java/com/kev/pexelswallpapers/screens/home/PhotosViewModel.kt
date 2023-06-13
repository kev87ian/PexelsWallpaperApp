package com.kev.pexelswallpapers.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kev.pexelswallpapers.data.local.PhotosDatabase
import com.kev.pexelswallpapers.data.paging.PhotosRemoteMediator
import com.kev.pexelswallpapers.data.remote.PhotosApiService
import com.kev.pexelswallpapers.model.photos_list.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val apiService: PhotosApiService,
    private val database: PhotosDatabase
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    fun getCuratedImages(): Flow<PagingData<Photo>> =
        Pager(
            config = PagingConfig(
                pageSize = 40,
                prefetchDistance = 10,
                initialLoadSize = 25
            ),
            pagingSourceFactory = {
                database.imagesDao().getAllImages()
            },
            remoteMediator = PhotosRemoteMediator(
                apiService = apiService,
                photosDatabase = database
            )
        ).flow.cachedIn(viewModelScope)
}
