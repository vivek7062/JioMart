package com.example.jiomartclone.presentation.screen.categoriestab

import CategoriesResponse
import com.example.jiomartclone.core.common.UiState

data class CategoriesUiState(
    val categoriesUiState: UiState<CategoriesResponse> = UiState()
)
