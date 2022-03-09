package com.ag.commissions.service.impl

import com.ag.commissions.domain.entity.Turnover
import com.ag.commissions.domain.repository.TurnoverRepository
import com.ag.commissions.dto.TransactionDto
import com.ag.commissions.service.TurnoverService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.LocalDate

@Suppress("BlockingMethodInNonBlockingContext")
@Service
class TurnoverServiceImpl(
    private val turnoverRepository: TurnoverRepository
) : TurnoverService {

    override fun getTurnoverByClientId(clientId: Long, month: LocalDate) =
        Mono.just(
            turnoverRepository.findFirstByClientIdAndMonth(
                clientId,
                LocalDate.of(month.year, month.month, 1)
            )?.turnoverAmount ?: BigDecimal.ZERO
        )

    override fun createOrUpdateTurnover(transaction: TransactionDto): Mono<Void> {
        val monthToSave = LocalDate.of(transaction.date.year, transaction.date.month, 1)

        turnoverRepository.findFirstByClientIdAndMonth(transaction.clientId, monthToSave)?.let {
            turnoverRepository.save(
                Turnover(
                    id = it.id,
                    clientId = it.clientId,
                    month = monthToSave,
                    turnoverAmount = it.turnoverAmount.add(transaction.amount)
                )
            )
        } ?: run {
            turnoverRepository.save(
                Turnover(
                    clientId = transaction.clientId,
                    month = monthToSave,
                    turnoverAmount = transaction.amount
                )
            )
        }

        return Mono.empty()
    }
}