package com.my.skyalbum.model.repository

import com.my.skyalbum.model.api.ApiService


class HomeRepository constructor(private val apiService: ApiService) {
    suspend fun getUsers() = apiService.getUsers()

    suspend fun getAlbums() = apiService.getAlbums()

    suspend fun getPhoto(id: Long) = apiService.getPhoto(id)

    suspend fun getPhotos() = apiService.getPhotos()
}