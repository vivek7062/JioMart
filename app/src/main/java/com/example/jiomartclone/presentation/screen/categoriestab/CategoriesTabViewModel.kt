package com.example.jiomartclone.presentation.screen.categoriestab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jiomartclone.core.common.Resource
import com.example.jiomartclone.domain.usecase.categories.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesTabViewModel @Inject constructor(private val getCategoriesUseCase: GetCategoriesUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow(CategoriesUiState())
    val state: StateFlow<CategoriesUiState> = _state
    private var job: Job? = null

    fun onIntent(categoriesIntent: CategoriesIntent) {
        when (categoriesIntent) {
            is CategoriesIntent.LoadData -> {
                if (_state.value.categoriesUiState.data == null) {
                    getCategories()
                }
            }
            is CategoriesIntent.Retry -> retry()
        }
    }

    fun selectMenu(menuId: String) {
        _state.update {
            it.copy(selectedMenu = menuId)
        }
    }

    fun getCategories() {
        job?.cancel()
        job = viewModelScope.launch {
            getCategoriesUseCase().collectLatest { response ->
                _state.update { current ->
                    when (response) {
                        is Resource.Loading -> current.copy(
                            categoriesUiState = current.categoriesUiState.copy(
                                isLoading = true,
                                error = null
                            )
                        )

                        is Resource.Error -> current.copy(
                            categoriesUiState = current.categoriesUiState.copy(
                                isLoading = false,
                                error = response.message
                            )
                        )

                        is Resource.Success -> {
                            val firstMenuId =
                                response.data.data.leftMenu.firstOrNull()?.id

                            current.copy(
                                selectedMenu = current.selectedMenu ?: firstMenuId,
                                categoriesUiState = current.categoriesUiState.copy(
                                    isLoading = false,
                                    error = null,
                                    data = response.data
                                )
                            )
                        }
                    }
                }
            }
        }
    }
    fun retry() {
        getCategories()
    }
}