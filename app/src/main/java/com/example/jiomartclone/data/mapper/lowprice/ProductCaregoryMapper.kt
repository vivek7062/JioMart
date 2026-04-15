package com.example.jiomartclone.data.mapper.lowprice

import com.example.jiomartclone.data.remote.dto.category.ProductCategoryDto
import com.example.jiomartclone.domain.model.lowprice.ProductCategory

fun ProductCategoryDto.toDomain(): ProductCategory {
    return ProductCategory(
        categoryId = categoryId,
        categoryName = categoryName,
        page = page,
        pageSize = pageSize,
        products = products.map { it.toDomain() },
        totalProducts = totalProducts
    )
}