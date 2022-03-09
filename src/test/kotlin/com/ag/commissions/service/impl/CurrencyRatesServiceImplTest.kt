package com.ag.commissions.service.impl

import com.ag.commissions.client.CurrencyRatesClient
import com.ag.commissions.constant.Currency
import com.ag.commissions.dto.CurrencyRatesDto
import com.ag.commissions.dto.TransactionDto
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.then
import org.mockito.kotlin.whenever
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class CurrencyRatesServiceImplTest {

    @Mock
    private lateinit var currencyRatesClient: CurrencyRatesClient

    @InjectMocks
    private lateinit var victim: CurrencyRatesServiceImpl

    private val transactionDtoEur = getTransactionDtoEur()
    private val transactionDtoUsd = getTransactionDtoUsd()
    private val transactionDtoAaa = getTransactionDtoAaa()
    private val currencyRatesDto = getCurrencyRatesDto()

    @Test
    fun shouldConvertToEurDtoWithEurTransaction() {
        StepVerifier.create(victim.convertToEurDto(transactionDtoEur))
            .expectNext(
                TransactionDto(
                    date = transactionDtoEur.date,
                    amount = transactionDtoEur.amount.setScale(2),
                    currency = Currency.EUR,
                    clientId = transactionDtoEur.clientId
                )
            )
            .verifyComplete()
    }

    @Test
    fun shouldConvertToEurDtoWithUsdTransaction() {
        whenever(currencyRatesClient.getRatesByDate(any())).doReturn(Mono.just(currencyRatesDto))

        StepVerifier.create(victim.convertToEurDto(transactionDtoUsd))
            .expectNext(
                TransactionDto(
                    date = transactionDtoEur.date,
                    amount = transactionDtoEur.amount.divide(
                        currencyRatesDto.rates[transactionDtoUsd.currency],
                        2,
                        RoundingMode.HALF_UP
                    ),
                    currency = Currency.EUR,
                    clientId = transactionDtoEur.clientId
                )
            )
            .verifyComplete()

        then(currencyRatesClient).should().getRatesByDate(transactionDtoUsd.date)
    }

    @Test
    fun shouldNotConvertToEurDtoWithInvalidCurrencyTransaction() {
        whenever(currencyRatesClient.getRatesByDate(any())).doReturn(Mono.just(currencyRatesDto))

        StepVerifier.create(victim.convertToEurDto(transactionDtoAaa))
            .expectErrorMatches { t: Throwable ->
                (t is IllegalArgumentException && "Invalid currency or rate is not present!" == t.message)
            }
            .verify()

        then(currencyRatesClient).should().getRatesByDate(transactionDtoUsd.date)
    }

    private fun getTransactionDtoEur() =
        TransactionDto(
            date = LocalDate.of(2021, 1, 1),
            amount = BigDecimal.TEN,
            currency = Currency.EUR,
            clientId = 42
        )

    private fun getTransactionDtoUsd() =
        TransactionDto(
            date = LocalDate.of(2021, 1, 1),
            amount = BigDecimal.TEN,
            currency = Currency.USD,
            clientId = 42
        )

    private fun getTransactionDtoAaa() =
        TransactionDto(
            date = LocalDate.of(2021, 1, 1),
            amount = BigDecimal.TEN,
            currency = "AAA",
            clientId = 42
        )

    private fun getCurrencyRatesDto() =
        CurrencyRatesDto(
            success = true,
            historical = true,
            base = Currency.EUR,
            date = LocalDate.of(2021, 1, 1),
            rates = mapOf("USD" to BigDecimal.valueOf(1.5), "RUB" to BigDecimal.valueOf(150))
        )
}