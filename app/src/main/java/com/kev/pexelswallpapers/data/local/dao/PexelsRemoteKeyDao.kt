package com.kev.pexelswallpapers.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kev.pexelswallpapers.data.local.paging.PexelsRemoteKeys

@Dao
interface PexelsRemoteKeyDao {
    @Query("SELECT * FROM pexels_remote_keys WHERE id=:id")
    suspend fun getRemoteKeys(id: Int): PexelsRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<PexelsRemoteKeys>)

    @Query("DELETE FROM pexels_remote_keys")
    suspend fun deleteAllRemoteKeys()
}
