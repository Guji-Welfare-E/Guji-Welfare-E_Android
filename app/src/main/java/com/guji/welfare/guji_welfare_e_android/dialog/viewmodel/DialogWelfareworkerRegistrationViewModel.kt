package com.guji.welfare.guji_welfare_e_android.dialog.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.guji.welfare.guji_welfare_e_android.App
import com.guji.welfare.guji_welfare_e_android.base.BaseViewModel
import com.guji.welfare.guji_welfare_e_android.data.network.API
import kotlinx.coroutines.launch

class DialogWelfareworkerRegistrationViewModel(
    private val api: API
): BaseViewModel() {
    private val _welfareworkerPhoneNumber = MutableLiveData<String>()
    val welfareworkerPhoneNumber: LiveData<String>
        get() = _welfareworkerPhoneNumber

    fun setWelfareworkerPhoneNumber(phoneNumber: String)= viewModelScope.launch{
        kotlin.runCatching {
            api.updateManagerData(phoneNumber)
        }.onSuccess{
            _welfareworkerPhoneNumber.value = phoneNumber
            //복지사 정보 저장 서버에서 복지사 정보 안줘서 못하는중
            App.prefs.welfareWorkerPhoneNumber
            App.prefs.welfareworkerAffiliation
            App.prefs.welfareWorkerName
        }.onFailure {
            Log.e("애러",it.message.toString())
        }
    }
}