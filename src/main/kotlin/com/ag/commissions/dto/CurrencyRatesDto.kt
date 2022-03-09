package com.ag.commissions.dto

import java.math.BigDecimal
import java.time.LocalDate

data class CurrencyRatesDto(

    val success: Boolean? = null,

    val historical: Boolean? = null,

    val base: String,

    val date: LocalDate,

    val rates: Map<String, BigDecimal>
)