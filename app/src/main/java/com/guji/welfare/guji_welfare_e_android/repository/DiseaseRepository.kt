package com.guji.welfare.guji_welfare_e_android.repository

import com.guji.welfare.guji_welfare_e_android.data.room.disease.dao.DiseaseDao
import com.guji.welfare.guji_welfare_e_android.data.room.disease.entity.Disease

class DiseaseRepository(private val diseaseDao: DiseaseDao) {
    fun insertData(disease: Disease) {
        diseaseDao.insertItem(disease)
    }

    fun getData(): List<Disease> {
        return diseaseDao.getAll()
    }

    fun deleteData() {
        diseaseDao.deleteAll()
    }
}