package com.example.jiomartclone.presentation.screen.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jiomartclone.data.local.auth.UserDao
import com.example.jiomartclone.data.local.auth.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.security.SecureRandom
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val userDao: UserDao) : ViewModel() {
    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState>
        get() = _uiState

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email, error = null)
    }

    fun onNameChange(name: String) {
        _uiState.value = _uiState.value.copy(name = name, error = null)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password, error = null)
    }

    fun resetSuccess() {
        _uiState.update {
            it.copy(success = false)
        }
    }

    fun getOtp() =  (1000..9999).random()

    suspend fun updateOtp(email: String){
        userDao.updateOTP(email, getOtp())
    }

    fun signUp() {
        viewModelScope.launch {
            val state = _uiState.value
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            if (state.email.isBlank() || state.name.isBlank() || state.password.isBlank()) {
                _uiState.value =
                    _uiState.value.copy(error = "All fields are required", isLoading = false)
                return@launch
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
                _uiState.value =
                    _uiState.value.copy(error = "All fields are required", isLoading = false)
                return@launch
            }
            val existingUser = userDao.getUser(state.email)
            if(existingUser!=null && !existingUser.isVerify){
                updateOtp(state.email)
                _uiState.value =
                    _uiState.value.copy(success = true, error = null, isLoading = false)
            }
            else if (existingUser != null) {
                _uiState.value =
                    _uiState.value.copy(error = "Email already exists", isLoading = false)
            }
            else {

                userDao.insertUser(
                    UserEntity(
                        0,
                        state.name,
                        state.email,
                        state.password,
                        getOtp(),
                        System.currentTimeMillis(),
                        isVerify = false
                    )
                )
                _uiState.value =
                    _uiState.value.copy(success = true, error = null, isLoading = false)
            }
        }
    }

}