package com.my.skyalbum.model.db

import androidx.room.*

@Entity
data class Album(
    @PrimaryKey val id: Long,

    /**
     * 所屬用戶ID
     */
    @ColumnInfo(name = "userId")val userID: Long?,
    /**
     * 相簿標題
     */
    @ColumnInfo(name = "title")var title: String?,
)

@Dao
interface AlbumsDao {
    @Query("SELECT * FROM album")
    fun getAll(): List<Album>

    @Query("SELECT * FROM album WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<Album>

    @Query("SELECT * FROM album WHERE userID LIKE (:userID)")
    fun getAlbumsByUserID(userID: Long): List<Album>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg album: Album)

    @Delete
    suspend fun delete(album: Album)

    @Query("SELECT * FROM album")
    fun getAlbumAndPhotos(): List<AlbumWithPhoto>
}

data class AlbumWithPhoto(
    @Embedded
    var album: Album,

    @Relation(parentColumn = "id", entityColumn = "albumId")
    var photo: List<Photo>
)
