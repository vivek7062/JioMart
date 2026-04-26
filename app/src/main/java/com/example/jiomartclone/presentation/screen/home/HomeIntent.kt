package com.example.jiomartclone.presentation.screen.home

sealed class HomeIntent {
    object LoadData : HomeIntent()
    object Retry : HomeIntent()
}