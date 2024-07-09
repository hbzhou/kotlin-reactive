package com.itsz.kotlin.reactive.rxjava

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.relational.core.mapping.Table

@Entity
@Table
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val name: String
)
