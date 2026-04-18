package com.example.jiomartclone.data.mapper.offertab

import com.example.jiomartclone.data.remote.dto.offertab.DiscountOfferDto
import com.example.jiomartclone.domain.model.offertab.DiscountOffer

fun DiscountOfferDto.toDomain() : DiscountOffer{
    return DiscountOffer(
        label = label,
        max = max,
        min = min
    )
}