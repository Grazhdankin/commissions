package com.ag.commissions.domain.entity

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "client_discount_rules")
class ClientDiscountRule(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "client_id")
    val clientId: Long,

    @Column(name = "commission")
    val commission: BigDecimal
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ClientDiscountRule

        if (id != other.id) return false
        if (clientId != other.clientId) return false
        if (commission != other.commission) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + clientId.hashCode()
        result = 31 * result + commission.hashCode()
        return result
    }
}