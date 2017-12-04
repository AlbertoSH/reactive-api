package com.github.albertosh.reactiveapi.client

import java.time.Duration
import java.time.LocalDateTime

fun main(args: Array<String>) {
    val client = RxClient()

    println("Regular request...")
    var time0 = LocalDateTime.now()
    lateinit var time1 : LocalDateTime
    client.execute()
            .doOnNext { time1 = LocalDateTime.now() }
            .subscribe { println("List all items: $it") }

    println("Time until first response: ${Duration.between(time0, time1)}")

    println("\n\n")


    println("Reactive request...")
    var time2 = LocalDateTime.now()
    var time3 : LocalDateTime? = null
    client.executeRx()
            .doOnNext { time3 = time3 ?: LocalDateTime.now() }
            .subscribe { println("List items as they are received: $it") }

    println("Time until first reactive response: ${Duration.between(time2, time3)}")
}