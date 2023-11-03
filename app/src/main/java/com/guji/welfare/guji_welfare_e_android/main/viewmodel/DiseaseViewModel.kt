package com.guji.welfare.guji_welfare_e_android.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.guji.welfare.guji_welfare_e_android.base.BaseViewModel
import com.guji.welfare.guji_welfare_e_android.data.room.disease.entity.Disease

class DiseaseViewModel: BaseViewModel() {
    private val _disease = MutableLiveData<List<Disease>>()
    val disease: LiveData<List<Disease>>
        get() = _disease

    fun updateDiseaseData(data: List<Disease>){
        _disease.value = data
    }
}