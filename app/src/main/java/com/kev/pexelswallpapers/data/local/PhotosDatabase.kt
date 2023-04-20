package com.kev.pexelswallpapers.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kev.pexelswallpapers.model.Photo
import com.kev.pexelswallpapers.model.PhotosRemoteKey

@Database(
    entities = [Photo::class, PhotosRemoteKey::class],
    version = 1,
    exportSchema = false
)
abstract class PhotosDatabase : RoomDatabase(){

    abstract fun remoteKeysDao(): PhotosRemoteKeyDao
    abstract fun imagesDao() : PhotosDao
}