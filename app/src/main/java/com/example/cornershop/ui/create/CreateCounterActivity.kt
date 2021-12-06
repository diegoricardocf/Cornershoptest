package com.example.cornershop.ui.create

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.example.cornershop.R
import com.example.cornershop.databinding.ActivityCreateCounterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateCounterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateCounterBinding
    private val viewModel by viewModel<CreateCounterViewmodel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCounterBinding.inflate(layoutInflater)
        supportActionBar?.title = "Create counter"
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.create_counter_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.counter_menu_add -> {
                if(counterIsValid()){
                    createCounter()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun counterIsValid(): Boolean {
        val counterText = binding.createCounterEditText.text.toString()
        val isValid = counterText.isNotEmpty()
        if(!isValid){
            binding.createCounterInputLayout.error = "Please, fill the name"
        }
        return isValid
    }

    private fun createCounter() {
        viewModel.getCounters().observe(this) {
            finish()
        }
        viewModel.createCounters(binding.createCounterEditText.text.toString())
    }
}