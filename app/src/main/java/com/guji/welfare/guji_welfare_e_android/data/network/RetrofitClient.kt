package com.guji.welfare.guji_welfare_e_android.data.network

import com.google.gson.GsonBuilder
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient{
    private const val URL = "http://guji-welfare-e.team-alt.com/"

    val cookieManager = MyCookieJar()

    private var okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .cookieJar(cookieManager)
        .build()

    private var gson = GsonBuilder().setLenient().create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val api: API = retrofit.create(API::class.java)
}

class MyCookieJar : CookieJar {
    private val cookies = mutableListOf<Cookie>()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        this.cookies.addAll(cookies)
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val validCookies = mutableListOf<Cookie>()
        val currentTimeMillis = System.currentTimeMillis()

        for (cookie in cookies) {
            if (cookie.expiresAt > currentTimeMillis) {
                validCookies.add(cookie)
            }
        }

        return validCookies
    }
    fun getTokenCookieValue(cookieName: String): String? {
        val tokenCookie = cookies.find { it.name == cookieName }
        return tokenCookie?.value
    }

    fun clear() {
        cookies.clear()
    }
}