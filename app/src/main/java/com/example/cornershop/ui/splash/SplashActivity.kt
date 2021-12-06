package com.example.cornershop.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.cornershop.R
import com.example.cornershop.ui.home.HomeActivity
import com.example.cornershop.ui.intro.IntroActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModel<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            loadFirsAccess()
        }, 3000)
    }

    private fun loadFirsAccess() {
        viewModel.getFirstOpen().observe(this) { firstOpen ->
            if (firstOpen) {
                startActivity(Intent(this, IntroActivity::class.java))
            } else {
                startActivity(Intent(this, HomeActivity::class.java))
            }
            finish()
        }
        viewModel.loadFirstOpen()
    }
}