package com.guji.welfare.guji_welfare_e_android.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.guji.welfare.guji_welfare_e_android.base.ListLiveData
import com.guji.welfare.guji_welfare_e_android.base.BaseViewModel
import com.guji.welfare.guji_welfare_e_android.main.adapter.data.GuardianInformationData

class MainViewModel : BaseViewModel() {

    private val _guardianInformationList = ListLiveData<GuardianInformationData>()
    val guardianInformationList: ListLiveData<GuardianInformationData>
        get() = _guardianInformationList

    val switchMyInformationStatus = MutableLiveData(true)
    val switchGuardianInformationStatus = MutableLiveData(true)
    val switchWelfareworkerInformationStatus = MutableLiveData(true)

    fun setMyName(name: String){
        _myName.value = name
    }

    fun setWelfareworker(phoneNumber: String){
        _welfareworker.value = phoneNumber
        Log.d("상담",welfareworker.value.toString())
    }


    fun getGuardianInformation(name: String, phoneNumber: String, relationship: String) {
        _guardianInformationList.add(GuardianInformationData(name, phoneNumber, relationship))
    }
}