package com.example.jiomartclone.domain.model.electronics

import com.example.jiomartclone.domain.model.lowprice.Product


data class Electronics(
    val bgColor: String?,
    val coupon: Coupon?,
    val headerImage: String?,
    val id: String,
    val items: List<ElectronicsItem>?,
    val offers: List<Offer>?,
    val products: List<Product>?,
    val subtitle: String?,
    val title: String,
    val type: String,
    val brand : List<String>?
)
