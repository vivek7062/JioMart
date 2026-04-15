package com.example.jiomartclone.data.mapper.lowprice

import com.example.jiomartclone.data.remote.dto.category.LowPriceCategoryWithProductDto
import com.example.jiomartclone.domain.model.lowprice.LowPriceCategoryWithProduct


fun LowPriceCategoryWithProductDto.toDomain() : LowPriceCategoryWithProduct {
    val list = LowPriceCategoryWithProduct()
    list.addAll(this@toDomain.map { it.toDomain() })
    return list
}