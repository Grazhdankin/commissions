package com.ag.commissions.service.impl

import com.ag.commissions.domain.entity.DefaultRule
import com.ag.commissions.domain.repository.DefaultRuleRepository
import com.ag.commissions.dto.TransactionDto
import com.ag.commissions.service.CommissionService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.math.RoundingMode

@Suppress("BlockingMethodInNonBlockingContext")
@Service
class DefaultRuleCommissionService(
    private val defaultRuleRepository: DefaultRuleRepository
) : CommissionService {

    override fun getCommission(transaction: TransactionDto): Mono<BigDecimal> =
        getActualRule().map {
            val calculatedCommission = transaction.amount.multiply(it.commissionRate).setScale(2, RoundingMode.HALF_UP)
            when {
                calculatedCommission < it.commissionMinimum -> it.commissionMinimum
                else -> calculatedCommission
            }
        }

    private fun getActualRule(): Mono<DefaultRule> =
        Mono.just(
            defaultRuleRepository.findFirstByActualTrue()
                ?: throw IllegalStateException("Default rule is not present in the DB!")
        )
}