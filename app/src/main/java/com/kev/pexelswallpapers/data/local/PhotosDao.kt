package com.kev.pexelswallpapers.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PhotosDao{

    @Upsert
    suspend fun upsertAll(photoEntity: List<PhotoEntity>)

/*    @Query("SELECT * FROM photos")
    fun pagingSource() : PagingSource*/

    @Query("DELETE FROM photos")
    suspend fun clearAll()

}
