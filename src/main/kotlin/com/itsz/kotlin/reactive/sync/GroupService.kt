package com.itsz.kotlin.reactive.sync

import com.itsz.kotlin.reactive.util.VT
import io.netty.util.concurrent.CompleteFuture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.future.await
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.withContext
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.reactive.function.client.WebClient
import java.time.LocalDateTime
import java.util.concurrent.CompletableFuture
import kotlin.random.Random

@Service
class GroupService(val groupRepository: GroupRepository, val webClient: WebClient, val restClient: RestClient) {

    suspend fun findAllWithNIO(): List<Group> = coroutineScope {
        val groupList = (1..7 step 2).map { async { responseEntity(it) } }.map { it.await() }
        return@coroutineScope groupRepository.findAll() + groupList
    }

    suspend fun findAllWithVTContext(): List<Group> = withContext(Dispatchers.VT) {
        val groupList = (1..7 step 2).map { async { restClientResponse(it) } }
            .map { it.await() }
            .map { Group(it.statusCode.value().toLong(), it.toString()) }
        return@withContext groupRepository.findAll() + groupList
    }

    suspend fun findAllWithIOContext(): List<Group> = withContext(Dispatchers.IO) {
        val groupList = (1..7 step 2).map { async { restClientResponse(it) } }
            .map { it.await() }
            .map { Group(Random(10L).nextLong(), Thread.currentThread().toString()) }
        return@withContext groupRepository.findAll() + groupList
    }

    suspend fun findAll(): List<Group> {
        val groupList = (1..7 step 2).map { CompletableFuture.supplyAsync { restClientResponse(it) } }
            .map { it.await() }
            .map { Group(Random(10L).nextLong(), Thread.currentThread().toString()) }
        return groupRepository.findAll() + groupList
    }

    fun save(user: Group) = groupRepository.save(user)

    private suspend fun responseEntity(index: Int): Group {
        println("${Thread.currentThread()} ----> launch a new job $index at ${LocalDateTime.now()}")
        return webClient.get()
            .uri("/delay/$index")
            .retrieve()
            .toBodilessEntity()
            .map { Group(Random(10L).nextLong(), Thread.currentThread().toString()) }
            .awaitSingle()
    }

    private fun restClientResponse(index: Int): ResponseEntity<Void> {
        println("${Thread.currentThread()} ----> launch a new job $index at ${LocalDateTime.now()}")
        return restClient.get()
            .uri("/delay/$index")
            .retrieve()
            .toBodilessEntity()
    }


}