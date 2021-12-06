package com.example.cornershop.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cornershop.data.model.Counter
import com.example.cornershop.data.model.CounterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: CounterRepository, private val dispatcher: CoroutineDispatcher) : ViewModel() {
    val counters = MutableLiveData<List<Counter>>()
    val showNoContentView = MutableLiveData<Boolean>()
    val totalTimes = MutableLiveData<Int>()
    val totalCounters = MutableLiveData<Int>()
    private val messageError = MutableLiveData<String>()
    private var fullCounterList: List<Counter>? = null
    private val showProgress = MutableLiveData<Boolean>()
    private var filterSearch = ""

    fun getCounters(): LiveData<List<Counter>> {
        return counters
    }
    fun getShowProgress(): LiveData<Boolean> {
        return showProgress
    }
    fun getShowNoContentView(): LiveData<Boolean> {
        return showNoContentView
    }
    fun getTotalTimes(): LiveData<Int> {
        return totalTimes
    }
    fun getTotalCounters(): LiveData<Int> {
        return totalCounters
    }
    fun getMessageError(): LiveData<String> {
        return messageError
    }

    fun loadCounters(pullToRefresh:Boolean) {
        viewModelScope.launch(dispatcher) {
            if(!pullToRefresh) {
                showProgress.postValue(true)
            }
            repository.getCounters({ responseCounters ->
                updateCounterList(responseCounters)
                showProgress.postValue(false)
            }, { error ->
                messageError.postValue(error)
                showProgress.postValue(false)
            })
        }
    }

    fun editCounter(counter: Counter, action: CounterAction) {
        viewModelScope.launch(Dispatchers.IO) {
            val count = counter.count ?: 0
            when (action) {
                CounterAction.Add -> {
                    incrementCounter(counter)
                }
                CounterAction.Subtract -> {
                    if (count == 0) {
                        deleteCounter(counter)
                    } else {
                        decrementCounter(counter)
                    }
                }
            }
        }
    }

    private fun deleteCounter(counter: Counter) {
        repository.deleteCounter(counter,{ responseCounters ->
            updateCounterList(responseCounters)
            showProgress.postValue(false)
        }, { error ->
            messageError.postValue(error)
            showProgress.postValue(false)
        })
    }

    private fun incrementCounter(counter: Counter) {
        repository.incrementCounter(counter,{ responseCounters ->
            updateCounterList(responseCounters)
            showProgress.postValue(false)
        }, { error ->
            messageError.postValue(error)
            showProgress.postValue(false)
        })
    }

    private fun decrementCounter(counter: Counter) {
        repository.decrementCounter(counter,{ responseCounters ->
            updateCounterList(responseCounters)
            showProgress.postValue(false)
        }, { error ->
            messageError.postValue(error)
            showProgress.postValue(false)
        })
    }

    private fun updateCounterList(counterResponse:List<Counter>){
        fullCounterList = counterResponse
        showNoContentView.postValue(counterResponse.isEmpty())
        if(filterSearch.isNotEmpty()) {
            filterList(filterSearch)
            return
        }

        var items = 0
        for (counter in counterResponse) {
            items += (counter.count ?: 0)
        }
        totalTimes.postValue(items)
        totalCounters.postValue(counterResponse.size)
        counters.postValue(counterResponse)
    }

    fun filterList(search: String) {
        filterSearch = search
        if(search.isEmpty()){
            counters.postValue(fullCounterList)
            return
        }
        val filteredCounters: List<Counter>? = fullCounterList?.filter {
                counter -> counter.title.contains(search)
        }
        counters.postValue(filteredCounters)
        showNoContentView.postValue(false)
    }
}