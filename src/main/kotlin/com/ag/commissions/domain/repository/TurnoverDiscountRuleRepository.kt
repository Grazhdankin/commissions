package com.ag.commissions.domain.repository

import com.ag.commissions.domain.entity.TurnoverDiscountRule
import org.springframework.data.jpa.repository.JpaRepository
import java.math.BigDecimal

interface TurnoverDiscountRuleRepository : JpaRepository<TurnoverDiscountRule, Long> {

    fun findAllByTurnoverLessThanEqual(turnover: BigDecimal): List<TurnoverDiscountRule>
}