package com.ag.commissions.service.impl

import com.ag.commissions.domain.entity.TurnoverDiscountRule
import com.ag.commissions.domain.repository.TurnoverDiscountRuleRepository
import com.ag.commissions.dto.TransactionDto
import com.ag.commissions.service.CommissionService
import com.ag.commissions.service.TurnoverService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.math.MathFlux
import java.math.BigDecimal

@Suppress("BlockingMethodInNonBlockingContext")
@Service
class TurnoverDiscountRuleCommissionService(
    private val turnoverDiscountRuleRepository: TurnoverDiscountRuleRepository,
    private val turnoverService: TurnoverService
) : CommissionService {

    @Suppress("ReactiveStreamsUnusedPublisher")
    override fun getCommission(transaction: TransactionDto): Mono<BigDecimal> =
        MathFlux.min(turnoverService.getTurnoverByClientId(transaction.clientId, transaction.date)
            .flatMapMany { getAllByTurnover(it) }.map { it.commission })
            .doOnSuccess { turnoverService.createOrUpdateTurnover(transaction) }

    private fun getAllByTurnover(turnover: BigDecimal): Flux<TurnoverDiscountRule> =
        Flux.fromIterable(turnoverDiscountRuleRepository.findAllByTurnoverLessThanEqual(turnover))
}