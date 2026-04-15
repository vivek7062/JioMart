package com.example.jiomartclone.data.mapper.electronics

import com.example.jiomartclone.data.remote.dto.electronics.OfferDto
import com.example.jiomartclone.domain.model.electronics.Offer

fun OfferDto.toDomain() : Offer{
    return Offer(
        title = title,
        code = code
    )
}