package com.kev.pexelswallpapers.data.remote

import com.kev.pexelswallpapers.BuildConfig
import com.kev.pexelswallpapers.model.photo_details.PhotoDetailsResponse
import com.kev.pexelswallpapers.model.photo_search.PhotoSearchResponse
import com.kev.pexelswallpapers.model.photos_list.PhotoResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotosApiService {

    @Headers("Authorization: ${BuildConfig.API_KEY}")
    @GET("curated")
    suspend fun getImages(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 40
    ): PhotoResponse

    @Headers("Authorization: ${BuildConfig.API_KEY}")
    @GET("photos/{id}")
    suspend fun getImageDetails(
        @Path("id") photoId: Int
    ): PhotoDetailsResponse

    @Headers("Authorization: ${BuildConfig.API_KEY}")
    @GET("search")
    suspend fun searchPhotos(
        @Query("query") photoQuery: String,
        @Query("per_page") perPage: Int = 40
    ): PhotoSearchResponse
}
