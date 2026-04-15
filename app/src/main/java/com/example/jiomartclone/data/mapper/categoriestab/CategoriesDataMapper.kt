fun CategoriesDataDto.toDomain(): CategoriesData {
    return CategoriesData(
        categories = categories.map { it.toDomain() },
        leftMenu = leftMenu.map { it.toDomain() }
    )
}