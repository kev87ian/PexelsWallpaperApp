package com.kev.pexelswallpapers.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesApiService {

    @GET("curated")
    suspend fun getImages(
        @Query("page") page:Int,
        @Query("per_page") perPage : Int = 30
    ) : List<PhotoDto>


}