package com.itsz.kotlin.reactive.rxjava

import jakarta.persistence.*
import org.springframework.data.relational.core.mapping.Table

@Entity
@Table
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_generator")
    @SequenceGenerator(name = "customer_generator", sequenceName = "customer_id_seq", allocationSize = 1)
    val id: Long,
    val name: String
)
