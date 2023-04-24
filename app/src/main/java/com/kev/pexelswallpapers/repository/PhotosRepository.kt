@file:Suppress("UNREACHABLE_CODE")

package com.kev.pexelswallpapers.repository

import com.kev.pexelswallpapers.data.remote.PhotosApiService
import com.kev.pexelswallpapers.model.photo_details.PhotoDetailsResponse
import com.kev.pexelswallpapers.model.photo_search.PhotoSearchResponse
import com.kev.pexelswallpapers.util.Resource
import javax.inject.Inject
import okio.IOException
import retrofit2.HttpException

class PhotosRepository @Inject constructor(
    private val apiService: PhotosApiService
) {

    suspend fun getPhotoDetails(photoId: Int): Resource<PhotoDetailsResponse> {
        return try {
            val response = apiService.getImageDetails(photoId)
            return Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            when (e) {
                is IOException -> return Resource.Error(
                    "Ensure you have an active internet connection"
                )

                is HttpException -> return Resource.Error(
                    "We're unable to reach servers. Please retry."
                )

                else -> return Resource.Error(
                    e?.localizedMessage ?: "An unknown error occurred. Please retry."
                )
            }
        }
    }

    suspend fun searchPhoto(photoQuery: String): Resource<PhotoSearchResponse> {
        return try {
            val result = apiService.searchPhotos(photoQuery)
            return Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            when (e) {
                is IOException -> return Resource.Error(
                    "Ensure you have an active internet connection"
                )
                is HttpException -> return Resource.Error(
                    "We're unable to reach servers. Please retry."
                )
                else -> return Resource.Error(
                    e.localizedMessage ?: "An unknown error occurred. Please retry."
                )
            }
        }
    }
}
