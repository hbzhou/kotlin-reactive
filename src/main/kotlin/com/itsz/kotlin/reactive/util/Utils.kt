package com.itsz.kotlin.reactive.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

val Dispatchers.VT: ExecutorCoroutineDispatcher
    get() =  Executors.newVirtualThreadPerTaskExecutor().asCoroutineDispatcher()