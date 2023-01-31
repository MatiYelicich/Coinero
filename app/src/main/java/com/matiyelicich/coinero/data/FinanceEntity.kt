package com.matiyelicich.coinero.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.matiyelicich.coinero.model.Categories
import com.matiyelicich.coinero.model.Modes

@Entity
data class FinanceEntity(
    @PrimaryKey
    val id: Int,
    var title: String,
    var amount: String,
    var date: String,
    var mode: Modes,
    var category: Categories
)
