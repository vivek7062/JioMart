package com.example.jiomartclone.presentation.screen.hometab.groceries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jiomartclone.core.common.Resource
import com.example.jiomartclone.domain.usecase.hometab.groceries.GetGroceriesBannerUseCase
import com.example.jiomartclone.domain.usecase.hometab.groceries.GetGroceriesCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroceriesCategoryViewModel @Inject constructor(
    private val groceriesCategoryUseCase: GetGroceriesCategoryUseCase,
    private val groceriesBannerUseCase: GetGroceriesBannerUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(GroceriesCategoryUiState())
    val state: StateFlow<GroceriesCategoryUiState> = _state

    fun onIntent(groceriesIntent: GroceriesIntent) {
        when (groceriesIntent) {
            is GroceriesIntent.LoadData -> {
                if(_state.value.groceryBannerUiState.data==null){
                    getGroceriesBanner()
                }
                if(_state.value.groceryCategoryUiState.data==null){
                    getGroceriesCategory()
                }
            }

            is GroceriesIntent.Retry -> {
                retry()
            }
        }
    }

    private fun combineState(state: GroceriesCategoryUiState): GroceriesCategoryUiState {
        return state.copy(
            isLoading = state.groceryBannerUiState.isLoading || state.groceryCategoryUiState.isLoading,
            error = state.groceryBannerUiState.error ?: state.groceryCategoryUiState.error
        )
    }

    fun getGroceriesCategory() {
        viewModelScope.launch {
            groceriesCategoryUseCase().collectLatest { result ->
                _state.update { current ->
                    val updated = when (result) {

                        is Resource.Loading -> current.copy(
                            groceryCategoryUiState = current.groceryCategoryUiState.copy(
                                isLoading = true, error = null
                            )
                        )

                        is Resource.Success -> current.copy(
                            groceryCategoryUiState = current.groceryCategoryUiState.copy(
                                isLoading = false, data = result.data, error = null
                            )
                        )

                        is Resource.Error -> current.copy(
                            groceryCategoryUiState = current.groceryCategoryUiState.copy(
                                isLoading = false, error = result.message
                            )
                        )
                    }
                    combineState(updated)
                }
            }
        }
    }

    fun getGroceriesBanner() {
        viewModelScope.launch {
            groceriesBannerUseCase().collectLatest { result ->
                _state.update { current ->
                    val updated = when (result) {

                        is Resource.Loading -> current.copy(
                            groceryBannerUiState = current.groceryBannerUiState.copy(
                                isLoading = true, error = null
                            )
                        )

                        is Resource.Success -> current.copy(
                            groceryBannerUiState = current.groceryBannerUiState.copy(
                                isLoading = false, data = result.data, error = null
                            )
                        )

                        is Resource.Error -> current.copy(
                            groceryBannerUiState = current.groceryBannerUiState.copy(
                                isLoading = false, error = result.message
                            )
                        )
                    }
                    combineState(updated)
                }
            }
        }
    }

    fun retry() {
        val current = _state.value

        if (current.groceryBannerUiState.error != null) {
            getGroceriesBanner()
        }

        if (current.groceryCategoryUiState.error != null) {
            getGroceriesCategory()
        }
    }
}