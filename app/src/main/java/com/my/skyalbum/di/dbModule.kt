package com.my.skyalbum.di

import android.content.Context
import androidx.room.Room
import com.my.skyalbum.model.db.SkyAlbumDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object dbModule {

    @Singleton
    @Provides
    fun providePhotosDatabase(@ApplicationContext context: Context): SkyAlbumDatabase {
        val db = Room.databaseBuilder(
            context,
            SkyAlbumDatabase::class.java, "sky_album.db"
        ).build()
        return db
    }
}
