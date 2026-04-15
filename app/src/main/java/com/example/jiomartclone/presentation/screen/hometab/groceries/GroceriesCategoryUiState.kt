package com.example.jiomartclone.presentation.screen.hometab.groceries

import com.example.jiomartclone.core.common.UiState
import com.example.jiomartclone.domain.model.groceries.GroceriesBanner
import com.example.jiomartclone.domain.model.groceries.GroceriesCategory

data class GroceriesCategoryUiState(
   val groceryCategoryUiState : UiState<List<GroceriesCategory>> = UiState(),
   val groceryBannerUiState : UiState<List<GroceriesBanner>> = UiState(),
   val isLoading: Boolean = false,
   val error: String? = null
)
