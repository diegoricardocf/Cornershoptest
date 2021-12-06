package com.example.cornershop.data.service

import com.example.cornershop.data.model.Counter
import retrofit2.Call
import retrofit2.http.*

interface Api{

    @GET("api/v1/counters")
    fun getCounters(): Call<List<Counter>>

    @POST("api/v1/counter")
    fun createCounter(@Body counter: Counter): Call<List<Counter>>

    @POST("api/v1/counter/inc")
    fun incrementCounter(@Body counter: Counter): Call<List<Counter>>

    @POST("api/v1/counter/dec")
    fun decrementCounter(@Body counter: Counter): Call<List<Counter>>

    @HTTP(method = "DELETE", path = "api/v1/counter", hasBody = true)
    fun deleteCounter(@Body counter: Counter): Call<List<Counter>>

}