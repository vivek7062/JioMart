package com.example.jiomartclone.presentation.screen.home

import com.example.jiomartclone.core.common.UiState
import com.example.jiomartclone.domain.model.Category
import com.example.jiomartclone.domain.model.HomeHeaderCategory

data class HomeUiState (
    val homeUiState: UiState<List<HomeHeaderCategory>> = UiState()
)