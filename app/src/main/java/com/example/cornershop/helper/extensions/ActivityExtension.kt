package com.example.cornershop.helper.extensions

import androidx.appcompat.app.AppCompatActivity
import com.example.cornershop.helper.ProgressDialog

fun AppCompatActivity.animateLoading(show: Boolean){
    if(show){
        ProgressDialog.instance.show(this.supportFragmentManager)
    }else{
        ProgressDialog.instance.dismiss()
    }
}