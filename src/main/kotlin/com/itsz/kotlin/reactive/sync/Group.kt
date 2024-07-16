package com.itsz.kotlin.reactive.sync

import jakarta.persistence.*


@Entity
@Table(name = "groups")
data class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_generator")
    @SequenceGenerator(name = "group_generator", sequenceName = "groups_id_seq", allocationSize = 1)
    val id: Long,
    val name: String
)