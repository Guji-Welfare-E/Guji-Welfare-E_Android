package com.guji.welfare.guji_welfare_e_android.data

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences(context: Context) {
    companion object {
        const val PREFS_FILENAME = "prefs"

        //Token
        const val PREF_KEY_ACCESS_TOKEN = "accessToken"
        const val PREF_KEY_REFRESH_TOKEN = "refreshToken"

        //My
        const val PREF_KEY_MY_NAME = "myName"
        const val PREF_KEY_MY_NICKNAME = "myNickname"
        const val PREF_KEY_MY_DWELLING = "myDwelling"

        //WelfareWorker
        const val PREF_KEY_WELFAREWORKER_PHONE_NUMBER = "welfareWorkerPhoneNumber"
        const val PREF_KEY_WELFAREWORKER_NAME = "welfareWorkerName"
        const val PREF_KEY_WELFAREWORKER_AFFILIATION = "welfareWorkerAffiliation"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)


    var accessToken: String?
        get() = prefs.getString(PREF_KEY_ACCESS_TOKEN, "")
        set(value) = prefs.edit().putString(PREF_KEY_ACCESS_TOKEN, value).apply()
    var refreshToken: String?
        get() = prefs.getString(PREF_KEY_REFRESH_TOKEN, "")
        set(value) = prefs.edit().putString(PREF_KEY_REFRESH_TOKEN, value).apply()


    var myName: String?
        get() = prefs.getString(PREF_KEY_MY_NAME, "")
        set(value) = prefs.edit().putString(PREF_KEY_MY_NAME, value).apply()
    var myDwelling: String?
        get() = prefs.getString(PREF_KEY_MY_DWELLING, "")
        set(value) = prefs.edit().putString(PREF_KEY_MY_DWELLING, value).apply()
    var myNickname: String?
        get() = prefs.getString(PREF_KEY_MY_NICKNAME, "")
        set(value) = prefs.edit().putString(PREF_KEY_MY_NICKNAME, value).apply()


    var welfareWorkerPhoneNumber: String?
        get() = prefs.getString(PREF_KEY_WELFAREWORKER_PHONE_NUMBER, "")
        set(value) = prefs.edit().putString(PREF_KEY_WELFAREWORKER_PHONE_NUMBER, value).apply()
    var welfareWorkerName: String?
        get() = prefs.getString(PREF_KEY_WELFAREWORKER_NAME, "")
        set(value) = prefs.edit().putString(PREF_KEY_WELFAREWORKER_NAME, value).apply()
    var welfareworkerAffiliation: String?
        get() = prefs.getString(PREF_KEY_WELFAREWORKER_AFFILIATION, "")
        set(value) = prefs.edit().putString(PREF_KEY_WELFAREWORKER_AFFILIATION, value).apply()

}