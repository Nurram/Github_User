package com.nurram.githubuser.network

import com.nurram.githubuser.model.ConstValue
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {

    companion object {
        val instance = RetrofitInstance()
    }

    private val client = OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor()
        .apply { level = HttpLoggingInterceptor.Level.BODY })
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(ConstValue.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiInterface = retrofit.create(ApiInterface::class.java)
}