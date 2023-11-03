package com.guji.welfare.guji_welfare_e_android.dialog.viewmodel

import androidx.lifecycle.viewModelScope
import com.guji.welfare.guji_welfare_e_android.base.BaseViewModel
import com.guji.welfare.guji_welfare_e_android.data.dto.user.GuardianDto
import com.guji.welfare.guji_welfare_e_android.data.network.API
import kotlinx.coroutines.launch

class DialogGuardianInformationAddViewModel(
    private val api: API
): BaseViewModel() {
    fun updateGuardiansData(data: List<GuardianDto>) = viewModelScope.launch{
        kotlin.runCatching {
            api.updateGuardiansData(data)
        }.onSuccess {

        }.onFailure {

        }
    }
}