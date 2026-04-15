package com.example.jiomartclone.core.common

data class UiState<T> (
    val isLoading: Boolean = false,
    val data:T?=null,
    val error: String?=null,
    val isNetworkError: Boolean = false
)