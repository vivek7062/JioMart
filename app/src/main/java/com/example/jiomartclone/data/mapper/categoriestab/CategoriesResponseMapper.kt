fun CategoriesResponseDto.toDomain(): CategoriesResponse {
    return CategoriesResponse(
        data = data.toDomain(),
        status = status
    )
}