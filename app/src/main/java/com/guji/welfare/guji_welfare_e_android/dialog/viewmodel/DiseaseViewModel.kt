package com.guji.welfare.guji_welfare_e_android.dialog.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.guji.welfare.guji_welfare_e_android.data.dto.user.DiseasesDto
import com.guji.welfare.guji_welfare_e_android.data.dto.user.Guardian
import com.guji.welfare.guji_welfare_e_android.data.network.RetrofitClient.api
import com.guji.welfare.guji_welfare_e_android.data.room.AppDatabase
import com.guji.welfare.guji_welfare_e_android.data.room.disease.entity.Disease
import com.guji.welfare.guji_welfare_e_android.repository.DiseaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiseaseViewModel(application: Application) : AndroidViewModel(application) {

    private val diseaseRepository: DiseaseRepository

    init {
        val diseaseDao = AppDatabase.getInstance(application)!!.diseaseDao()
        diseaseRepository = DiseaseRepository(diseaseDao)
    }

    private val _disease = MutableLiveData<List<DiseasesDto>>()
    val disease: LiveData<List<DiseasesDto>> = _disease

    fun insertData(disease: Disease) {
        CoroutineScope(Dispatchers.IO).launch {
            diseaseRepository.insertData(disease)
            _disease.postValue(diseaseRepository.getData().map {
                DiseasesDto(it.date, it.name)
            })
        }
    }

    suspend fun getDataSize(): Int {
        var size = 0
        CoroutineScope(Dispatchers.IO).launch {
            size = diseaseRepository.getData().size
        }.join()
        return size
    }

    fun updateDiseaseData(data: List<Disease>) {
        _disease.postValue(data.map {
            DiseasesDto(it.date, it.name)
        })
    }

    //사용자의 질병 일관 변경
    fun updateDiseaseData(diseasesDto: List<DiseasesDto>) = viewModelScope.launch {
        kotlin.runCatching {
            api.updateDiseasesData(diseasesDto)
        }.onSuccess {
            when (it.status) {
                200 -> {
                    _disease.postValue(diseasesDto)
                }
                else -> {
                    Log.d("updateDiseaseData",it.toString())
                }
            }
        }.onFailure { e ->
            Log.d("애러", e.message.toString() + " updateGuardiansData")
        }
    }
}