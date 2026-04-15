fun LeftMenuDto.toDomain(): LeftMenu {
    return LeftMenu(
        icon = icon,
        id = id,
        selected = selected,
        title = title
    )
}