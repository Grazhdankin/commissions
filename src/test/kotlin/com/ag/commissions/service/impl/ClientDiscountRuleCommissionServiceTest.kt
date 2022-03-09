package com.ag.commissions.service.impl

import com.ag.commissions.constant.Currency
import com.ag.commissions.domain.entity.ClientDiscountRule
import com.ag.commissions.domain.repository.ClientDiscountRuleRepository
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
import reactor.test.StepVerifier
import java.math.BigDecimal
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class ClientDiscountRuleCommissionServiceTest {

    @Mock
    private lateinit var clientDiscountRuleRepository: ClientDiscountRuleRepository

    @InjectMocks
    private lateinit var victim: ClientDiscountRuleCommissionService

    private val clientDiscountRule = getClientDiscountRule()
    private val transactionDto = getTransactionDto()

    @Test
    fun shouldGetCommission() {
        whenever(clientDiscountRuleRepository.findFirstByClientId(any())).doReturn(clientDiscountRule)

        StepVerifier.create(victim.getCommission(transactionDto))
            .expectNext(clientDiscountRule.commission)
            .verifyComplete()

        then(clientDiscountRuleRepository).should().findFirstByClientId(transactionDto.clientId)
    }

    private fun getClientDiscountRule() =
        ClientDiscountRule(
            id = 1,
            clientId = 42,
            commission = BigDecimal.valueOf(0.05)
        )

    private fun getTransactionDto() =
        TransactionDto(
            date = LocalDate.of(2021, 1, 1),
            amount = BigDecimal.TEN,
            currency = Currency.EUR,
            clientId = 42
        )
}