package com.example.jiomartclone.data.remote.dto.offertab

data class FiltersOfferDto(
    val brands: List<String>,
    val categories: List<String>,
    val discount: List<DiscountOfferDto>,
    val price: List<PriceOfferDto>,
    val subCategories: List<String>
)