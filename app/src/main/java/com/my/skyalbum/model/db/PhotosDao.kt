package com.my.skyalbum.model.db

import androidx.room.*

@Entity
data class Photo(
    @PrimaryKey val id: Long,

    /**
     * 所屬相簿id
     */
    @ColumnInfo(name = "albumId")
    val albumID: Long?,
    /**
     * 相片標題
     */
    @ColumnInfo(name = "title")var title: String?,
    /**
     * 圖url
     */
    @ColumnInfo(name = "url")val url: String?,
    /**
     * 縮圖url
     */
    @ColumnInfo(name = "thumbnailUrl")var thumbnailUrl: String?
)

@Dao
interface PhotosDao {
    @Query("SELECT * FROM photo")
    fun getAll(): List<Photo>

    @Query("SELECT * FROM photo WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<Photo>

    @Query("SELECT * FROM photo WHERE albumID LIKE (:albumID)")
    fun getPhotosByAlbumID(albumID: Long): List<Photo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg photo: Photo)

    @Delete
    suspend fun delete(photo: Photo)
}
