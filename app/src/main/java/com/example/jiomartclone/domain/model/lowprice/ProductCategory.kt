package com.example.jiomartclone.domain.model.lowprice

data class ProductCategory(
    val categoryId: String,
    val categoryName: String,
    val page: Int,
    val pageSize: Int,
    val products: List<Product>,
    val totalProducts: Int
)