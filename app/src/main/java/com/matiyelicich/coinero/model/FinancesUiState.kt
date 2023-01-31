package com.matiyelicich.coinero.model

sealed interface FinancesUiState {

    object Loading:FinancesUiState
    data class Error(val throwable: Throwable):FinancesUiState
    data class Success(val finances: List<FinanceModel>):FinancesUiState
}