package com.example.jiomartclone.presentation.screen.hometab.electronics

sealed class ElectronicsIntent {
    object LoadData : ElectronicsIntent()
    object Retry : ElectronicsIntent()
}