package com.example.jiomartclone.core.base

import retrofit2.HttpException
import com.example.jiomartclone.core.common.Resource
import com.example.jiomartclone.core.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import javax.inject.Inject

abstract class BaseRepository (private val dispatcherProvider: DispatcherProvider) {
    fun <T, R> safeApiCall(apiCall: suspend () -> T, mapper: (T) -> R): Flow<Resource<R>> = flow {
        emit(Resource.Loading())
        val result = mapper(apiCall())
        emit(Resource.Success(result))

    }.catch { e ->
        when (e) {
            is IOException -> emit(Resource.Error("No Internet", true))
            is HttpException -> emit(Resource.Error("Server Error ${e.code()}"))
            else -> emit(Resource.Error(e.message ?: "Something went wrong"))
        }
    }.flowOn(dispatcherProvider.io)
}