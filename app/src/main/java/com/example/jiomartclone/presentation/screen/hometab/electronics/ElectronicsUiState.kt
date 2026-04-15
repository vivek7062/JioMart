package com.example.jiomartclone.presentation.screen.hometab.electronics

import com.example.jiomartclone.domain.model.electronics.Electronics
import com.example.jiomartclone.core.common.UiState

data class ElectronicsUiState(
    val electronicsUiState: UiState<List<Electronics>> = UiState()
)
