package com.guji.welfare.guji_welfare_e_android.data

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences(context: Context) {
    companion object{
        const val PREFS_FILENAME = "prefs"
        const val PREF_KEY_ACCESSTOKEN = "accessToken"
        const val PREF_KEY_MY_NAME = "name"
        const val PREF_KEY_NICKNAME = "nickname"
        const val PREF_KEY_PHONE_NUMBER = "phoneNumber"
        const val PREF_KEY_DWELLING = "dwelling"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var accessToken: String?
        get() = prefs.getString(PREF_KEY_ACCESSTOKEN, "")
        set(value) = prefs.edit().putString(PREF_KEY_ACCESSTOKEN, value).apply()
    var myName: String?
        get() = prefs.getString(PREF_KEY_MY_NAME, "")
        set(value) = prefs.edit().putString(PREF_KEY_MY_NAME, value).apply()
    var dwelling: String?
        get() = prefs.getString(PREF_KEY_DWELLING, "")
        set(value) = prefs.edit().putString(PREF_KEY_DWELLING, value).apply()
    var nickname: String?
        get() = prefs.getString(PREF_KEY_NICKNAME, "")
        set(value) = prefs.edit().putString(PREF_KEY_NICKNAME, value).apply()
    var phoneNumber: String?
        get() = prefs.getString(PREF_KEY_PHONE_NUMBER, "")
        set(value) = prefs.edit().putString(PREF_KEY_PHONE_NUMBER, value).apply()

}