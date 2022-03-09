package com.ag.commissions.controller

import com.ag.commissions.AbstractIT
import com.ag.commissions.constant.Currency
import com.ag.commissions.dto.CommissionDto
import com.ag.commissions.dto.TransactionDto
import com.ag.commissions.service.CommissionCalculationService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.then
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.LocalDate

class CommissionControllerIT : AbstractIT() {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var commissionCalculationService: CommissionCalculationService

    @SpyBean
    private lateinit var victim: CommissionController

    private val transactionDto = getTransactionDto()
    private val commissionDto = getCommissionDto()

    @Test
    fun shouldGetCommission() {
        whenever(commissionCalculationService.getCommission(any())).doReturn(Mono.just(commissionDto))

        val response = webTestClient.post()
            .uri("/api/v1/commission")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(transactionDto))
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.OK)
            .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
            .expectBody(CommissionDto::class.java)
            .returnResult()
            .responseBody

        then(victim).should().getCommission(transactionDto)
        then(commissionCalculationService).should().getCommission(transactionDto)
        assertEquals(response, commissionDto)
    }

    private fun getTransactionDto() =
        TransactionDto(
            date = LocalDate.of(2021, 1, 1),
            amount = BigDecimal.TEN,
            currency = Currency.EUR,
            clientId = 1
        )

    private fun getCommissionDto() =
        CommissionDto(
            amount = BigDecimal.valueOf(0.05),
            currency = Currency.EUR
        )
}