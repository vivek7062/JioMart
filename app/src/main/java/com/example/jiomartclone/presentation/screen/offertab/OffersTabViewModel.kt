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

    fun selectMenu(menuId: String) {
        _state.update {
            it.copy(selectedMenuId = menuId)
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

    fun toggleFilter(filterId: String, value : String){
        _state.update { current->
            val map = current.selectedFilters.toMutableMap()
            val set = map[filterId]?.toMutableSet()?:mutableSetOf()
            if(set.contains(value)) set.remove(value)
            else set.add(value)
            map[filterId] = set
            current.copy(selectedFilters = map)
        }
    }

    fun applyFilters(filters: Map<String, Set<String>>) {
        _state.update {
            it.copy(selectedFilters = filters)
        }
    }

    fun clearFilter() {
        _state.update {
            it.copy(selectedFilters = emptyMap())
        }
    }
}