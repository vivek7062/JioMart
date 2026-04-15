package com.example.jiomartclone.presentation.screen.categoriestab

sealed class CategoriesIntent {
    object LoadData : CategoriesIntent()
    object Retry : CategoriesIntent()
}