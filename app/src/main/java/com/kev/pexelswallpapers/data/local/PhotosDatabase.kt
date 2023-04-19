package com.kev.pexelswallpapers.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kev.pexelswallpapers.data.local.dao.PhotosDao

@Database(
    entities = [PhotoEntity::class],
    version = 1)
@TypeConverters(Converters::class)
abstract class PhotosDatabase : RoomDatabase() {

    abstract val photosDao : PhotosDao
}