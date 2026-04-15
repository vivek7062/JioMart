package com.example.jiomartclone.presentation.screen.hometab.groceries

sealed class GroceriesIntent {
    object LoadData : GroceriesIntent()
    object Retry : GroceriesIntent()
}