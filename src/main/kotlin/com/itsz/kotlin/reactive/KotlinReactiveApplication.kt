package com.itsz.kotlin.reactive

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
class KotlinReactiveApplication

fun main(args: Array<String>) {
	runApplication<KotlinReactiveApplication>(*args)
}
