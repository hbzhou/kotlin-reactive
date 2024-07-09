package com.itsz.kotlin.reactive.rxjava

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/customers")
class CustomerController(val customerService: CustomerService) {

    @GetMapping
    suspend fun findAll(): Flux<Customer> = customerService.findAll()

    @PostMapping
     fun save(@RequestBody customer: Customer): Mono<Customer> = customerService.save(customer)
}

