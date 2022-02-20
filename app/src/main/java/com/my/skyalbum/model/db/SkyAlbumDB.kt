package com.my.skyalbum.model.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Photo::class, Album::class], version = 1, exportSchema = false)
abstract class SkyAlbumDatabase : RoomDatabase() {
    companion object {}

    abstract fun photoDao(): PhotosDao
    abstract fun albumsDao(): AlbumsDao
}