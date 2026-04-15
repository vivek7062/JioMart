package com.example.jiomartclone.core.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseViewModel : ViewModel() {
    protected val _loading = MutableStateFlow(false)
    val loading : StateFlow<Boolean> = _loading

}