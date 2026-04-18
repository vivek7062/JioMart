package com.example.jiomartclone.domain.model.offertab
import com.example.jiomartclone.domain.model.lowprice.Product

data class OfferResponse(
    val filters: FiltersOffer,
    val leftMenu: List<LeftMenuOffer>,
    val products: List<Product>,
    val screen: String,
)