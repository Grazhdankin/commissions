package com.ag.commissions.domain.entity

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "turnover_discount_rules")
class TurnoverDiscountRule(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "turnover")
    val turnover: BigDecimal,

    @Column(name = "commission")
    val commission: BigDecimal
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TurnoverDiscountRule

        if (id != other.id) return false
        if (turnover != other.turnover) return false
        if (commission != other.commission) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + turnover.hashCode()
        result = 31 * result + commission.hashCode()
        return result
    }
}