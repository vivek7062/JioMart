package com.example.jiomartclone.data.mapper.offertab

import com.example.jiomartclone.data.remote.dto.offertab.PriceOfferDto
import com.example.jiomartclone.domain.model.offertab.PriceOffer

fun PriceOfferDto.toDomain(): PriceOffer {
    return PriceOffer(
        label = label,
        min = min,
        max = max
    )
}