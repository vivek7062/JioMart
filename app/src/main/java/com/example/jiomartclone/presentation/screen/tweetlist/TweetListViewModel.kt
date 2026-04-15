package com.example.jiomartclone.presentation.screen.tweetlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jiomartclone.core.common.Resource
import com.example.jiomartclone.core.common.UiState
import com.example.jiomartclone.core.dispatcher.DispatcherProvider
import com.example.jiomartclone.domain.usecase.home.GetTweetsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TweetListViewModel @Inject constructor(private val tweetListUseCase: GetTweetsByCategoryUseCase, private val dispatcher: DispatcherProvider) : ViewModel(){
    private val _uiState = MutableStateFlow(TweetUiState())
    val uiState : StateFlow<TweetUiState>
        get() = _uiState

    fun getTweetList(category : String){
        viewModelScope.launch(dispatcher.io) {
            tweetListUseCase(category).collect { result ->
                when(result){
                    is Resource.Loading ->{
                        _uiState.update { it.copy(UiState(isLoading = true)) }
                    }
                    is Resource.Success ->{
                        _uiState.update { it.copy(homeUiState = UiState(isLoading = false, data = result.data)) }
                    }
                    is Resource.Error ->{
                        _uiState.update { it.copy(UiState(isLoading = false, error = result.message)) }
                    }
                }
            }
        }
    }
}