package com.my.skyalbum.view.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.room.withTransaction
import com.my.skyalbum.model.api.ApiResult
import com.my.skyalbum.model.api.vo.AlbumItem
import com.my.skyalbum.model.api.vo.PhotoItem
import com.my.skyalbum.model.api.vo.UserItem
import com.my.skyalbum.model.db.Album
import com.my.skyalbum.model.db.SkyAlbumDatabase
import com.my.skyalbum.model.manager.RepositoryManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber

class HomeViewModel @ViewModelInject constructor(
    private val repositoryManager: RepositoryManager,
    private val skyAlbumDatabase: SkyAlbumDatabase
) : ViewModel() {
    private var _selectedUserId = MutableLiveData<Long>(1)
    val selectedUserId: LiveData<Long> = _selectedUserId

    private val _getUsersResult = MutableLiveData<ApiResult<List<UserItem>>>()
    val getUsersResult: LiveData<ApiResult<List<UserItem>>> = _getUsersResult

    private val _getAlbumsResult = MutableLiveData<ApiResult<List<AlbumItem>>>()
    val getAlbumsResult: LiveData<ApiResult<List<AlbumItem>>> = _getAlbumsResult

    private val _readAlbumsResult = MutableLiveData<List<AlbumItem>>()
    val readAlbumsResult: LiveData<List<AlbumItem>> = _readAlbumsResult

    private val _readAlbumAndPhotosResult = MutableLiveData<List<AlbumItem>>()
    val readAlbumAndPhotosResult: LiveData<List<AlbumItem>> = _readAlbumAndPhotosResult

    fun setSelectedUser(id: Long){
        _selectedUserId.value = id
    }

    fun getUsers() {
        viewModelScope.launch {
            flow {
                val resp = repositoryManager.homeRepository.getUsers()
                if (!resp.isSuccessful) throw HttpException(resp)
                emit(ApiResult.success(resp.body()))
            }
                .flowOn(Dispatchers.IO)
                .catch { e -> emit(ApiResult.error(e)) }
                .collect { _getUsersResult.postValue(it) }
        }
    }

    fun getAlbums() {
        viewModelScope.launch {
            flow {
                val resp = repositoryManager.homeRepository.getAlbums()
                if (!resp.isSuccessful) throw HttpException(resp)
                emit(ApiResult.success(resp.body()))
            }
                .flowOn(Dispatchers.IO)
                .catch { e -> emit(ApiResult.error(e)) }
                .collect { _getAlbumsResult.postValue(it) }
        }
    }

    fun insertAlbums(albums: List<AlbumItem>){
        viewModelScope.launch {
            val albumsArr = albums.map { Album(it.id, it.userId, it.title) }.toTypedArray()
            skyAlbumDatabase.withTransaction {
                skyAlbumDatabase.albumsDao().insertAll(*albumsArr)
            }
        }
    }

    fun readAlbumByUserId(userId: Long) {
        viewModelScope.launch {
            flow{
                val list = skyAlbumDatabase.albumsDao().getAlbumsByUserID(userId).map { AlbumItem(it.userID, it.id, it.title) }
                emit(
                    list
                )
            }.flowOn(Dispatchers.IO)
                .collect {
                    _readAlbumsResult.value = it
                }
        }
    }

    fun readAlbumAndPhotosByAlbum() {
        viewModelScope.launch {
            flow{
                val list = skyAlbumDatabase.albumsDao().getAlbumAndPhotos()
                    .map { AlbumItem(it.album.userID, it.album.id, it.album.title,
                        if(it.photo.isNotEmpty()) it.photo.get(0).thumbnailUrl else null) }
                    .filter { it.userId == selectedUserId.value }
                emit(
                    list
                )
            }.flowOn(Dispatchers.IO)
                .collect {
                    _readAlbumAndPhotosResult.value = it
                }
        }
    }

}