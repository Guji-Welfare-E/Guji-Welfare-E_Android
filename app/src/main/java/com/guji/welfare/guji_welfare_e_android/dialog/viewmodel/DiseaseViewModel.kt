package com.guji.welfare.guji_welfare_e_android.dialog.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.guji.welfare.guji_welfare_e_android.base.BaseViewModel
import com.guji.welfare.guji_welfare_e_android.data.room.disease.entity.Disease

class DiseaseViewModel : BaseViewModel() {
    private val temp = mutableListOf<Disease>()

    private val _disease = MutableLiveData<List<Disease>>()
    val disease: LiveData<List<Disease>> = _disease

    init {
        _disease.value = temp
        Log.d("상태","temp: $temp")
    }

    fun addDiseaseData(data: Disease) {
        temp.add(data)
        Log.d("상태","temp: $temp disease: ${disease.value}")
        _disease.value = temp
    }

    fun removeDiseaseData(data: Disease) {
        temp.remove(data)
        _disease.value = temp
    }

    fun clearDiseaseData() {
        temp.clear()
        _disease.value = temp
    }

    //사용자의 질병 일관 변경
//    fun updateGuardiansData = viewModelScope.launch {
//        kotlin.runCatching {
//            api.
//        }
//    }
}