package com.example.jiomartclone.presentation.screen.offertab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jiomartclone.core.common.Resource
import com.example.jiomartclone.domain.model.offertab.DiscountOffer
import com.example.jiomartclone.domain.model.offertab.PriceOffer
import com.example.jiomartclone.domain.usecase.offerstab.OffersTabUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OffersTabViewModel @Inject constructor(private val offersTabUseCase: OffersTabUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow(OffersTabUiState())
    val state: StateFlow<OffersTabUiState> = _state
    private var job: Job? = null

    fun onIntent(offersTabIntent: OffersTabIntent) {
        when (offersTabIntent) {
            OffersTabIntent.LoadData -> {
                if (_state.value.offersTabUiState.data == null) {
                    getOfferTabData()
                }
            }

            OffersTabIntent.Retry -> retry()
        }
    }

    fun getOfferTabData() {
        job?.cancel()
        job = viewModelScope.launch {
            offersTabUseCase().collectLatest { response ->
                _state.update { current ->
                    when (response) {
                        is Resource.Loading -> {
                            current.copy(
                                offersTabUiState = current.offersTabUiState.copy(
                                    isLoading = true, error = null
                                )
                            )
                        }

                        is Resource.Error -> {
                            current.copy(
                                offersTabUiState = current.offersTabUiState.copy(
                                    isLoading = false, error = response.message
                                )
                            )
                        }

                        is Resource.Success -> {
                            current.copy(
                                selectedMenuId = response.data.leftMenu.firstOrNull()?.id,
                                offersTabUiState = current.offersTabUiState.copy(
                                    isLoading = false, error = null, data = response.data
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    fun retry() {
        getOfferTabData()
    }

    fun toggleBrand(brand: String) {
        _state.update { current ->
            val updated = current.selectedBrands.toMutableSet()
            if (updated.contains(brand)) updated.remove(brand)
            else updated.add(brand)
            current.copy(selectedBrands = updated)
        }
    }

    fun toggleCategory(category: String) {
        _state.update { current ->
            val updated = current.selectedCategories.toMutableSet()
            if (updated.contains(category)) updated.remove(category)
            else updated.add(category)
            current.copy(selectedCategories = updated)
        }
    }

    fun toggleSubCategory(subcategory: String) {
        _state.update { current ->
            val updated = current.selectedSubCategories.toMutableSet()
            if (updated.contains(subcategory)) updated.remove(subcategory)
            else updated.add(subcategory)
            current.copy(selectedSubCategories = updated)
        }
    }

    fun selectPrice(range: PriceOffer) {
        _state.update { it.copy(selectedPriceRange = range) }
    }

    fun selectDiscount(range: DiscountOffer) {
        _state.update { it.copy(selectedDiscountRange = range) }
    }

    fun clearFilters() {
        _state.update {
            it.copy(
                selectedBrands = emptySet(),
                selectedCategories = emptySet(),
                selectedSubCategories = emptySet(),
                selectedPriceRange = null,
                selectedDiscountRange = null,
                isFilterApplied = false
            )
        }
    }
}