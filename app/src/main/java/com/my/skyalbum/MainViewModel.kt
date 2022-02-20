package com.my.skyalbum

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import com.my.skyalbum.model.api.ApiResult
import com.my.skyalbum.model.api.vo.PhotoItem
import com.my.skyalbum.model.db.Photo
import com.my.skyalbum.model.db.SkyAlbumDatabase
import com.my.skyalbum.model.manager.RepositoryManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainViewModel @ViewModelInject constructor(
    private val repositoryManager: RepositoryManager,
    private val skyAlbumDatabase: SkyAlbumDatabase
) : ViewModel() {
    private val _getPhotosResult = MutableLiveData<ApiResult<List<PhotoItem>>>()
    val getPhotosResult: LiveData<ApiResult<List<PhotoItem>>> = _getPhotosResult

    private val _readPhotosResult = MutableLiveData<List<PhotoItem>>()
    val readPhotosResult: LiveData<List<PhotoItem>> = _readPhotosResult

    private var _isPhotoDownload = MutableLiveData(false)
    var isPhotoDownload: LiveData<Boolean> = _isPhotoDownload

    fun getPhotos() {
        viewModelScope.launch {
            flow {
                val resp = repositoryManager.homeRepository.getPhotos()
                if (!resp.isSuccessful) throw HttpException(resp)
                emit(ApiResult.success(resp.body()))
            }
                .flowOn(Dispatchers.IO)
                .catch { e -> emit(ApiResult.error(e)) }
                .collect {
                    _isPhotoDownload.postValue(true)
                    _getPhotosResult.postValue(it)
                }
        }
    }

    fun insertPhotos(photos: List<PhotoItem>){
        viewModelScope.launch {
            val photosArr = photos.map { Photo(it.id, it.albumId, it.title, it.url, it.thumbnailUrl) }.toTypedArray()
            skyAlbumDatabase.withTransaction {
                skyAlbumDatabase.photoDao().insertAll(*photosArr)
            }
        }
    }

    fun readAllPhotos() {
        viewModelScope.launch {
            flow{
                val list = skyAlbumDatabase.photoDao().getAll().map{PhotoItem(it.albumID, it.id, it.title, it.url, it.thumbnailUrl)}
                emit(
                    list
                )
            }.flowOn(Dispatchers.IO)
                .collect {
                    _readPhotosResult.value = it
                }
        }
    }

    fun readPhotosByAlbum(albumId: Long) {
        viewModelScope.launch {
            flow{
                val list = skyAlbumDatabase.photoDao().getPhotosByAlbumID(albumId).map{PhotoItem(it.albumID, it.id, it.title, it.url, it.thumbnailUrl)}
                emit(
                    list
                )
            }.flowOn(Dispatchers.IO)
                .collect {
                    _readPhotosResult.value = it
                }
        }
    }



}