package com.example.jiomartclone.data.remote.dto.category

data class ProductDto(
    val brand: String,
    val description: String,
    val foodType: String,
    val isNew: Boolean,
    val isWishlist: Boolean,
    val discount: Int,
    val productId: Int,
    val productImageUrl: String,
    val productInfo: String,
    val productMaxDiscount: Int,
    val name: String,
    val price: Int,
    val rating: Double,
    val unit: String
)