package com.example.cornershop.ui.create

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cornershop.data.model.Counter
import com.example.cornershop.data.model.CounterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateCounterViewmodel (private val repository: CounterRepository) : ViewModel() {
    private val counters = MutableLiveData<List<Counter>>()

    fun getCounters(): LiveData<List<Counter>> {
        return counters
    }

    fun createCounters(name:String) {
        val counter = Counter(title = name,count = 0)
        viewModelScope.launch(Dispatchers.IO) {
            repository.createCounter(counter, { responseCounters ->
                counters.postValue(responseCounters)
            }, { error ->
                Log.d("CORNERSHOP - ERR", error)
            })
        }
    }
}