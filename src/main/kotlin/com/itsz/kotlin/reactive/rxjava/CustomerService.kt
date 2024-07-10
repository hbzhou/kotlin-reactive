package com.itsz.kotlin.reactive.rxjava

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import java.time.LocalDateTime
import java.util.concurrent.Flow
import kotlin.random.Random

@Service
class CustomerService(
    val customerRepository: CustomerRepository,
    val webClient: WebClient,
    val restClient: RestClient
) {


    suspend fun findAll(): Flux<Customer> = coroutineScope {
        val response = (1..7 step 2).map { async { responseEntity(it) } }
            .map { it.await() }
            .toFlux()
        return@coroutineScope customerRepository.findAll().concatWith(response)
    }


    private suspend fun responseEntity(index: Int): Customer {
        println("${Thread.currentThread().name} ----> launch a new job $index at ${LocalDateTime.now()}")
        return webClient.get()
            .uri("/delay/$index")
            .retrieve()
            .toBodilessEntity()
            .map { Customer(Random(10L).nextLong(), Thread.currentThread().toString()) }
            .awaitSingle()
    }

    fun save(customer: Customer): Mono<Customer> = customerRepository.save(customer)

}


