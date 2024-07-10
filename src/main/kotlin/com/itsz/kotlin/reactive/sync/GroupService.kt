package com.itsz.kotlin.reactive.sync

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.reactive.function.client.WebClient
import java.time.LocalDateTime

@Service
class GroupService(val groupRepository: GroupRepository, val webClient: WebClient, val restClient: RestClient) {

    suspend fun findAll(): List<Group> = coroutineScope {
        val groupList = (1..7 step 2).map { async { responseEntity(it) } }.map { it.await() }
        return@coroutineScope groupRepository.findAll() + groupList
    }

    fun findAllBlock(): List<Group> {
        val groupList = (1..7 step 2).map { restClientResponse(it) }
            .map { Group(it.statusCode.value().toLong(), it.toString()) }
        return groupRepository.findAll() + groupList
    }

    fun save(user: Group) = groupRepository.save(user)

    private suspend fun responseEntity(index: Int): Group {
        println("${Thread.currentThread().name} ----> launch a new job $index at ${LocalDateTime.now()}")
        return webClient.get()
            .uri("/delay/$index")
            .retrieve()
            .toBodilessEntity()
            .map { Group(index.toLong(), it.toString()) }
            .awaitSingle()
    }

    private fun restClientResponse(index: Int): ResponseEntity<Void> {
        println("${Thread.currentThread().name} ----> launch a new job $index at ${LocalDateTime.now()}")
        return restClient.get()
            .uri("/delay/$index")
            .retrieve()
            .toBodilessEntity()
    }


}