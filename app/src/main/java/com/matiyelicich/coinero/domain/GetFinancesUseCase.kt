package com.matiyelicich.coinero.domain

import com.matiyelicich.coinero.data.FinanceRepository
import com.matiyelicich.coinero.model.FinanceModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFinancesUseCase @Inject constructor(private val financeRepository: FinanceRepository) {

    operator fun invoke(): Flow<List<FinanceModel>> = financeRepository.finances
}