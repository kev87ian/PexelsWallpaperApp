package com.kev.pexelswallpapers.data.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kev.pexelswallpapers.model.PhotosRemoteKey

interface PhotosRemoteKeyDao{

    @Query("SELECT * FROM photos_remote_keys_table WHERE id=:id")
    suspend fun getRemoteKey(id:Int): PhotosRemoteKey

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<PhotosRemoteKey>)

    @Query("DELETE FROM photos_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}