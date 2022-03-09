package com.ag.commissions.dto

import com.ag.commissions.constant.Currency
import com.jparams.verifier.tostring.ToStringVerifier
import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import java.math.BigDecimal
import java.time.LocalDate

@JsonTest
class TransactionDtoIT {

    @Autowired
    private lateinit var json: JacksonTester<TransactionDto>

    private val dto = getTransactionDto()

    @Test
    fun shouldCheckEqualsContract() {
        EqualsVerifier.forClass(TransactionDto::class.java)
            .suppress(Warning.BIGDECIMAL_EQUALITY)
            .verify()
    }

    @Test
    fun shouldCheckToString() {
        ToStringVerifier.forClass(TransactionDto::class.java)
            .verify()
    }

    @Test
    fun shouldDeserialize() {
        assertThat(json.read("expected_TransactionDto.json").getObject()).isEqualTo(dto)
    }


    private fun getTransactionDto() =
        TransactionDto(
            date = LocalDate.of(2021, 1, 1),
            amount = BigDecimal.TEN,
            currency = Currency.EUR,
            clientId = 1
        )
}