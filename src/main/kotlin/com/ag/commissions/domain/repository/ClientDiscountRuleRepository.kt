package com.ag.commissions.domain.repository

import com.ag.commissions.domain.entity.ClientDiscountRule
import org.springframework.data.jpa.repository.JpaRepository

interface ClientDiscountRuleRepository : JpaRepository<ClientDiscountRule, Long> {

    fun findFirstByClientId(clientId: Long): ClientDiscountRule?
}