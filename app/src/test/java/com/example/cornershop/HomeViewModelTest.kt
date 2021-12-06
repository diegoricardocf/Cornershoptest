package com.example.cornershop

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cornershop.data.service.Api
import com.example.cornershop.data.model.Counter
import com.example.cornershop.ui.home.HomeViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.junit.Assert.*
import org.junit.Rule

@ExperimentalCoroutinesApi
class HomeViewModelTest  {
    private lateinit var repository: CounterRepositoryMock
    private lateinit var viewModel: HomeViewModel

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var api: Api

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        repository = CounterRepositoryMock(api)
        viewModel = HomeViewModel(repository,testDispatcher)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun countersListShouldBeNotEmptyWhenLoadAvalidListFromServer() = runBlockingTest {
        val counterList = listOf(Counter("0","name", 10))
        repository.resultList = counterList
        viewModel.loadCounters(false)
        assertEquals(1, viewModel.counters.value?.size)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun showNoContentViewShouldBeFalseWhenLoadAvalidListFromServer() = runBlockingTest {
        val counterList = listOf(Counter("0","name", 10))
        repository.resultList = counterList
        viewModel.loadCounters(false)
        viewModel.showNoContentView.value?.let { assertFalse(it) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun showNoContentViewShouldBeTrueWhenLoadEmptyListFromServer() = runBlockingTest {
        val counterList = listOf<Counter>()
        repository.resultList = counterList
        viewModel.loadCounters(false)
        viewModel.showNoContentView.value?.let { assertTrue(it) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun shouldReturnOneValueAfterFilterTheCounterList() = runBlockingTest {
        val counterList = listOf(Counter("0","corner", 10),Counter("1","shop", 2))
        repository.resultList = counterList
        viewModel.loadCounters(false)
        viewModel.filterList("corner")
        viewModel.counters.value?.size.let { assertEquals(1,it) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun shouldReturnEmptyListAfterFilterTheCounterList() = runBlockingTest {
        val counterList = listOf(Counter("0","corner", 10),Counter("1","shop", 2))
        repository.resultList = counterList
        viewModel.loadCounters(false)
        viewModel.filterList("cornershop")
        viewModel.counters.value?.size.let { assertEquals(0,it) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun shouldReturnAllTheItemsAfterFilterTheCounterList() = runBlockingTest {
        val counterList = listOf(Counter("0","green apple", 10),Counter("1","red apple", 2))
        repository.resultList = counterList
        viewModel.loadCounters(false)
        viewModel.filterList("apple")
        viewModel.counters.value?.size.let { assertEquals(2,it) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun shouldSumAllTheCountersWhenServerRetunsValidList() = runBlockingTest {
        val counterList = listOf(Counter("0","green apple", 10),Counter("1","red apple", 3))
        repository.resultList = counterList
        viewModel.loadCounters(false)
        viewModel.filterList("apple")
        assertEquals(2, viewModel.totalCounters.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun shouldSumAllTheConterItemsWhenServerRetunsValidList() = runBlockingTest {
        val counterList = listOf(Counter("0","green apple", 10),Counter("1","red apple", 3))
        repository.resultList = counterList
        viewModel.loadCounters(false)
        viewModel.filterList("apple")
        assertEquals(13, viewModel.totalTimes.value)
    }
}
