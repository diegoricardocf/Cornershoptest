package com.example.cornershop.helper

import com.orhanobut.hawk.Hawk

class SharedPrefHelper {
    companion object {
        const val FIRST_ACCESS = "FIRST_ACCESS"
    }

    fun updateFirstAccess(){
        Hawk.put(FIRST_ACCESS, false)
    }

    fun getFirtAccess() : Boolean {
      return Hawk.get(FIRST_ACCESS, true)
    }

}