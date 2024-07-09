package com.itsz.kotlin.reactive.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.time.LocalDateTime

@Service
class UserService(val userRepository: UserRepository, val webClient: WebClient) {

    suspend fun findAll(): Flow<User> = coroutineScope {
        val response = (1..7 step 2).map { async { responseEntity(it) } }.map { it.await() }.asFlow()
        return@coroutineScope merge(userRepository.findAll(), response)
    }

    suspend fun findAllWithChannelFlow(): Flow<User> = coroutineScope {
        val response = channelFlow {
            for (index in (1..7 step 2)) {
                launch {
                    val user = responseEntity(index)
                    send(user)
                }
            }
        }
        return@coroutineScope merge(userRepository.findAll(), response)
    }

    suspend fun findAllWithFlowOf(): Flow<User> = withContext(Dispatchers.Default) {
        val users = (1..5).map { index -> async { responseEntity(index) } }.map { it.await() }
        val results = flowOf(*users.toTypedArray())
        return@withContext merge(userRepository.findAll(), results)
    }

    suspend fun save(user: User) = userRepository.save(user)

    private suspend fun responseEntity(index: Int): User {
        println("${Thread.currentThread().name} ----> launch a new job $index at ${LocalDateTime.now()}")
        return webClient.get()
            .uri("/delay/$index")
            .retrieve()
            .toBodilessEntity()
            .map { User(index.toLong(), it.toString()) }
            .awaitSingle()
    }
}