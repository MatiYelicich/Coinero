package com.matiyelicich.coinero.model

import com.matiyelicich.coinero.R

enum class Modes {
    Efectivo, Tarjeta, Disable;

    companion object {
        fun getStringResource(categoryName: String): Int {
            return when (categoryName) {
                "Efectivo" -> R.string.cash
                "Tarjeta" -> R.string.card
                else -> R.string.error
            }
        }
    }
}