package com.itsz.kotlin.reactive.sync

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.relational.core.mapping.Table

@Entity
@Table(name = "groups")
data class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val name: String
)