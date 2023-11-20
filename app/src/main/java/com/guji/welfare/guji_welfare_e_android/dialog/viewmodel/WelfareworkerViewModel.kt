package com.guji.welfare.guji_welfare_e_android.dialog.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.guji.welfare.guji_welfare_e_android.App
import com.guji.welfare.guji_welfare_e_android.base.BaseViewModel
import com.guji.welfare.guji_welfare_e_android.data.network.RetrofitClient.api
import kotlinx.coroutines.launch

class WelfareworkerViewModel: BaseViewModel() {
    private val _welfareworkerPhoneNumber = MutableLiveData<String>()
    val welfareworkerPhoneNumber: LiveData<String>
        get() = _welfareworkerPhoneNumber

    fun setWelfareworkerPhoneNumber(phoneNumber: String)= viewModelScope.launch{
        kotlin.runCatching {
            api.updateManagerData(phoneNumber)
        }.onSuccess{
            _welfareworkerPhoneNumber.value = phoneNumber
            val data = it.data
            App.prefs.welfareWorkerName = data.name
            App.prefs.welfareWorkerPhoneNumber = data.telephoneNum
            App.prefs.welfareWorkerBelong = data.belong
        }.onFailure {
            Log.e("애러",it.message.toString())
        }
    }
}