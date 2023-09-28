package com.guji.welfare.guji_welfare_e_android.dialog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.guji.welfare.guji_welfare_e_android.base.BaseViewModel

class DialogWelfareworkerRegistrationViewModel: BaseViewModel() {
    private val _welfareworkerPhoneNumber = MutableLiveData<String>()
    val welfareworkerPhoneNumber: LiveData<String>
        get() = _welfareworkerPhoneNumber

    fun setWelfareworkerPhoneNumber(phoneNumber: String) {
        _welfareworkerPhoneNumber.value = phoneNumber
    }
}