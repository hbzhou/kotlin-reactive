package com.itsz.kotlin.reactive.sync

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/groups")
class GroupController(val groupService: GroupService) {

    @GetMapping
    suspend fun findAll(): List<Group> = groupService.findAll()


    @PostMapping
    suspend fun save(@RequestBody user: Group) = groupService.save(user)

}