package com.itsz.kotlin.reactive.sync

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.time.LocalDateTime

@Service
class GroupService(val groupRepository: GroupRepository, val webClient: WebClient) {

    suspend fun findAll(): List<Group> {
        return groupRepository.findAll().toList()
    }

    suspend fun save(user: Group) = groupRepository.save(user)

    private suspend fun responseEntity(index: Int): Group {
        println("${Thread.currentThread().name} ----> launch a new job $index at ${LocalDateTime.now()}")
        return webClient.get()
            .uri("/delay/$index")
            .retrieve()
            .toBodilessEntity()
            .map { Group(index.toLong(), it.toString()) }
            .awaitSingle()
    }
}