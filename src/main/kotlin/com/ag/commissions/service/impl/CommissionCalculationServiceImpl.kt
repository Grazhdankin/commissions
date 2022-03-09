package com.ag.commissions.service.impl

import com.ag.commissions.constant.Currency
import com.ag.commissions.dto.CommissionDto
import com.ag.commissions.dto.TransactionDto
import com.ag.commissions.service.CommissionCalculationService
import com.ag.commissions.service.CommissionService
import com.ag.commissions.service.CurrencyRatesService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.math.MathFlux
import java.util.function.Function
import java.util.stream.Collectors

@Service
class CommissionCalculationServiceImpl(
    private val currencyRatesService: CurrencyRatesService,
    private val services: List<CommissionService>
) : CommissionCalculationService {

    override fun getCommission(transaction: TransactionDto): Mono<CommissionDto> =
        MathFlux.min(currencyRatesService.convertToEurDto(transaction)
            .flatMapIterable {
                services.stream().map { service -> service.getCommission(it) }.collect(Collectors.toList())
            }
            .flatMapSequential(Function.identity()))
            .map {
                CommissionDto(amount = it, currency = Currency.EUR)
            }
}