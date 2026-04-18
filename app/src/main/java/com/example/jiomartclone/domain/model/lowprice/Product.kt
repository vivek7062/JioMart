package com.example.jiomartclone.domain.model.lowprice

data class Product(
    val brand: String,
    val menuId: String?,
    val category: String?,
    val subCategory: String?,
    val description: String,
    val foodType: String,
    val isNew: Boolean,
    val isWishlist: Boolean,
    val productDiscount: Int,
    val productId: Int,
    val productImageUrl: String,
    val productInfo: String,
    val productMaxDiscount: Int,
    val productName: String,
    val productPrice: Int,
    val rating: Double,
    val unit: String
)