package com.example.cornershop.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cornershop.helper.SharedPrefHelper

class SplashViewModel : ViewModel() {
    private val firstOpen = MutableLiveData<Boolean>()

    fun getFirstOpen(): LiveData<Boolean> {
        return firstOpen
    }

    fun loadFirstOpen() {
        firstOpen.postValue(SharedPrefHelper().getFirtAccess())
    }
}