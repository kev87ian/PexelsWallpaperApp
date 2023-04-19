package com.kev.pexelswallpapers.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kev.pexelswallpapers.domain.model.Src

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey
    val id: Int,
    val src: Src,
    val url: String
)
