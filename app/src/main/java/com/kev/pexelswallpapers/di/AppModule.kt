package com.kev.pexelswallpapers.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.kev.pexelswallpapers.data.local.PhotosDatabase
import com.kev.pexelswallpapers.data.paging.PhotosRemoteMediator
import com.kev.pexelswallpapers.data.remote.PhotosApiService
import com.kev.pexelswallpapers.model.Photo
import com.kev.pexelswallpapers.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            /*            .retryOnConnectionFailure(true)*/
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit
        .Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun createsApiService(retrofit: Retrofit): PhotosApiService {
        return retrofit.create(PhotosApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): PhotosDatabase {
        return Room.databaseBuilder(
            context,
            PhotosDatabase::class.java,
            "photos_db"
        ).build()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun providesPhotosPager(database: PhotosDatabase, apiService: PhotosApiService): Pager<Int, Photo> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = PhotosRemoteMediator(apiService, database),
            pagingSourceFactory = { database.imagesDao().getAllImages() }
        )
    }
}
