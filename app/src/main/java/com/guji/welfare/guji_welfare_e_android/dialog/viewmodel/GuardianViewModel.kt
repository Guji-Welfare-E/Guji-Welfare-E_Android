package com.guji.welfare.guji_welfare_e_android.dialog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.guji.welfare.guji_welfare_e_android.base.BaseViewModel
import com.guji.welfare.guji_welfare_e_android.data.dto.user.Guardian
import com.guji.welfare.guji_welfare_e_android.data.dto.user.GuardianDto
import com.guji.welfare.guji_welfare_e_android.data.network.RetrofitClient.api
import kotlinx.coroutines.launch

class GuardianViewModel: BaseViewModel() {
    private val _guardian = MutableLiveData<List<Guardian>>()
    val guardian: LiveData<List<Guardian>>
        get() = _guardian

    fun updateGuardiansData(data: List<Guardian>){
        _guardian.postValue(data)
    }

    fun updateGuardiansData(data: List<GuardianDto>) = viewModelScope.launch{
        kotlin.runCatching {
            api.updateGuardiansData(data)
        }.onSuccess {

        }.onFailure {

        }
    }
}