package com.kev.pexelswallpapers.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kev.pexelswallpapers.data.local.PhotoEntity

@Dao
interface PhotosDao{

    @Upsert
    suspend fun upsertAll(photoEntity: List<PhotoEntity>)

    @Query("SELECT * FROM photos")
    fun pagingSource() : PagingSource<Int, PhotoEntity>

    @Query("DELETE FROM photos")
    suspend fun clearAll()

}
