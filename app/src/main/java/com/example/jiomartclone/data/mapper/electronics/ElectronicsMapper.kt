package com.example.jiomartclone.data.mapper.electronics

import com.example.jiomartclone.data.mapper.lowprice.toDomain
import com.example.jiomartclone.data.remote.dto.electronics.ElectronicsDto
import com.example.jiomartclone.domain.model.electronics.Electronics

fun ElectronicsDto.toDomain() : Electronics{
    return Electronics(
        bgColor = bgColor,
        coupon = coupon?.toDomain(),
        headerImage = headerImage,
        id = id,
        items = items?.map { it.toDomain() },
        offers = offers?.map { it.toDomain() },
        products = products?.map { it.toDomain() },
        subtitle = subtitle,
        title = title,
        type = type,
        brand = brand
    )
}