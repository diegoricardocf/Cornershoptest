package com.example.cornershop

import com.example.cornershop.data.service.Api
import com.example.cornershop.data.model.Counter
import com.example.cornershop.data.model.CounterRepository

class CounterRepositoryMock(api: Api) : CounterRepository(api) {
    var resultList = listOf<Counter>()
    override  fun getCounters(success: (List<Counter>) -> Unit, failure: (String) -> Unit){
        success(resultList)
    }

    fun test(){}
}