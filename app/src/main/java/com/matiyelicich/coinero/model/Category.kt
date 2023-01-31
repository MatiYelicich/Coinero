package com.matiyelicich.coinero.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.matiyelicich.coinero.R
import com.matiyelicich.coinero.theme.*

enum class Categories {
    Comida, Supermercado, Salud, Compras, Transporte, Seguro, Entretenimiento, Servicios, Bebidas, Otros, None;

    companion object {
        fun getOptions(): List<String> {
            return values().filter { it != None }.map { it.name }
        }

        fun getStringResource(categoryName: String): Int {
            return when (categoryName) {
                "Comida" -> R.string.c_food
                "Supermercado" -> R.string.c_supermarket
                "Salud" -> R.string.c_health
                "Compras" -> R.string.c_shopping
                "Transporte" -> R.string.c_transportation
                "Seguro" -> R.string.c_insurance
                "Entretenimiento" -> R.string.c_entertainment
                "Servicios" -> R.string.c_services
                "Bebidas" -> R.string.c_drinks
                "Otros" -> R.string.c_others
                else -> R.string.error
            }
        }
    }
}
fun colorCategory(value: String): Color {

    return when (value) {
        "Comida" -> CRed
        "Transporte" -> CBlue
        "Supermercado" -> COrange
        "Compras" -> CPurple
        "Entretenimiento" -> CAquamarine
        "Servicios" -> CLightGreen
        "Salud" -> CPink
        "Seguro" -> CGreen
        "Bebidas" -> CLightBlue
        else -> Color.Gray
    }
}

fun iconCategory(value: String): ImageVector {

    return when (value) {
        "Comida" -> Icons.Rounded.Restaurant
        "Transporte" -> Icons.Rounded.Train
        "Supermercado" -> Icons.Rounded.ShoppingCart
        "Compras" -> Icons.Rounded.ShoppingBag
        "Entretenimiento" -> Icons.Rounded.LocalActivity
        "Servicios" -> Icons.Rounded.AccountBalance
        "Salud" -> Icons.Rounded.Favorite
        "Seguro" -> Icons.Rounded.Shield
        "Bebidas" -> Icons.Rounded.WaterDrop
        else -> Icons.Rounded.Label
    }
}