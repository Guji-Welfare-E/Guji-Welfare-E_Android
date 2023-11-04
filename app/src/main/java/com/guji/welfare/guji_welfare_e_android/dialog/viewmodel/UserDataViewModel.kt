package com.guji.welfare.guji_welfare_e_android.dialog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.guji.welfare.guji_welfare_e_android.base.BaseViewModel

class UserDataViewModel: BaseViewModel() {
    private val _myNickname = MutableLiveData<String>()
    val myNickname: LiveData<String>
        get() = _myNickname

    fun setMyNickName(nickName: String){
        _myNickname.value = nickName
    }
}