package com.ag.commissions.service.impl

import com.ag.commissions.client.CurrencyRatesClient
import com.ag.commissions.constant.CacheName
import com.ag.commissions.constant.Currency
import com.ag.commissions.dto.CurrencyRatesDto
import com.ag.commissions.dto.TransactionDto
import com.ag.commissions.service.CurrencyRatesService
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Service
class CurrencyRatesServiceImpl(
    private val currencyRatesClient: CurrencyRatesClient
) : CurrencyRatesService {

    override fun convertToEurDto(transactionDto: TransactionDto): Mono<TransactionDto> =
        getCurrencyRate(transactionDto).map {
            TransactionDto(
                date = transactionDto.date,
                amount = transactionDto.amount.divide(it, 2, RoundingMode.HALF_UP),
                currency = Currency.EUR,
                clientId = transactionDto.clientId
            )
        }

    private fun getCurrencyRate(transaction: TransactionDto): Mono<BigDecimal> =
        when (transaction.currency) {
            Currency.EUR -> Mono.just(BigDecimal.ONE)
            else -> getRatesByDate(transaction.date).map {
                it.rates[transaction.currency]
                    ?: throw IllegalArgumentException("Invalid currency or rate is not present!")
            }
        }

    @Cacheable(value = [CacheName.RATES], key = "#date")
    fun getRatesByDate(date: LocalDate): Mono<CurrencyRatesDto> =
        currencyRatesClient.getRatesByDate(date).cache()
}