package com.matiyelicich.coinero.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FinanceDao {

    @Query("SELECT * from FinanceEntity")
    fun getFinances(): Flow<List<FinanceEntity>>

    @Insert
    suspend fun addFinance(item: FinanceEntity)

    @Update
    suspend fun updateFinance(item: FinanceEntity)

    @Delete
    suspend fun deleteFinance(item: FinanceEntity)
}