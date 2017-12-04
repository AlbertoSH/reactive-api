package com.github.albertoth.reactiveapi.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class App

fun main(args: Array<String>) {

    runApplication<App>(*args)
}
