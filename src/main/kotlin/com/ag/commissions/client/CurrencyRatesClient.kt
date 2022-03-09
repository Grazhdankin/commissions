package com.ag.commissions.client

import com.ag.commissions.dto.CurrencyRatesDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import reactivefeign.spring.config.ReactiveFeignClient
import reactor.core.publisher.Mono
import java.time.LocalDate

@ReactiveFeignClient(name = "currency-rates-client", url = "\${app.currency-rates-client.url}")
interface CurrencyRatesClient {

    @GetMapping(value = ["/{date}"])
    fun getRatesByDate(@PathVariable date: LocalDate): Mono<CurrencyRatesDto>
}