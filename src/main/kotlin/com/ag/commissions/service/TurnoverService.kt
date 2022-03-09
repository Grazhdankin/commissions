package com.ag.commissions.service

import com.ag.commissions.dto.TransactionDto
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.LocalDate

interface TurnoverService {

    fun getTurnoverByClientId(clientId: Long, month: LocalDate): Mono<BigDecimal>

    fun createOrUpdateTurnover(transaction: TransactionDto): Mono<Void>
}