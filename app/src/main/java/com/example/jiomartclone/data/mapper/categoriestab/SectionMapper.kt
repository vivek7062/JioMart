fun SectionDto.toDomain(): Section {
    return Section(
        items = items.map { it.toDomain() },
        title = title
    )
}