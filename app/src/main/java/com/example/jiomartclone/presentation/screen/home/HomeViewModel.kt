package com.example.jiomartclone.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jiomartclone.core.common.Resource
import com.example.jiomartclone.core.common.UiState
import com.example.jiomartclone.data.repositoryimplementation.CategoryRepositoryImpl
import com.example.jiomartclone.domain.usecase.home.GetCategoriesUseCase
import com.example.jiomartclone.domain.usecase.home.GetCategoryHeaderUseCase
import com.example.jiomartclone.domain.usecase.home.GetTweetsByCategoryUseCase
import com.example.jiomartclone.presentation.screen.hometab.electronics.ElectronicsIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val homeHeaderUseCase: GetCategoryHeaderUseCase) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState>
        get() = _state


    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadData -> {
                if (_state.value.homeUiState.data == null) {
                    getCategory()
                }
            }
            is HomeIntent.Retry -> {
                getCategory()
            }
        }
    }
    fun getCategory() {
        viewModelScope.launch {
            homeHeaderUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                homeUiState = UiState(isLoading = true)
                            )
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                homeUiState = UiState(isLoading = false, data = result.data),
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(homeUiState = UiState(isLoading = false, error = result.message))
                        }
                    }
                }
            }
        }
    }
}