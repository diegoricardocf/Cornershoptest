package com.example.cornershop.data.model

import com.example.cornershop.data.service.Api
import com.example.cornershop.data.service.BaseCallback
import retrofit2.Call

open class CounterRepository(private val api: Api){

    val genericError = "Something went wrong, try again later"


     open fun getCounters(success: (List<Counter>) -> Unit, failure: (String) -> Unit){
        api.getCounters().enqueue(object : BaseCallback<List<Counter>>() {
            override fun onSuccess(response: List<Counter>?) {
                response?.let {
                    success(response)
                }
            }
            override fun onError(error: String) {
                failure(error)
            }
            override fun onFailure(call: Call<List<Counter>>, t: Throwable) {
                failure(genericError)
            }
        })
    }

    fun createCounter(counter: Counter, success: (List<Counter>) -> Unit, failure: (String) -> Unit){
        api.createCounter(counter).enqueue(object : BaseCallback<List<Counter>>() {
            override fun onSuccess(response: List<Counter>?) {
                response?.let {
                    success(response)
                }
            }
            override fun onError(error: String) {
                failure(error)
            }
            override fun onFailure(call: Call<List<Counter>>, t: Throwable) {
                failure(genericError)
            }
        })
    }

    fun incrementCounter(counter: Counter, success: (List<Counter>) -> Unit, failure: (String) -> Unit){
        api.incrementCounter(counter).enqueue(object : BaseCallback<List<Counter>>() {
            override fun onSuccess(response: List<Counter>?) {
                response?.let {
                    success(response)
                }
            }
            override fun onError(error: String) {
                failure(error)
            }
            override fun onFailure(call: Call<List<Counter>>, t: Throwable) {
                failure(genericError)
            }
        })
    }

    fun decrementCounter(counter: Counter, success: (List<Counter>) -> Unit, failure: (String) -> Unit){
        api.decrementCounter(counter).enqueue(object : BaseCallback<List<Counter>>() {
            override fun onSuccess(response: List<Counter>?) {
                response?.let {
                    success(response)
                }
            }
            override fun onError(error: String) {
                failure(error)
            }
            override fun onFailure(call: Call<List<Counter>>, t: Throwable) {
                failure(genericError)
            }
        })
    }

    fun deleteCounter(counter: Counter, success: (List<Counter>) -> Unit, failure: (String) -> Unit){
        api.deleteCounter(counter).enqueue(object : BaseCallback<List<Counter>>() {
            override fun onSuccess(response: List<Counter>?) {
                response?.let {
                    success(response)
                }
            }
            override fun onError(error: String) {
                failure(error)
            }
            override fun onFailure(call: Call<List<Counter>>, t: Throwable) {
                failure(genericError)
            }
        })
    }
}