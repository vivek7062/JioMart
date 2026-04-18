package com.example.jiomartclone.data.mapper.lowprice

import com.example.jiomartclone.data.remote.dto.category.ProductDto
import com.example.jiomartclone.domain.model.lowprice.Product

fun ProductDto.toDomain(): Product{
    return Product(
        brand = brand,
        category = category,
        menuId = menuId,
        subCategory = subCategory,
        description = description,
        foodType = foodType,
        isNew = isNew,
        isWishlist = isWishlist,
        productDiscount = discount,
        productId = productId,
        productImageUrl = productImageUrl,
        productInfo = productInfo,
        productMaxDiscount = productMaxDiscount,
        productName = name,
        productPrice = price,
        rating = rating,
        unit = unit
    )
}