package com.example.jiomartclone.data.mapper.electronics

import com.example.jiomartclone.data.remote.dto.electronics.ElectronicsItemDto
import com.example.jiomartclone.domain.model.electronics.ElectronicsItem

fun ElectronicsItemDto.toDomain() : ElectronicsItem{
    return ElectronicsItem(
        title = title,
        cta = cta,
        image = image,
        subtitle = subtitle
    )
}