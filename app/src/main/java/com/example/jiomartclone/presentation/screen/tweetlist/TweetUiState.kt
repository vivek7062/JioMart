package com.example.jiomartclone.presentation.screen.tweetlist

import com.example.jiomartclone.core.common.UiState
import com.example.jiomartclone.domain.model.Category

data class TweetUiState(
    val homeUiState: UiState<List<Category>> = UiState(),
)