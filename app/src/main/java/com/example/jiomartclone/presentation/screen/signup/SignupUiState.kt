package com.example.jiomartclone.presentation.screen.signup

data class SignupUiState(
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val error: String? = null,
    val success: Boolean = false,
    val isLoading: Boolean = false
)