package com.ag.commissions.service

import com.ag.commissions.dto.TransactionDto
import reactor.core.publisher.Mono
import java.math.BigDecimal

interface CommissionService {

    fun getCommission(transaction: TransactionDto): Mono<BigDecimal>
}