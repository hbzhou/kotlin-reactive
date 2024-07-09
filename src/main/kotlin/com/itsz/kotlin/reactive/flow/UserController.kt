package com.itsz.kotlin.reactive.flow

import kotlinx.coroutines.flow.Flow
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService) {

    @GetMapping
    suspend fun findAllWithFlow(): Flow<User> = userService.findAll()

    @GetMapping("/channelFlow")
    suspend fun findAllWithChannelFlow() = userService.findAllWithChannelFlow()

    @GetMapping("/flowOf")
    suspend fun findAllWithFlowOf() = userService.findAllWithFlowOf()

    @PostMapping
    suspend fun save(@RequestBody user: User) = userService.save(user)

}