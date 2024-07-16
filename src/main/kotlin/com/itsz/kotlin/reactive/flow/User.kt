package com.itsz.kotlin.reactive.flow

import jakarta.persistence.*
import org.springframework.data.relational.core.mapping.Table

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_generator")
    @SequenceGenerator(name = "users_generator", sequenceName = "users_id_seq", allocationSize = 1)
    val id: Long,
    val name: String
)