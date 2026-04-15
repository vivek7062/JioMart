package com.example.jiomartclone.data.remote.dto.category

data class ProductCategoryDto(
    val categoryId: String,
    val categoryName: String,
    val page: Int,
    val pageSize: Int,
    val products: List<ProductDto>,
    val totalProducts: Int
)