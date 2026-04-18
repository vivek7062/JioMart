package com.example.jiomartclone.presentation.screen.categoriestab

import CategoriesResponse
import Category
import com.example.jiomartclone.core.common.UiState

data class CategoriesUiState(
    val categoriesUiState: UiState<CategoriesResponse> = UiState(),
    var selectedMenu: String? = null
)
