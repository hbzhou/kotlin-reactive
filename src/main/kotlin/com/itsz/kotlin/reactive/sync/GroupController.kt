package com.itsz.kotlin.reactive.sync

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/groups")
class GroupController(val groupService: GroupService) {

    @GetMapping("/nio")
    suspend fun findAllWithNIO(): List<Group> = groupService.findAllWithNIO()

    @GetMapping("/vt")
    suspend fun findAllWithVTContext(): List<Group> = groupService.findAllWithVTContext()

    @GetMapping("/io")
    suspend fun findAllWithIOContext(): List<Group> = groupService.findAllWithIOContext()

    @GetMapping
    suspend fun findAll() = groupService.findAll()

    @PostMapping
    fun save(@RequestBody user: Group) = groupService.save(user)

}