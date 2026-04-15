package com.example.jiomartclone.presentation.screen.hometab.lowprice

import com.example.jiomartclone.core.common.UiState
import com.example.jiomartclone.domain.model.HomeHeaderCategory
import com.example.jiomartclone.domain.model.HomeLowPriceBanner
import com.example.jiomartclone.domain.model.lowprice.LowPriceCategoryWithProduct

data class LowPriceUiState (
    val categoryState: UiState<LowPriceCategoryWithProduct> = UiState(),
    val homeUiState: UiState<List<HomeLowPriceBanner>> = UiState(),
    val isLoading : Boolean = false,
    val error : String? = null
)