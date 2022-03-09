package com.ag.commissions.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.LocalDate
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Digits
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class TransactionDto(

    val date: LocalDate,

    @field:DecimalMin(value = "0.01")
    @field:Digits(
        integer = 7,
        fraction = 2
    )
    val amount: BigDecimal,

    @field:NotBlank
    @field:Size
    val currency: String,

    @JsonProperty("client_id")
    val clientId: Long
)
