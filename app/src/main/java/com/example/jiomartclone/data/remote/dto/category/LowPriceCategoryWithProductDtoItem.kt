package com.example.jiomartclone.data.remote.dto.category

data class LowPriceCategoryWithProductDtoItem(
    val categoryId: String,
    val categoryName: String,
    val products: List<ProductDto>
)