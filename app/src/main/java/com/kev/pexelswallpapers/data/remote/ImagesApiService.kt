package com.kev.pexelswallpapers.data.remote

import com.kev.pexelswallpapers.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ImagesApiService {

    @Headers("Authorization: ${BuildConfig.API_KEY}")
    @GET("curated")
    suspend fun getImages(
        @Query("page") page:Int,
        @Query("per_page") perPage : Int = 30
    ) : PhotosResponse

    @Headers("AuthorizationL ${BuildConfig.API_KEY}")
    @GET("search")
    suspend fun searchImages(
        @Query("page") page: Int,


    )

}