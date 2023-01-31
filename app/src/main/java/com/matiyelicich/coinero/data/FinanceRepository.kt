package com.matiyelicich.coinero.data

import com.matiyelicich.coinero.data.FinanceDao
import com.matiyelicich.coinero.data.FinanceEntity
import com.matiyelicich.coinero.model.FinanceModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FinanceRepository @Inject constructor(private val financeDao: FinanceDao) {

    val finances: Flow<List<FinanceModel>> =
        financeDao.getFinances().map { items -> items.map { FinanceModel(it.id, it.title, it.amount, it.date, it.mode, it.category) } }

    suspend fun add(financeModel: FinanceModel) {
        financeDao.addFinance(financeModel.toData())
    }

    suspend fun update(financeModel: FinanceModel) {
        financeDao.updateFinance(financeModel.toData())
    }

    suspend fun delete(financeModel: FinanceModel) {
        financeDao.deleteFinance(financeModel.toData())
    }
}

fun FinanceModel.toData():FinanceEntity = FinanceEntity(this.id, this.title, this.amount, this.date, this.mode, this.category)