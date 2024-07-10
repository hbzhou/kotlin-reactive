package com.itsz.kotlin.reactive.sync

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/groups")
class GroupController(val groupService: GroupService) {

    @GetMapping
    suspend fun findAll(): List<Group> = groupService.findAll()

    @GetMapping("/block")
    suspend fun findAllBlock(): List<Group> = groupService.findAllBlock()

    @PostMapping
    fun save(@RequestBody user: Group) = groupService.save(user)

}