package com.example.jiomartclone.presentation.screen.auth.otp

import com.example.jiomartclone.core.common.UiState

data class OtpUiState(
    val otp: Int = 0,
    val email: String = "",
    val otpUiState: UiState<Unit> = UiState()
)