package com.example.jiomartclone.presentation.screen.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jiomartclone.core.common.Resource
import com.example.jiomartclone.core.common.UiState
import com.example.jiomartclone.domain.usecase.categoryproduct.GetProductByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductByCategoryViewModel @Inject constructor(private val getProductByCategoryUseCase: GetProductByCategoryUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow(CategoryProductsUiState())
    val state: MutableStateFlow<CategoryProductsUiState> = _state

    fun getProductsByCategory(category: String) {
        viewModelScope.launch {
            getProductByCategoryUseCase(getCategory(category)).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(productUiState = UiState(isLoading = true))
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(productUiState = UiState(error = result.message))
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(productUiState = UiState(data = result.data))
                        }
                    }
                }
            }
        }
    }
    fun getCategory(category: String): String {
        return when (category) {
            "fruits" -> "69cb469f856a682189e4bb11"
            "vegetables" -> "69cb49bb36566621a8646842"
            "snacks" -> "69cb5388856a682189e4e627"
            "dairy" -> "69cb53a2856a682189e4e675"
            "beverages" -> "69cb540636566621a8648a39"
            "bakery" -> "69cb545f856a682189e4e8f8"
            "meat" -> "69cb54d2856a682189e4ea33"
            "frozen" -> "69cb54e9aaba882197ad1017"
            else -> "69cb469f856a682189e4bb11"
        }
    }

}