package com.ag.commissions.dto

import java.math.BigDecimal

data class CommissionDto(

    val amount: BigDecimal,

    val currency: String
)
