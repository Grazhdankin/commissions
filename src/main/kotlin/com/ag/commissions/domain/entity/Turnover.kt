package com.ag.commissions.domain.entity

import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "turnovers")
class Turnover(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "client_id")
    val clientId: Long,

    @Column(name = "month")
    val month: LocalDate,

    @Column(name = "turnover_amount")
    val turnoverAmount: BigDecimal
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Turnover

        if (id != other.id) return false
        if (clientId != other.clientId) return false
        if (month != other.month) return false
        if (turnoverAmount != other.turnoverAmount) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + clientId.hashCode()
        result = 31 * result + month.hashCode()
        result = 31 * result + turnoverAmount.hashCode()
        return result
    }
}