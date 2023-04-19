package com.kev.pexelswallpapers.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kev.pexelswallpapers.model.Photo

@Dao
interface PhotosDao {
    @Upsert
    suspend fun saveAll(images: List<Photo>)

    @Query("SELECT * FROM images_table")
    fun pagingSource(): PagingSource<Int, Photo>

    @Query("DELETE FROM images_table")
    suspend fun deleteAllImages()
}
