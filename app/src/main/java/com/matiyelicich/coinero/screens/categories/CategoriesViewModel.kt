package com.matiyelicich.coinero.screens.categories

import androidx.lifecycle.viewModelScope
import com.matiyelicich.coinero.NEW_FINANCE_SCREEN
import com.matiyelicich.coinero.domain.DeleteFinanceUseCase
import com.matiyelicich.coinero.domain.GetFinancesUseCase
import com.matiyelicich.coinero.model.FinanceModel
import com.matiyelicich.coinero.model.FinancesUiState
import com.matiyelicich.coinero.screens.CoineroViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val deleteFinanceUseCase: DeleteFinanceUseCase,
    getFinancesUseCase: GetFinancesUseCase
) : CoineroViewModel() {

    val uiState: StateFlow<FinancesUiState> = getFinancesUseCase().map(FinancesUiState::Success)
        .catch { FinancesUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FinancesUiState.Loading)

    fun onNewExpense(openScreen: (String) -> Unit) {
        openScreen(NEW_FINANCE_SCREEN)
    }

    fun onItemRemove(financeModel: FinanceModel) {
        viewModelScope.launch {
            deleteFinanceUseCase(financeModel)
        }
    }

}