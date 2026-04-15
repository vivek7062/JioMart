fun CategoryDto.toDomain(): Category {
    return Category(
        menuId = menuId,
        sections = sections.map { it.toDomain() }
    )
}