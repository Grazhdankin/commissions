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

@JsonTest
class CommissionDtoIT {

    @Autowired
    private lateinit var json: JacksonTester<CommissionDto>

    private val dto = getCommissionDto()

    @Test
    fun shouldCheckEqualsContract() {
        EqualsVerifier.forClass(CommissionDto::class.java)
            .suppress(Warning.BIGDECIMAL_EQUALITY)
            .verify()
    }

    @Test
    fun shouldCheckToString() {
        ToStringVerifier.forClass(CommissionDto::class.java)
            .verify()
    }

    @Test
    fun shouldSerialize() {
        assertThat(json.write(dto)).isEqualToJson("expected_CommissionDto.json")
    }

    private fun getCommissionDto() =
        CommissionDto(
            amount = BigDecimal.valueOf(0.05),
            currency = Currency.EUR
        )
}