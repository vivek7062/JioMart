package com.example.jiomartclone.presentation.screen.offertab

sealed class OffersTabIntent {
    object LoadData : OffersTabIntent()
    object Retry : OffersTabIntent()
}