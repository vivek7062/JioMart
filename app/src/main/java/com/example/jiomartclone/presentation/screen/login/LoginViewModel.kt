package com.example.jiomartclone.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jiomartclone.core.common.UiState
import com.example.jiomartclone.data.local.auth.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val dao: UserDao) : ViewModel(){
    private val _uiState = MutableStateFlow(LoginUiState())
    val state : StateFlow<LoginUiState>
        get() = _uiState

    fun onEmailChange(email: String){
        _uiState.value = _uiState.value.copy(email=email)
    }

    fun onPasswordChange(password: String){
        _uiState.value = _uiState.value.copy(password=password)
    }

    fun login(){
        viewModelScope.launch {
            val state = _uiState.value
            _uiState.value = _uiState.value.copy(loginUiState = UiState(isLoading = true))
            if(state.email.isBlank() || state.password.isBlank()){
                _uiState.value = _uiState.value.copy(loginUiState = UiState(error = "Please enter email and password", isLoading = false))
                return@launch
            }
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches()){
                _uiState.value = _uiState.value.copy(loginUiState = UiState(error = "Please enter valid email address", isLoading = false))
                return@launch
            }

            val user = dao.getUser(state.email)

            if(user ==null || !user.isVerify){
                _uiState.value = _uiState.value.copy(loginUiState = UiState(error = "User Not Found", isLoading = false))
            } else if(user.password == state.password){
                _uiState.value = _uiState.value.copy(loginUiState = UiState(data = Unit, isLoading = false))
            } else{
                _uiState.value = _uiState.value.copy(loginUiState = UiState(error = "Incorrect Login Details", isLoading = false))
            }

        }
    }
}