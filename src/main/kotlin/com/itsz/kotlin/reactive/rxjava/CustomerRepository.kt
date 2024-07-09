package com.itsz.kotlin.reactive.rxjava

import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface CustomerRepository : ReactiveCrudRepository<Customer, Long>