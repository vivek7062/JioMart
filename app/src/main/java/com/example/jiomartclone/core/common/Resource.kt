package com.example.jiomartclone.core.common

sealed class Resource<T> {
    class Loading<T> : Resource<T>()
    data class Success<T>(val data : T) : Resource<T>()
    data class Error<T>(val message : String, val isNetworkError: Boolean = false) : Resource<T>()

}