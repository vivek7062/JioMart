package com.example.jiomartclone.presentation.screen.hometab.electronics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jiomartclone.core.common.Resource
import com.example.jiomartclone.domain.usecase.hometab.electronics.GetElectronicsDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ElectronicsViewModel @Inject constructor(private val electronicsDataUseCase: GetElectronicsDataUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow(ElectronicsUiState())
    val state: StateFlow<ElectronicsUiState> = _state

    fun onIntent(intent: ElectronicsIntent) {
        when (intent) {
            is ElectronicsIntent.LoadData -> {
                if (_state.value.electronicsUiState.data == null) {
                    getElectronics()
                }
            }
            is ElectronicsIntent.Retry -> {
                getElectronics()
            }
        }
    }

    fun getElectronics() {
        viewModelScope.launch {
            electronicsDataUseCase().collectLatest { response ->
                _state.update { current ->
                    when (response) {
                        is Resource.Loading -> {
                            current.copy(
                                electronicsUiState = current.electronicsUiState.copy(
                                    isLoading = true, error = null
                                )
                            )
                        }

                        is Resource.Error -> {
                            current.copy(
                                electronicsUiState = current.electronicsUiState.copy(
                                    isLoading = false, error = response.message
                                )
                            )
                        }

                        is Resource.Success -> {
                            current.copy(
                                electronicsUiState = current.electronicsUiState.copy(
                                    isLoading = false, error = null, data = response.data
                                )
                            )
                        }
                    }

                }
            }
        }
    }
}