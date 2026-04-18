package com.example.jiomartclone.presentation.screen.offertab

import com.example.jiomartclone.core.common.UiState
import com.example.jiomartclone.domain.model.offertab.DiscountOffer
import com.example.jiomartclone.domain.model.offertab.OfferResponse
import com.example.jiomartclone.domain.model.offertab.PriceOffer

data class OffersTabUiState(
    val offersTabUiState: UiState<OfferResponse> = UiState(),
    val selectedMenuId: String? = null,
    val selectedBrands: Set<String> = emptySet(),
    val selectedCategories: Set<String> = emptySet(),
    val selectedSubCategories: Set<String> = emptySet(),
    val selectedPriceRange: PriceOffer? = null,
    val selectedDiscountRange: DiscountOffer? = null,
    val isFilterApplied: Boolean = false
)
