package com.matiyelicich.coinero.screens.splash

import androidx.compose.runtime.mutableStateOf
import com.matiyelicich.coinero.HOME_SCREEN
import com.matiyelicich.coinero.SPLASH_SCREEN
import com.matiyelicich.coinero.screens.CoineroViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : CoineroViewModel() {
    val showError = mutableStateOf(false)

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {

        showError.value = false
        openAndPopUp(HOME_SCREEN, SPLASH_SCREEN)
    }
}
