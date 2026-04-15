package com.example.jiomartclone.data.remote.dto.electronics

import com.example.jiomartclone.data.remote.dto.category.ProductDto

data class ElectronicsDto(
    val bgColor: String,
    val coupon: CouponDto?,
    val headerImage: String,
    val id: String,
    val items: List<ElectronicsItemDto>?,
    val offers: List<OfferDto>?,
    val products: List<ProductDto>?,
    val subtitle: String?,
    val title: String,
    val type: String,
    val brand : List<String>
)