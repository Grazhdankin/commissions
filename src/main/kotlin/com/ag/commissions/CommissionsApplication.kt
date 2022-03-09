package com.ag.commissions

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import reactivefeign.spring.config.EnableReactiveFeignClients

@SpringBootApplication
@EnableReactiveFeignClients
class CommissionsApplication

fun main(args: Array<String>) {
    runApplication<CommissionsApplication>(*args)
}
