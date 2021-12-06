package com.example.cornershop.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cornershop.data.model.Counter
import com.example.cornershop.databinding.HomeRowBinding
import com.example.cornershop.helper.AutoUpdatableAdapter
import kotlin.properties.Delegates

enum class CounterAction{
    Add,
    Subtract
}

class HomeAdapter(val editTapped: (Counter, CounterAction) -> Unit) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>(), AutoUpdatableAdapter {
    var items: List<Counter> by Delegates.observable(emptyList()) {
            _, old, new ->
        autoNotify(old, new) { o, n -> o.id == n.id }
    }

    class  HomeViewHolder(val binding: HomeRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(HomeRowBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val counter = items[position]
        holder.binding.homeRowCounterName.text = counter.title
        holder.binding.homeRowCounterQuant.text = (counter.count ?:0 ).toString()
        holder.binding.homeRowPlusButton.setOnClickListener {
            editTapped(counter,CounterAction.Add)
        }
        holder.binding.homeRowMinusButton.setOnClickListener {
            editTapped(counter,CounterAction.Subtract)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}