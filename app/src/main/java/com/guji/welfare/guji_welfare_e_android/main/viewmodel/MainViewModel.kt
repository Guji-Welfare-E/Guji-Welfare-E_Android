package com.guji.welfare.guji_welfare_e_android.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.guji.welfare.guji_welfare_e_android.base.ListLiveData
import com.guji.welfare.guji_welfare_e_android.base.BaseViewModel
import com.guji.welfare.guji_welfare_e_android.main.adapter.data.GuardianInformationData

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

    fun setMyName(name: String){
        _myName.value = name
    }

    fun setAffiliation(affiliation: String){
        _myAffiliation.value = affiliation
    }

    fun setWelfareworkerName(name: String){
        _welfareworkerName.value = name
    }

    fun setWelfareworkerAffiliation(affiliation: String){
        _welfareworkerAffiliation.value = affiliation
    }

    fun getGuardianInformation(name: String, phoneNumber: String, relationship: String) {
        _guardianInformationList.add(GuardianInformationData(name, phoneNumber, relationship))
    }
    fun removeGuardianInformation(name: String, phoneNumber: String, relationship: String){
        _guardianInformationList.remove(GuardianInformationData(name, phoneNumber, relationship))
    }
}