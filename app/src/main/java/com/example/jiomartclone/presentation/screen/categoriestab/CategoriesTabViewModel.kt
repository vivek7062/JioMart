package com.example.jiomartclone.presentation.screen.categoriestab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jiomartclone.core.common.Resource
import com.example.jiomartclone.domain.usecase.categories.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesTabViewModel @Inject constructor(private val getCategoriesUseCase: GetCategoriesUseCase) : ViewModel() {
    private val _state = MutableStateFlow(CategoriesUiState())
    val state : StateFlow<CategoriesUiState> = _state

    fun onIntent(categoriesIntent: CategoriesIntent){
        when(categoriesIntent){
            is CategoriesIntent.LoadData -> {
                if(_state.value.categoriesUiState.error!=null){
                    getCategories()
                }
            }
            is CategoriesIntent.Retry ->{
                retry()
            }
        }
    }

    fun getCategories(){
        viewModelScope.launch {
            getCategoriesUseCase().collectLatest { response->
                _state.update { current->
                    when(response){
                        is Resource.Loading -> {
                            current.copy(
                                categoriesUiState = current.categoriesUiState.copy(
                                    isLoading = true, error = null
                                )
                            )
                        }
                        is Resource.Error -> {
                            current.copy(
                                categoriesUiState = current.categoriesUiState.copy(
                                    isLoading = false, error = response.message
                                )
                            )
                        }
                        is Resource.Success->{
                            current.copy(
                                categoriesUiState = current.categoriesUiState.copy(
                                    isLoading = false, error = null, data = response.data
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    fun retry(){
        if(_state.value.categoriesUiState.error!=null){
            getCategories()
        }
    }
}