package com.example.cornershop.di

import com.example.cornershop.data.service.Api
import com.example.cornershop.data.model.CounterRepository
import com.example.cornershop.ui.create.CreateCounterViewmodel
import com.example.cornershop.ui.home.HomeViewModel
import com.example.cornershop.ui.intro.IntroViewModel
import com.example.cornershop.ui.splash.SplashViewModel
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object CountersModule{
    private val viewModelModule = module {
        viewModel { SplashViewModel() }
        viewModel { HomeViewModel(get(), Dispatchers.IO) }
        viewModel { CreateCounterViewmodel(get()) }
        viewModel { IntroViewModel() }
    }
    private val repositoryModule = module {
        single {
            CounterRepository(get())
        }
    }

    private val netWorkModule = module {
        single {
            Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client( OkHttpClient.Builder()
                    .readTimeout(15, TimeUnit.SECONDS)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(520, TimeUnit.SECONDS)
                    .addInterceptor { chain ->
                        val request = chain.request()
                            .newBuilder()
                            .build()
                        chain.proceed(request)
                    }.build()).build().create(Api::class.java)
        }
    }

    val modules = listOf(
        viewModelModule,repositoryModule,netWorkModule
    )

}
