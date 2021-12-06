package com.example.cornershop.ui.intro

import androidx.lifecycle.ViewModel
import com.example.cornershop.helper.SharedPrefHelper

class IntroViewModel : ViewModel() {

    fun updateFirstOpen() {
        SharedPrefHelper().updateFirstAccess()
    }
}