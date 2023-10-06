package com.guji.welfare.guji_welfare_e_android.data.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient{
    private const val URL = "http://guji-welfare-e.team-alt.com/"

    private val loggingInterceptor = LoggingInterceptor()

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    var gson = GsonBuilder().setLenient().create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val api: API = retrofit.create(API::class.java)
}


