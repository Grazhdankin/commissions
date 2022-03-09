package com.ag.commissions.service

import com.ag.commissions.dto.TransactionDto
import reactor.core.publisher.Mono

interface CurrencyRatesService {

    fun convertToEurDto(transactionDto: TransactionDto): Mono<TransactionDto>
}