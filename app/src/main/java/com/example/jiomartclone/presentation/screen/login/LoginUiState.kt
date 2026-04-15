package com.example.jiomartclone.presentation.screen.login

import com.example.jiomartclone.core.common.UiState

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val loginUiState: UiState<Unit> = UiState()
)