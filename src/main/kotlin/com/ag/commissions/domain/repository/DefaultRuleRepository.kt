package com.ag.commissions.domain.repository

import com.ag.commissions.domain.entity.DefaultRule
import org.springframework.data.jpa.repository.JpaRepository

interface DefaultRuleRepository : JpaRepository<DefaultRule, Long> {

    fun findFirstByActualTrue(): DefaultRule?
}