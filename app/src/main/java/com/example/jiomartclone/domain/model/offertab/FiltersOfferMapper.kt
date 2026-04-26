package com.example.jiomartclone.domain.model.offertab

fun FiltersOffer.toFilterSections(): List<FilterSection>{
    val list = mutableListOf<FilterSection>()
    list.add(
        FilterSection(
            id = "brand",
            title = "Brands",
            options = brands.map { FilterOption(it, it) }
        )
    )

    list.add(
        FilterSection(
            id = "category",
            title = "Categories",
            options = categories.map { FilterOption(it, it) }
        )
    )

    list.add(
        FilterSection(
            id = "subCategory",
            title = "Sub Categories",
            options = subCategories.map { FilterOption(it, it) }
        )
    )

    list.add(
        FilterSection(
            id = "price",
            title = "Price",
            options = price.map {
                FilterOption(it.label, it.label, it.min, it.max)
            }
        )
    )

    list.add(
        FilterSection(
            id = "discount",
            title = "Discount",
            options = discount.map {
                FilterOption(it.label, it.label, it.min, it.max)
            }
        )
    )
    return list
}