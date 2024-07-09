package com.itsz.kotlin.reactive.sync

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository : CrudRepository<Group, Long>