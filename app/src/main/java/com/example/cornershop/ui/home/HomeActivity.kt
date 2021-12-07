package com.example.cornershop.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.cornershop.data.model.Counter
import com.example.cornershop.databinding.ActivityHomeBinding
import com.example.cornershop.helper.extensions.afterTextChanged
import com.example.cornershop.helper.extensions.animateLoading
import com.example.cornershop.ui.create.CreateCounterActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var binding: ActivityHomeBinding
    private var homeAdapter:HomeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        initListeners()
        initViews()
        initObservables()
    }

    private fun initListeners() {
        binding.homeAddCounterBt.setOnClickListener {
            startActivity(Intent(this,CreateCounterActivity::class.java))
        }
        binding.homeSearchEditText.afterTextChanged { text ->
            viewModel.filterList(text)
        }
        binding.homeSwipeToRefresh.setOnRefreshListener {
            binding.homeSwipeToRefresh.isRefreshing = true
            viewModel.loadCounters(true)
        }
    }
    private fun initViews() {
        homeAdapter = HomeAdapter { counter, action ->
            editCounter(counter, action)
        }
        binding.homeRecycler.adapter = homeAdapter
        binding.homeRecycler.layoutManager = LinearLayoutManager(this)

        binding.homeNoContentView.setup("No counters yet", "When I started counting my blessings, my whole life turned around.” —Willie Nelson")
    }

    private fun editCounter(counter: Counter, action: CounterAction) {
        viewModel.editCounter(counter,action)
    }

    private fun initObservables() {
        viewModel.getCounters().observe(this) { counters ->
            updateRecycler(counters)
        }
        viewModel.getShowProgress().observe(this) { shouldShow ->
             animateLoading(shouldShow)
        }

        viewModel.getTotalTimes().observe(this) { totalTimes ->
            updateTotalTimes(totalTimes)
        }

        viewModel.getTotalCounters().observe(this) { totalCounters ->
           updateTotalCounters(totalCounters)
        }

        viewModel.getShowNoContentView().observe(this) { shouldShowNoContentView ->
            binding.homeNoContentView.show(shouldShowNoContentView)
            binding.homeSearchInputlayout.visibility = (if (shouldShowNoContentView) View.GONE else View.VISIBLE)
        }

        viewModel.getMessageError().observe(this){ errorMesage ->
            binding.homeSwipeToRefresh.isRefreshing = false
            Toast.makeText(this,errorMesage,Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateTotalCounters(totalCounters: Int) {
        binding.homeTotalItemsTextview.text = totalCounters.toString().plus(" times")
        if(totalCounters == 0){
            binding.homeTotalItemsTextview.visibility = View.GONE
        }else{
            binding.homeTotalItemsTextview.visibility = View.VISIBLE
        }
    }

    private fun updateTotalTimes(totalTimes: Int) {
        if(totalTimes == 0){
            binding.homeTotalCountersTextview.visibility = View.GONE
        }else{
            binding.homeTotalCountersTextview.visibility = View.VISIBLE
        }
        binding.homeTotalCountersTextview.text = totalTimes.toString().plus(" items")
    }

    private fun updateRecycler(counters: List<Counter>) {
        homeAdapter?.items = counters
        if(binding.homeSwipeToRefresh.isRefreshing){
            binding.homeSwipeToRefresh.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCounters(false)
    }

    override fun onRefresh() {

    }
}