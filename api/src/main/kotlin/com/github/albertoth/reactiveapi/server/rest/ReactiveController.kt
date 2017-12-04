package com.github.albertoth.reactiveapi.server.rest

import com.github.albertosh.reactiveapi.datamodel.Item
import io.reactivex.Observable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.TimeUnit
import io.reactivex.functions.BiFunction as RxBifunction

@RestController
class ReactiveController {

    @RequestMapping(value = ["/reactive"], method = [RequestMethod.GET],
            produces = ["application/json", "application/stream+json"])
    fun getSomeItems(): Observable<Item> {
        val timer = Observable.interval(1000, TimeUnit.MILLISECONDS)
        val items = Observable.range(0, 3)
                .map { Item(it, "Description #$it") }

        return Observable.zip(timer, items, RxBifunction { _, u -> u })
    }

}