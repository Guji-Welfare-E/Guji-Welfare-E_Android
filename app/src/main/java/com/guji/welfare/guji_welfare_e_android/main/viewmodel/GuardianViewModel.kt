package com.guji.welfare.guji_welfare_e_android.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.guji.welfare.guji_welfare_e_android.base.BaseViewModel
import com.guji.welfare.guji_welfare_e_android.main.adapter.data.GuardianInformationData

class GuardianViewModel: BaseViewModel() {
    private val _guardian = MutableLiveData<List<GuardianInformationData>>()
    val guardian: LiveData<List<GuardianInformationData>>
        get() = _guardian

    fun updateGuardiansData(data: List<GuardianInformationData>){
        _guardian.value = data
    }
}