package com.ag.commissions.dto

import com.ag.commissions.constant.Currency
import com.jparams.verifier.tostring.ToStringVerifier
import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import java.math.BigDecimal
import java.time.LocalDate

@JsonTest
class CurrencyRatesDtoIT {

    @Autowired
    private lateinit var json: JacksonTester<CurrencyRatesDto>

    private val dto = getCurrencyRatesDto()

    @Test
    fun shouldCheckEqualsContract() {
        EqualsVerifier.forClass(CurrencyRatesDto::class.java).suppress(Warning.BIGDECIMAL_EQUALITY).verify()
    }

    @Test
    fun shouldCheckToString() {
        ToStringVerifier.forClass(CurrencyRatesDto::class.java).verify()
    }

    @Test
    fun shouldDeserialize() {
        Assertions.assertThat(json.read("expected_CurrencyRatesDto.json").getObject()).isEqualTo(dto)
    }

    private fun getCurrencyRatesDto() =
        CurrencyRatesDto(
            success = true,
            historical = true,
            base = Currency.EUR,
            date = LocalDate.of(2021, 1, 1),
            rates = mapOf("USD" to BigDecimal.valueOf(1.5), "RUB" to BigDecimal.valueOf(150))
        )
}