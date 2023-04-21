package com.kev.pexelswallpapers.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.kev.pexelswallpapers.data.local.PhotosDatabase
import com.kev.pexelswallpapers.data.remote.PhotosApiService
import com.kev.pexelswallpapers.model.Photo
import com.kev.pexelswallpapers.model.PhotosRemoteKey
import javax.inject.Inject
import kotlinx.coroutines.delay
import okio.IOException
import retrofit2.HttpException

@ExperimentalPagingApi
class PhotosRemoteMediator @Inject constructor(
    private val apiService: PhotosApiService,
    private val photosDatabase: PhotosDatabase
) : RemoteMediator<Int, Photo>() {

    private val imagesDao = photosDatabase.imagesDao()
    private val remoteKeysDao = photosDatabase.remoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Photo>): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKey = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKey?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKey != null
                        )
                    nextPage
                }
            }

            delay(3000L)
            val response = apiService.getImages(page = currentPage)
            val endOfPaginationReached = response.photos.isEmpty()
            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            photosDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    imagesDao.deleteAllImages()
                    remoteKeysDao.deleteAllRemoteKeys()
                }

                val keys = response.photos.map { photo ->
                    PhotosRemoteKey(
                        id = photo.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                remoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                imagesDao.saveAll(images = response.photos)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            e.printStackTrace()
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            e.printStackTrace()
            return MediatorResult.Error(e)
        } catch (e: Exception) {
            e.printStackTrace()
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Photo>
    ): PhotosRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeysDao.getRemoteKey(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Photo>
    ): PhotosRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
                remoteKeysDao.getRemoteKey(id = it.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Photo>
    ): PhotosRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                remoteKeysDao.getRemoteKey(id = it.id)
            }
    }
}
