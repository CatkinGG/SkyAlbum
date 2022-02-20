package com.my.skyalbum.view.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.my.skyalbum.model.api.ApiResult
import com.my.skyalbum.model.api.vo.PhotoItem
import com.my.skyalbum.model.manager.RepositoryManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailViewModel @ViewModelInject constructor(
    private val repositoryManager: RepositoryManager,
) : ViewModel() {
    private val _getPhotoResult = MutableLiveData<ApiResult<PhotoItem>>()
    val getPhotoResult: LiveData<ApiResult<PhotoItem>> = _getPhotoResult

    fun getPhoto(id: Long) {
        viewModelScope.launch {
            flow {
                val resp = repositoryManager.homeRepository.getPhoto(id)
                if (!resp.isSuccessful) throw HttpException(resp)
                emit(ApiResult.success(resp.body()))
            }
                .flowOn(Dispatchers.IO)
                .catch { e -> emit(ApiResult.error(e)) }
                .collect { _getPhotoResult.postValue(it) }
        }
    }
}