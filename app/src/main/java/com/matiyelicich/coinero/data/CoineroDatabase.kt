package com.matiyelicich.coinero.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FinanceEntity::class], version = 1)
abstract class CoineroDatabase:RoomDatabase() {

    abstract fun financeDao():FinanceDao
}