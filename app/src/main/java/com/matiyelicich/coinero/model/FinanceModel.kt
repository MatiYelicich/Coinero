package com.matiyelicich.coinero.model

data class FinanceModel(
    val id: Int = System.currentTimeMillis().hashCode(),
    val title: String,
    val amount: String,
    val date: String,
    val mode: Modes,
    val category: Categories
)
