package com.ag.commissions.service

import com.ag.commissions.dto.CommissionDto
import com.ag.commissions.dto.TransactionDto
import reactor.core.publisher.Mono

interface CommissionCalculationService {

    fun getCommission(transaction: TransactionDto): Mono<CommissionDto>
}