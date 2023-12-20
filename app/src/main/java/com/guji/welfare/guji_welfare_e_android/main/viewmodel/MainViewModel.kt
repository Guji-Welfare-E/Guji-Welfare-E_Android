package com.guji.welfare.guji_welfare_e_android.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.guji.welfare.guji_welfare_e_android.App
import com.guji.welfare.guji_welfare_e_android.base.BaseViewModel
import com.guji.welfare.guji_welfare_e_android.data.dto.user.UserDataDto
import com.guji.welfare.guji_welfare_e_android.data.network.RetrofitClient.api
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {
    //switch
    val switchMyInformationStatus = MutableLiveData(true)
    val switchGuardianInformationStatus = MutableLiveData(true)
    val switchWelfareworkerInformationStatus = MutableLiveData(true)
    val switchDiseaseDisorderInformationStatus = MutableLiveData(true)

    private val _userData = MutableLiveData<UserDataDto>()
    val userData: LiveData<UserDataDto>
        get() = _userData

    fun getUserData() = viewModelScope.launch {
        kotlin.runCatching {
            api.getUserData()
        }.onSuccess {
            _userData.value = it

            App.prefs.myNickname = it.data.nickName
            App.prefs.myName = it.data.name
            App.prefs.myBirthday = it.data.birth
            App.prefs.myDwelling = it.data.residence

            if (it.data.manager != null) {
                App.prefs.welfareWorkerName = it.data.manager.name
                App.prefs.welfareWorkerPhoneNumber = it.data.manager.telephoneNum
                App.prefs.welfareWorkerBelong = it.data.manager.belong
            }


        }.onFailure {
            Log.e("애러", it.message.toString())
        }
    }
}