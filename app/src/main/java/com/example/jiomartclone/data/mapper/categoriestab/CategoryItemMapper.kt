fun CategoryItemDto.toDomain(): CategoryItem {
    return CategoryItem(
        id = id,
        image = image,
        title = title
    )
}