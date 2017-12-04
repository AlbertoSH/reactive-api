package com.github.albertosh.reactiveapi.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.albertosh.reactiveapi.datamodel.Item
import io.reactivex.Observable
import okhttp3.ResponseBody
import okio.BufferedSource
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Streaming
import java.io.IOException

class RxClient {

    interface Service {
        @GET("/reactive")
        @Headers("Accept: application/stream+json")
        @Streaming
        fun executeRx() : Observable<ResponseBody>

        @GET("/reactive")
        @Headers("Accept: application/json")
        fun execute() : Observable<List<Item>>
    }

    private val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:8080")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

    private val service: Service = retrofit.create(Service::class.java)

    private val mapper = ObjectMapper()

    fun executeRx() = service.executeRx()
            .flatMap { events(it.source()) }
            .map { mapper.readValue(it, Item::class.java) }

    fun execute() = service.execute()

}

internal fun events(source: BufferedSource): Observable<String> {
    return Observable.create { subscriber ->
        try {
            while (!source.exhausted()) {
                subscriber.onNext(source.readUtf8Line()!!)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            subscriber.onError(e)
        }

        subscriber.onComplete()
    }
}
