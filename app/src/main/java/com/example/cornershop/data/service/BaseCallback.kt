package com.example.cornershop.data.service
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
abstract class BaseCallback<T> : Callback<T> {

    abstract fun onSuccess(response: T?)

    abstract fun onError(error: String)

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            onSuccess(response.body())
        } else {
                onError(
                    Gson().fromJson(Objects.requireNonNull<ResponseBody>(response.errorBody()).string(), ErrorResponse::class.java).message ?: ""
                )
        }
    }
}
class ErrorResponse(
    var message: String? = null
)