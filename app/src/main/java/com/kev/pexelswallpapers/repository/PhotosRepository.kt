package com.kev.pexelswallpapers.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kev.pexelswallpapers.data.local.PhotosDatabase
import com.kev.pexelswallpapers.data.paging.PhotosRemoteMediator
import com.kev.pexelswallpapers.data.remote.PhotosApiService
import com.kev.pexelswallpapers.model.photo_details.PhotoDetailsResponse
import com.kev.pexelswallpapers.model.photos_list.Photo
import com.kev.pexelswallpapers.util.Resource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class PhotosRepository @Inject constructor(
    private val apiService: PhotosApiService,
    private val photosDatabase: PhotosDatabase
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getAllImages(): Flow<PagingData<Photo>> {
        val pagingSource = { photosDatabase.imagesDao().getAllImages() }
        return Pager(
            config = PagingConfig(pageSize = 30),
            remoteMediator = PhotosRemoteMediator(
                apiService = apiService,
                photosDatabase = photosDatabase
            ),
            pagingSourceFactory = pagingSource

        ).flow
    }

    suspend fun getPhotoDetails(photoId: Int): Flow<Resource<PhotoDetailsResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.getImageDetails(photoId)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            when (e) {
                is IOException -> emit(
                    Resource.Error("Ensure you have an active internet connection")
                )
                is HttpException -> emit(
                    Resource.Error("We're unable to reach the servers. Please retry")
                )
                else -> emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
            }
        }
    }
}
