package com.kev.pexelswallpapers.data.local.paging

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "pexels_remote_keys")
data class PexelsRemoteKeys(
    @PrimaryKey
    val id: Int,
    val prevPage: Int,
    val nextPage: Int
)
