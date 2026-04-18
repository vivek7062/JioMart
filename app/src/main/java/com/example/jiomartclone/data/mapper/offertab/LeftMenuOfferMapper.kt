package com.example.jiomartclone.data.mapper.offertab

import com.example.jiomartclone.data.remote.dto.offertab.LeftMenuOfferDto
import com.example.jiomartclone.domain.model.offertab.LeftMenuOffer

fun LeftMenuOfferDto.toDomain() : LeftMenuOffer{
    return LeftMenuOffer(
        icon = icon,
        id = id,
        title = title
    )
}