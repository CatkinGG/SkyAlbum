package com.my.skyalbum.model.api

import com.my.skyalbum.model.api.vo.AlbumItem
import com.my.skyalbum.model.api.vo.PhotoItem
import com.my.skyalbum.model.api.vo.UserItem
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("/users")
    suspend fun getUsers(
    ): Response<List<UserItem>>

    @GET("/albums")
    suspend fun getAlbums(
    ): Response<List<AlbumItem>>

    @GET("/photos/{id}")
    suspend fun getPhoto(
        @Path("id") id: Long
    ): Response<PhotoItem>

    @GET("/photos")
    suspend fun getPhotos(
    ): Response<List<PhotoItem>>
}