package com.ag.commissions.domain.entity

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "default_rules")
class DefaultRule(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "commission_rate")
    val commissionRate: BigDecimal,

    @Column(name = "commission_minimum")
    val commissionMinimum: BigDecimal,

    @Column(name = "actual")
    val actual: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DefaultRule

        if (id != other.id) return false
        if (commissionRate != other.commissionRate) return false
        if (commissionMinimum != other.commissionMinimum) return false
        if (actual != other.actual) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + commissionRate.hashCode()
        result = 31 * result + commissionMinimum.hashCode()
        result = 31 * result + actual.hashCode()
        return result
    }
}