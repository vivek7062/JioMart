package com.example.jiomartclone.presentation.screen.hometab.lowprice

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jiomartclone.core.common.Resource
import com.example.jiomartclone.core.common.UiState
import com.example.jiomartclone.domain.model.lowprice.LowPriceCategoryWithProduct
import com.example.jiomartclone.domain.usecase.hometab.lowprice.LowPriceCategoryProductUseCase
import com.example.jiomartclone.domain.usecase.hometab.lowprice.LowPriceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.Response
import javax.inject.Inject

@HiltViewModel
class LowPriceViewModel @Inject constructor(
    private val lowPriceUseCase: LowPriceUseCase,
    private val categoryProductUseCase: LowPriceCategoryProductUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(LowPriceUiState())
    val state: StateFlow<LowPriceUiState> = _state

    fun onIntent(intent: LowPriceIntent) {
        when (intent) {

            LowPriceIntent.LoadData -> {
                if(_state.value.homeUiState.data == null){
                    getBanner()
                }
                if(_state.value.categoryState.data == null){
                    fetchLowPriceCategories()
                }
            }

            LowPriceIntent.Retry -> {
                retry()
            }
        }
    }

    private fun combineState(lowPriceUiState: LowPriceUiState): LowPriceUiState {
        return lowPriceUiState.copy(
            isLoading = lowPriceUiState.homeUiState.isLoading || lowPriceUiState.categoryState.isLoading,
            error = lowPriceUiState.homeUiState.error ?: lowPriceUiState.categoryState.error
        )
    }

    fun getBanner() {
        viewModelScope.launch {
            lowPriceUseCase().collectLatest { result ->
                _state.update { current ->
                    val updatedState = when (result) {
                        is Resource.Loading -> {
                            current.copy(
                                homeUiState = current.homeUiState.copy(
                                    isLoading = true, error = null
                                )
                            )
                        }

                        is Resource.Success -> {
                            current.copy(
                                homeUiState = current.homeUiState.copy(
                                    isLoading = false, data = result.data, error = null
                                )
                            )
                        }

                        is Resource.Error -> {
                            current.copy(
                                homeUiState = current.homeUiState.copy(
                                    isLoading = false, error = result.message
                                )
                            )
                        }
                    }
                    combineState(updatedState)
                }
            }
        }
    }

    fun fetchLowPriceCategories() {
        viewModelScope.launch {
            categoryProductUseCase().collectLatest { result ->
                _state.update { current ->
                    val updatedState = when (result) {
                        is Resource.Loading -> {
                            current.copy(
                                categoryState = current.categoryState.copy(
                                    isLoading = true, error = null
                                )
                            )
                        }

                        is Resource.Success -> {
                            current.copy(
                                categoryState = current.categoryState.copy(
                                    isLoading = false, error = null, data = result.data
                                )
                            )
                        }

                        is Resource.Error -> {
                            current.copy(
                                categoryState = current.categoryState.copy(
                                    isLoading = false, error = result.message
                                )
                            )
                        }
                    }
                    combineState(updatedState)
                }
            }
        }
    }

    fun retry() {
        if (_state.value.homeUiState.error != null) {
            getBanner()
        }
        if (_state.value.categoryState.error != null) {
            fetchLowPriceCategories()
        }
    }
}