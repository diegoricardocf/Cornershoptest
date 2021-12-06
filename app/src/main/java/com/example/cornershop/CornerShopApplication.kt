package com.example.cornershop

import android.app.Application
import com.example.cornershop.di.CountersModule
import com.orhanobut.hawk.Hawk
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CornerShopApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CornerShopApplication)
            androidLogger(Level.NONE)
            modules(CountersModule.modules)
        }
        Hawk.init(this).build()
    }
}