package com.guji.welfare.guji_welfare_e_android

import android.app.Application
import com.guji.welfare.guji_welfare_e_android.data.MySharedPreferences

class App: Application() {
    companion object {
        lateinit var prefs : MySharedPreferences
    }
    /* prefs라는 이름의 MySharedPreferences 하나만 생성할 수 있도록 설정. */

    override fun onCreate() {
        prefs = MySharedPreferences(applicationContext)
        super.onCreate()
    }
}