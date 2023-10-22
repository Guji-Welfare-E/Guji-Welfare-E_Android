package com.guji.welfare.guji_welfare_e_android.data

import android.content.Context
import android.content.SharedPreferences
import com.guji.welfare.guji_welfare_e_android.App

class MySharedPreferences(context: Context) {
    companion object {
        const val PREFS_FILENAME = "prefs"

        const val PREF_KEY_AUTO_LOGIN = "autoLogin"

        //Token
        const val PREF_KEY_ACCESS_TOKEN = "accessToken"
        const val PREF_KEY_REFRESH_TOKEN = "refreshToken"

        //My
        const val PREF_KEY_MY_NAME = "myName"
        const val PREF_KEY_MY_NICKNAME = "myNickname"
        const val PREF_KEY_MY_DWELLING = "myDwelling"
        const val PREF_KEY_MY_BIRTHDAY = "Birthday"

        //WelfareWorker
        const val PREF_KEY_WELFAREWORKER_PHONE_NUMBER = "welfareWorkerPhoneNumber"
        const val PREF_KEY_WELFAREWORKER_NAME = "welfareWorkerName"
        const val PREF_KEY_WELFAREWORKER_AFFILIATION = "welfareWorkerAffiliation"

        //guardian
        const val PREF_KEY_GUARDIAN_DATA = "guardianData"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    fun remove(){
        App.prefs.autoLogin = false
        prefs.edit().remove(PREF_KEY_ACCESS_TOKEN).apply()
        prefs.edit().remove(PREF_KEY_REFRESH_TOKEN).apply()
        prefs.edit().remove(PREF_KEY_MY_NAME).apply()
        prefs.edit().remove(PREF_KEY_MY_NICKNAME).apply()
        prefs.edit().remove(PREF_KEY_MY_DWELLING).apply()
        prefs.edit().remove(PREF_KEY_MY_BIRTHDAY).apply()
        prefs.edit().remove(PREF_KEY_WELFAREWORKER_PHONE_NUMBER).apply()
        prefs.edit().remove(PREF_KEY_WELFAREWORKER_NAME).apply()
        prefs.edit().remove(PREF_KEY_WELFAREWORKER_AFFILIATION).apply()
        prefs.edit().remove(PREF_KEY_GUARDIAN_DATA).apply()
    }

    var autoLogin: Boolean
        get() = prefs.getBoolean(PREF_KEY_AUTO_LOGIN, false)
        set(value) = prefs.edit().putBoolean(PREF_KEY_AUTO_LOGIN, value).apply()
    var accessToken: String
        get() = prefs.getString(PREF_KEY_ACCESS_TOKEN, "").toString()
        set(value) = prefs.edit().putString(PREF_KEY_ACCESS_TOKEN, value).apply()
    var refreshToken: String
        get() = prefs.getString(PREF_KEY_REFRESH_TOKEN, "").toString()
        set(value) = prefs.edit().putString(PREF_KEY_REFRESH_TOKEN, value).apply()


    //내정보
    var myName: String
        get() = prefs.getString(PREF_KEY_MY_NAME, "").toString()
        set(value) = prefs.edit().putString(PREF_KEY_MY_NAME, value).apply()
    var myDwelling: String
        get() = prefs.getString(PREF_KEY_MY_DWELLING, "").toString()
        set(value) = prefs.edit().putString(PREF_KEY_MY_DWELLING, value).apply()
    var myNickname: String
        get() = prefs.getString(PREF_KEY_MY_NICKNAME, "").toString()
        set(value) = prefs.edit().putString(PREF_KEY_MY_NICKNAME, value).apply()
    var myBirthday: String
        get() = prefs.getString(PREF_KEY_MY_BIRTHDAY, "").toString()
        set(value) = prefs.edit().putString(PREF_KEY_MY_BIRTHDAY, value).apply()

    //메니저
    var welfareWorkerPhoneNumber: String
        get() = prefs.getString(PREF_KEY_WELFAREWORKER_PHONE_NUMBER, "").toString()
        set(value) = prefs.edit().putString(PREF_KEY_WELFAREWORKER_PHONE_NUMBER, value).apply()
    var welfareWorkerName: String
        get() = prefs.getString(PREF_KEY_WELFAREWORKER_NAME, "").toString()
        set(value) = prefs.edit().putString(PREF_KEY_WELFAREWORKER_NAME, value).apply()
    var welfareworkerAffiliation: String
        get() = prefs.getString(PREF_KEY_WELFAREWORKER_AFFILIATION, "").toString()
        set(value) = prefs.edit().putString(PREF_KEY_WELFAREWORKER_AFFILIATION, value).apply()

    //보호자
    var guardianData: String
        get() = prefs.getString(PREF_KEY_GUARDIAN_DATA, "").toString()
        set(value) = prefs.edit().putString(PREF_KEY_GUARDIAN_DATA,value).apply()

}