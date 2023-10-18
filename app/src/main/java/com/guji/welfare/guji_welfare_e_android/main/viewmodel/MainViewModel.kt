package com.guji.welfare.guji_welfare_e_android.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.guji.welfare.guji_welfare_e_android.base.ListLiveData
import com.guji.welfare.guji_welfare_e_android.base.BaseViewModel
import com.guji.welfare.guji_welfare_e_android.data.dto.user.UserDataDto
import com.guji.welfare.guji_welfare_e_android.data.network.RetrofitClient
import com.guji.welfare.guji_welfare_e_android.main.adapter.data.GuardianInformationData
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {

    private val _myName = MutableLiveData<String>()
    val myName: LiveData<String>
        get() = _myName

    private val _myAffiliation = MutableLiveData<String>()
    val myDwelling: LiveData<String>
        get() = _myAffiliation


    private val _welfareworkerName = MutableLiveData<String>()
    val welfareworkerName: LiveData<String>
        get() = _welfareworkerName
    private val _welfareworkerPhoneNumber = MutableLiveData<String>()
    val welfareworkerPhoneNumber: LiveData<String>
        get() = _welfareworkerPhoneNumber
    private val _welfareworkerAffiliation = MutableLiveData<String>()
    val welfareworkerAffiliation: LiveData<String>
        get() = _welfareworkerAffiliation


    private val _guardianInformationList = ListLiveData<GuardianInformationData>()
    val guardianInformationList: ListLiveData<GuardianInformationData>
        get() = _guardianInformationList

    val switchMyInformationStatus = MutableLiveData(true)
    val switchGuardianInformationStatus = MutableLiveData(true)
    val switchWelfareworkerInformationStatus = MutableLiveData(true)

    fun setMyName(name: String) {
        _myName.value = name
    }

    fun setAffiliation(affiliation: String) {
        _myAffiliation.value = affiliation
    }

    fun setWelfareworkerName(name: String) {
        _welfareworkerName.value = name
    }

    fun setWelfareworkerAffiliation(affiliation: String) {
        _welfareworkerAffiliation.value = affiliation
    }

    fun getGuardianInformation(name: String, phoneNumber: String, relationship: String) {
        _guardianInformationList.add(GuardianInformationData(name, phoneNumber, relationship))
    }

    fun removeGuardianInformation(name: String, phoneNumber: String, relationship: String) {
        _guardianInformationList.remove(GuardianInformationData(name, phoneNumber, relationship))
    }


    private val _userData = MutableLiveData<UserDataDto>()
    val userData: LiveData<UserDataDto>
        get() = _userData


    fun getUserData() = viewModelScope.launch {
        kotlin.runCatching {
            RetrofitClient.api.getUserData()
        }.onSuccess {
            Log.d("상태",it.toString())
            _userData.value = it
        }.onFailure {
            Log.e("애러",it.message.toString())
        }
    }
}