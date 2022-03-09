package com.ag.commissions.service.impl

import com.ag.commissions.domain.entity.ClientDiscountRule
import com.ag.commissions.domain.repository.ClientDiscountRuleRepository
import com.ag.commissions.dto.TransactionDto
import com.ag.commissions.service.CommissionService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.math.BigDecimal

@Suppress("BlockingMethodInNonBlockingContext")
@Service
class ClientDiscountRuleCommissionService(
    private val clientDiscountRuleRepository: ClientDiscountRuleRepository
) : CommissionService {

    override fun getCommission(transaction: TransactionDto): Mono<BigDecimal> =
        getByClientId(transaction.clientId).map { it.commission }

    private fun getByClientId(clientId: Long): Mono<ClientDiscountRule> =
        Mono.justOrEmpty(clientDiscountRuleRepository.findFirstByClientId(clientId))
}