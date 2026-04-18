package com.example.jiomartclone.domain.model.offertab


data class FiltersOffer(
    val brands: List<String>,
    val categories: List<String>,
    val discount: List<DiscountOffer>,
    val price: List<PriceOffer>,
    val subCategories: List<String>
)
