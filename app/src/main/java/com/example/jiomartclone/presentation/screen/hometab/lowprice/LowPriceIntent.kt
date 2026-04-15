package com.example.jiomartclone.presentation.screen.hometab.lowprice

sealed class LowPriceIntent {
    object LoadData : LowPriceIntent()
    object Retry : LowPriceIntent()
}