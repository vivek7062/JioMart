package com.example.jiomartclone.data.mapper.offertab

import com.example.jiomartclone.data.mapper.lowprice.toDomain
import com.example.jiomartclone.data.remote.dto.offertab.OfferResponseDto
import com.example.jiomartclone.domain.model.offertab.OfferResponse

fun OfferResponseDto.toDomain(): OfferResponse {
    return OfferResponse(
        filters = filters.toDomain(),
        leftMenu = leftMenu.map { it.toDomain() },
        products = products.map { it.toDomain() },
        screen =screen
    )
}