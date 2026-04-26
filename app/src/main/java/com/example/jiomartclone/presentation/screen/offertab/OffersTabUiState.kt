package com.example.jiomartclone.presentation.screen.offertab

import com.example.jiomartclone.core.common.UiState
import com.example.jiomartclone.domain.model.offertab.OfferResponse

data class OffersTabUiState(
    val offersTabUiState: UiState<OfferResponse> = UiState(),
    val selectedMenuId: String? = null,
    val selectedFilters: Map<String, Set<String>> = emptyMap()
)
