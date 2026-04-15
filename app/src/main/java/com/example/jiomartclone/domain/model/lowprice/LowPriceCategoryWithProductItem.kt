package com.example.jiomartclone.domain.model.lowprice

data class LowPriceCategoryWithProductItem(
    val id: String,
    val name: String,
    val products: List<Product>
)