package com.guji.welfare.guji_welfare_e_android.model.local.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientRetrofit {
    const val baseURL = "http://10.80.0.0:8080"

    val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: API = retrofit.create(API::class.java)
}