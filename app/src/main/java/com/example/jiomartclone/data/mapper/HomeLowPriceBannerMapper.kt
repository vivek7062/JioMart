package com.example.jiomartclone.data.mapper

import com.example.jiomartclone.data.remote.dto.category.HomeHeaderCategoryDto
import com.example.jiomartclone.data.remote.dto.category.HomeLowPriceBannerDto
import com.example.jiomartclone.domain.model.HomeLowPriceBanner

fun HomeLowPriceBannerDto.toDomain() : HomeLowPriceBanner{
    return HomeLowPriceBanner(url = bannerUrl, action = action)
}