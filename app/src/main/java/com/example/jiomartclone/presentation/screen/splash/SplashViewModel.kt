package com.example.jiomartclone.presentation.screen.splash

import androidx.lifecycle.viewModelScope
import com.example.jiomartclone.core.base.BaseViewModel
import com.example.jiomartclone.data.local.datastore.DataStoreManager
import com.example.jiomartclone.presentation.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val dataStoreManager: DataStoreManager) : BaseViewModel()  {
    init {
        checkLoginStatus()
    }

    private val _startDestination = MutableStateFlow<String?>(null)
    val startDestination: StateFlow<String?> = _startDestination
    private fun checkLoginStatus(){
        viewModelScope.launch {
            delay(2500)
            val token = dataStoreManager.getToken()
            if(token==null){
                _startDestination.value = Routes.Login.route
            }else{
                _startDestination.value = Routes.Home.route
            }
        }
    }
}