package com.kev.pexelswallpapers.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kev.pexelswallpapers.data.local.PhotosDatabase
import com.kev.pexelswallpapers.data.paging.PhotosRemoteMediator
import com.kev.pexelswallpapers.data.remote.PhotosApiService
import com.kev.pexelswallpapers.model.Photo
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PhotosRepository @Inject constructor(
    private val apiService: PhotosApiService,
    private val photosDatabase: PhotosDatabase
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getAllImages(): Flow<PagingData<Photo>> {
        val pagingSource = { photosDatabase.imagesDao().pagingSource() }
        return Pager(
            config = PagingConfig(pageSize = 30),
            remoteMediator = PhotosRemoteMediator(
                apiService = apiService,
                photosDatabase = photosDatabase
            ),
            pagingSourceFactory = pagingSource

        ).flow
    }
}
