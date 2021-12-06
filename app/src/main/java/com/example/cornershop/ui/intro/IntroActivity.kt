package com.example.cornershop.ui.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cornershop.databinding.ActivityIntroBinding
import com.example.cornershop.ui.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class IntroActivity : AppCompatActivity() {

    private val viewModel by viewModel<IntroViewModel>()
    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        initListeners()
    }

    private fun initListeners() {
        binding.introBt.setOnClickListener {
            viewModel.updateFirstOpen()
            startActivity(Intent(this,HomeActivity::class.java))
        }
    }
}