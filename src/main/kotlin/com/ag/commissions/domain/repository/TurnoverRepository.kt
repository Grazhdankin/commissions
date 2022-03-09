package com.ag.commissions.domain.repository

import com.ag.commissions.domain.entity.Turnover
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface TurnoverRepository : JpaRepository<Turnover, Long> {

    fun findFirstByClientIdAndMonth(clientId: Long, month: LocalDate): Turnover?
}