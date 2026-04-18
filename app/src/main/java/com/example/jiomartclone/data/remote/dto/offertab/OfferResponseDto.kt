package com.example.jiomartclone.data.remote.dto.offertab

import com.example.jiomartclone.data.remote.dto.category.ProductDto

data class OfferResponseDto(
    val filters: FiltersOfferDto,
    val leftMenu: List<LeftMenuOfferDto>,
    val products: List<ProductDto>,
    val screen: String
)