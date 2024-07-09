package com.itsz.kotlin.reactive.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class Configuration {

    @Bean
    fun webClient() = WebClient.builder().baseUrl("https://httpbin.org/").build()

    @Bean
    fun restClient() = RestClient.builder().baseUrl("https://httpbin.org/").build()


}