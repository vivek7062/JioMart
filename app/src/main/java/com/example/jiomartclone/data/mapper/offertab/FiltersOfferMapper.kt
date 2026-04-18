package com.example.jiomartclone.data.mapper.offertab

import com.example.jiomartclone.data.remote.dto.offertab.FiltersOfferDto
import com.example.jiomartclone.domain.model.offertab.FiltersOffer

fun FiltersOfferDto.toDomain() : FiltersOffer {
    return FiltersOffer(
        brands = brands,
        categories = categories,
        discount = discount.map { it.toDomain() },
        price = price.map { it.toDomain() },
        subCategories =  subCategories
    )
}