package com.matiyelicich.coinero.domain

import com.matiyelicich.coinero.data.FinanceRepository
import com.matiyelicich.coinero.model.FinanceModel
import javax.inject.Inject

class DeleteFinanceUseCase @Inject constructor(private val financeRepository: FinanceRepository) {

    suspend operator fun invoke(financeModel: FinanceModel) {
        financeRepository.delete(financeModel)
    }
}