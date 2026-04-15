package com.example.jiomartclone.data.mapper.electronics

import com.example.jiomartclone.data.remote.dto.electronics.CouponDto
import com.example.jiomartclone.domain.model.electronics.Coupon

fun CouponDto.toDomain() : Coupon{
    return Coupon(
        code = code,
        text = text
    )
}