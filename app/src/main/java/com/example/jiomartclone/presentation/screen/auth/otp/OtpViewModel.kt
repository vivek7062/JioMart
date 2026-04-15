package com.example.jiomartclone.presentation.screen.auth.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jiomartclone.core.common.NotificationHelper
import com.example.jiomartclone.core.common.UiState
import com.example.jiomartclone.data.local.auth.UserDao
import com.example.jiomartclone.data.local.datastore.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val userDao: UserDao,
    private val dataStoreManager: DataStoreManager,
    private val notificationHelper: NotificationHelper
) : ViewModel() {

    private val _state = MutableStateFlow(OtpUiState())
    val state: StateFlow<OtpUiState> = _state

    fun onEmailChange(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun onOtpChange(otp: Int) {
        _state.update { it.copy(otp = otp) }
    }

    private fun getOtp() = (1000..9999).random()

    fun resendOtp() {
        viewModelScope.launch {

            val state = _state.value

            _state.update {
                it.copy(otpUiState = UiState(isLoading = true))
            }

            if (state.email.isBlank()) {
                _state.update {
                    it.copy(
                        otpUiState = UiState(
                            error = "Please Enter Email"
                        )
                    )
                }
                return@launch
            }

            val otp = getOtp()
            notificationHelper.showNotification(
                title = "OTP Received",
                message = "Your OTP is $otp"
            )
            userDao.updateOTP(state.email, otp)

            _state.update {
                it.copy(
                    otpUiState = UiState(data = null, isLoading = false)
                )
            }
        }
    }

    fun validateOtp() {
        viewModelScope.launch {

            val state = _state.value

            _state.update {
                it.copy(otpUiState = UiState(isLoading = true))
            }

            if (state.email.isBlank() || state.otp < 1000) {
                _state.update {
                    it.copy(
                        otpUiState = UiState(
                            error = "Please Enter Otp"
                        )
                    )
                }
                return@launch
            }

            val user = userDao.getUser(state.email)

            if (user == null) {
                _state.update {
                    it.copy(
                        otpUiState = UiState(
                            error = "User not found"
                        )
                    )
                }
                return@launch
            }

            if (user.otp == state.otp) {
                userDao.getVerifiedUser(state.email, true)
                dataStoreManager.saveToken(state.email)

                _state.update {
                    it.copy(
                        otpUiState = UiState(
                            data = Unit
                        )
                    )
                }

            } else {
                _state.update {
                    it.copy(
                        otpUiState = UiState(
                            error = "Invalid OTP"
                        )
                    )
                }
            }
        }
    }
}