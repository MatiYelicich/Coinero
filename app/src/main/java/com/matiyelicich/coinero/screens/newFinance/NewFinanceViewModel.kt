package com.matiyelicich.coinero.screens.newFinance

import androidx.lifecycle.viewModelScope
import com.matiyelicich.coinero.HOME_SCREEN
import com.matiyelicich.coinero.NEW_FINANCE_SCREEN
import com.matiyelicich.coinero.SPLASH_SCREEN
import com.matiyelicich.coinero.domain.AddFinanceUseCase
import com.matiyelicich.coinero.model.Categories
import com.matiyelicich.coinero.model.FinanceModel
import com.matiyelicich.coinero.model.Modes
import com.matiyelicich.coinero.screens.CoineroViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewFinanceViewModel @Inject constructor(
    private val addFinanceUseCase: AddFinanceUseCase,
) : CoineroViewModel() {

    fun onFinancesCreated(title: String, amount: String, date: String, mode: Modes, category: Categories) {
        viewModelScope.launch {
            addFinanceUseCase(
                FinanceModel(
                    title = title,
                    amount = amount,
                    date = date,
                    mode = mode,
                    category = category
                )
            )
        }
    }

    fun returnToHome(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(HOME_SCREEN, NEW_FINANCE_SCREEN)
    }
}
